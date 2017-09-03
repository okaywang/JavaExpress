package com.zhaopin.thrift.rpc.executor;

import java.util.concurrent.ExecutorService;

import org.apache.thrift.TApplicationException;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.zhaopin.common.ThriftContext;
import com.zhaopin.common.container.RequestID;
import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.common.trace.ZipkinContext;
import com.zhaopin.thrift.rpc.common.ServiceRegisty;
import com.zhaopin.thrift.rpc.common.TMessage;
import com.zhaopin.thrift.rpc.common.TMessageType;
import com.zhaopin.thrift.rpc.monitor.InvokeStatistics;
import com.zhaopin.thrift.rpc.monitor.StatisticsRecord;
import com.zhaopin.thrift.rpc.processor.TProcessor;
import com.zhaopin.thrift.rpc.protocol.TProtocol;
import com.zhaopin.thrift.rpc.protocol.ThriftProtocol;
import com.zhaopin.thrift.rpc.protocol.ThriftProtocolWraper;
import com.zhaopin.thrift.rpc.server.ServiceRegister;
import com.zhaopin.thrift.rpc.util.ExceptionStackTraceUtils;
import com.zhaopin.thrift.rpc.util.InvokeChainUtil;
import com.zhaopin.thrift.rpc.util.RuntimeExceptionCodec;
import com.zhaopin.thrift.rpc.util.UniqueIdUtils;

import brave.Tracer;
import brave.Span.Kind;
import brave.internal.HexCodec;
import brave.propagation.TraceContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ThriftEventExecutor {

	public static final Logger LOGGER = LoggerFactory.getLogger(ThriftEventExecutor.class);

	private static ExecutorService executors = ThreadPoolExecutorFactory.getThreadPoolExecutor();

	public void processRequest(final ChannelHandlerContext ctx, final ByteBuf byteBuf) {
		// 是否需要对请求的顺序进行顺序响应?
		// 提交请求到线程池
		final long startTime = System.currentTimeMillis();
		executors.execute(new Runnable() {
			@Override
			public void run() {
				ThriftProtocol iprot = new ThriftProtocol(byteBuf);
				ThriftProtocol oprot = new ThriftProtocol(ctx.alloc().ioBuffer());
				TMessage msg = null;
				StatisticsRecord record = new StatisticsRecord();
				record.setStartTime(startTime);
				TProcessor processor = null;
				brave.Span span = null;
				try {
					msg = iprot.readMessageBegin();
					if (msg.getGrayToken() == null) {
						ThriftContext.setGrayToken("");
					} else {
						ThriftContext.setGrayToken(msg.getGrayToken());
					}
					// 设置附件
					if (msg.getAttachment() != null) {
						ThriftContext.setAttach(msg.getAttachment());
					}
					String requestId = msg.getTraceId();
					long traceIdHigh = 0, traceIdLow = 0;
					if (StringUtils.isEmpty(msg.getTraceId())) {
						// 设置统一的traceId
						requestId = UniqueIdUtils.generate();
						RequestID.setRequestID(requestId);
						traceIdHigh = HexCodec.lowerHexToUnsignedLong(requestId.substring(0, 16));
						traceIdLow = HexCodec.lowerHexToUnsignedLong(requestId.substring(16, 32));
					} else {
						RequestID.setRequestID(requestId);
						if (requestId.contains("-")) {
							LOGGER.warn("the trace id contain '-' character!");
						} else if (requestId.length() < 32) {
							LOGGER.warn("the trace id's length is less than 32 characters!");
						} else {
							traceIdHigh = HexCodec.lowerHexToUnsignedLong(requestId.substring(0, 16));
							traceIdLow = HexCodec.lowerHexToUnsignedLong(requestId.substring(16, 32));
						}
					}
					TraceContext traceCtx = null;
					long parentId = msg.getParentId();
					// 当前的spanId
					long spanId = msg.getSpanId();
					boolean sampled = msg.isSampled();
					if (ZipkinContext.inst.isInit()) {
						Tracer tracer = ZipkinContext.inst.getTracing().tracer();
						// 基于采样率的采样
						if (parentId == 0) {
							// 从开始判断是否采样
							sampled = ZipkinContext.inst.getSampler().isSampled(traceIdLow);
							traceCtx = brave.propagation.TraceContext.newBuilder().traceId(traceIdLow)
									.traceIdHigh(traceIdHigh).spanId(spanId).parentId(null).sampled(sampled).build();
						} else {
							traceCtx = brave.propagation.TraceContext.newBuilder().traceId(traceIdLow)
									.traceIdHigh(traceIdHigh).spanId(spanId).parentId(parentId).sampled(sampled)
									.build();
						}
						// 调用跟踪链的ID, 用来连接上下文
						ThriftContext.setParentId(msg.getParentId());
						ThriftContext.setSpanId(spanId);
						ThriftContext.setSampled(sampled);
						msg.setSampled(sampled);
						span = tracer.toSpan(traceCtx).name(msg.getName()).start();
						span.kind(Kind.SERVER);
						tracer.withSpanInScope(span);
					}
					// 开始服务调用统计
					String client = ctx.channel().remoteAddress().toString();
					// 设置调用的客户端地址
					ThriftContext.setClientAddr(client);
					record.setClient(client);
					processor = getProcessor(msg.getName(), record);
					InvokeChainUtil.mutateInvokeChain(msg.getTraceId(), record, true);
					// 处理实际的请求
					processor.process(new ThriftProtocolWraper(iprot, msg), oprot);
					ByteBuf buffer = oprot.getBuffer();
					// 写消息到客户端
					if (ctx.channel().isActive()) {
						writeResponse(ctx, buffer);
					} else {
						LOGGER.error("channel is close, unable to send result to client!");
					}
					record.setSuccess(true);
					if (span != null) {
						span.tag("result", "success");
					}

					InvokeStatistics.instance.statistics(record);
				} catch (Throwable fail) {
					if (span != null) {
						span.tag("result", "failure");
					}
					// 打印错误日志
					long cost = System.currentTimeMillis() - startTime;
					record.setSuccess(false);
					if (msg != null) {
						logError(msg, cost, fail);
						try {
							processException(fail, oprot, msg, ctx);
						} catch (Throwable reason) {
							LOGGER.error("[thrift]exception", reason);
						}
					} else {
						LOGGER.error("thrift service exception", fail);
					}
					InvokeStatistics.instance.statistics(record);
				} finally {
					// 释放分配的内存
					iprot.getBuffer().release();
					oprot.getBuffer().release();
					if (msg != null) {
						InvokeChainUtil.removeInvokeChain(msg.getTraceId());
					}
					if (span != null) {
						span.finish();
					}
				}
			}

			protected void processException(Throwable cause, ThriftProtocol oprot, TMessage msg,
					ChannelHandlerContext ctx) {
				// 对nodejs需要特殊处理
				if (msg.getThriftVersion() == TProtocol.NODEJS) {
					String reason = ExceptionStackTraceUtils.getStackTrace(cause);
					TApplicationException stackUtil = new TApplicationException(TApplicationException.INTERNAL_ERROR,
							reason);
					// 需要将TMessage中的:前面的部分去掉
					String funcName = msg.getName();
					int index = msg.getName().indexOf(":");
					if (index >= 0) {
						funcName = msg.getName().substring(index + 1);
					}
					oprot.writeMessageBegin(new TMessage(funcName, TMessageType.EXCEPTION, msg.getSeqid()));
					stackUtil.write(oprot);
					oprot.writeMessageEnd();
					// 写入异常结果给nodejs
					writeResponse(ctx, oprot.getBuffer());
				} else {
					oprot.writeMessageBegin(new TMessage("error", TMessageType.RUNTIME_EXCEPTION, msg.getSeqid(),
							msg.getTraceId(), msg.getGrayToken(), msg.getParentId()));
					RuntimeExceptionCodec.write(oprot, cause);
					oprot.writeMessageEnd();
					writeResponse(ctx, oprot.getBuffer());
				}

			}

			protected void writeResponse(ChannelHandlerContext ctx, ByteBuf buffer) {
				ByteBuf result = ctx.alloc().ioBuffer(buffer.readableBytes() + 4);
				result.writeInt(buffer.readableBytes());
				result.writeBytes(buffer);
				ctx.channel().writeAndFlush(result);
			}
		});
	}

	private void logError(final TMessage message, long cost, Throwable cause) {
		JSONObject errorlog = new JSONObject();
		errorlog.put("service", message.getName());
		errorlog.put("ExecuteTime", cost);
		errorlog.put("traceId", RequestID.getRequestID());
		errorlog.put("gray_token", ThriftContext.getGrayToken());
		errorlog.put("parentId", message.getParentId());
		errorlog.put("spanId", message.getSpanId());
		LOGGER.error("{\"thrift service exception\": {}", errorlog, cause);
	}

	private TProcessor getProcessor(String service, StatisticsRecord record) {
		int index = service.indexOf(":");
		if (index < 0) {
			// 没有对应的服务
			LOGGER.error("exception service {} not exists!", service);
			throw new IllegalStateException("service \"" + service + "\" not exists!");
		}
		String target = service.substring(0, index);
		record.setService(target);
		record.setMethod(service.substring(index + 1));
		ServiceRegisty serviceRegisty = ServiceRegister.instance.getServiceDetail(target);
		if (serviceRegisty != null) {
			// 设置服务的相关信息
			record.setVersion(serviceRegisty.getVersion());
			record.setGroup(serviceRegisty.getGroup());
		}
		TProcessor processor = ServiceRegister.instance.getProcessors(target);
		if (processor == null) {
			// 没有对应的服务
			LOGGER.error("exception service {} not exists!", service);
			throw new IllegalStateException("service \"" + service + "\" not exists!");
		}
		return processor;
	}
}
