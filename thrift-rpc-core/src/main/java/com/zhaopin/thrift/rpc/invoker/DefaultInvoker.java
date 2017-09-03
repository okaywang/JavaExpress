package com.zhaopin.thrift.rpc.invoker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.zhaopin.common.ThriftContext;
import com.zhaopin.common.container.RequestID;
import com.zhaopin.rpc.exception.RpcTransportException;
import com.zhaopin.thrift.rpc.RpcContext;
import com.zhaopin.thrift.rpc.codec.ThriftCodec;
import com.zhaopin.thrift.rpc.common.ServerNode;
import com.zhaopin.thrift.rpc.common.ThriftRequest;
import com.zhaopin.thrift.rpc.exception.RpcException;
import com.zhaopin.thrift.rpc.exception.TTransportException;
import com.zhaopin.thrift.rpc.monitor.ConsumerRegister;
import com.zhaopin.thrift.rpc.transport.ThriftFutures;

import brave.internal.HexCodec;

import com.zhaopin.thrift.rpc.transport.DefaultThriftFuture;
import com.zhaopin.thrift.rpc.transport.ThriftChannels;
import com.zhaopin.thrift.rpc.transport.ThriftChannel;
import com.zhaopin.thrift.rpc.transport.ThriftFuture;

public class DefaultInvoker extends AbstractInvoker {

	public static Map<String, ThriftCodec<?>> codecCache = new HashMap<String, ThriftCodec<?>>();

	public static final AtomicInteger ChannelIndex = new AtomicInteger(0);

	@Override
	public Object invoke(ThriftInvocation invoker) throws Throwable {
		ServerNode serverNode = selectProvider(invoker);
		// 版本兼容，需要设置远端服务的thrift版本
		invoker.setThriftVersion(serverNode.getThriftVersion());
		// 设置服务的实际地址
		invoker.setRemoteAddr(serverNode.getHost() + ":" + serverNode.getPort());
		// 序列化消息
		ThriftCodec<?> thriftCodec = getThriftCodec(invoker.getCodecHelper());
		// 获取输出流
		String service = invoker.getInvocation().getService();
		String method = invoker.getMethod();
		final int seqid = getSeqId(false);
		// thrift请求上下文联系的ID
		ThriftRequest thriftRequest = new ThriftRequest(seqid, service + ":" + method, thriftCodec, invoker.getArgs(),
				RequestID.getRequestID(), ThriftContext.getGrayToken(), invoker.getSpanId(), invoker.getParentId());
		// 设置thrift的版本
		thriftRequest.setThriftVersion(invoker.getThriftVersion());
		// 添加thrift的附件
		thriftRequest.addAttach(ThriftContext.getAttach());
		// 设置是否采样
		thriftRequest.setSampled(invoker.isSampled());
		ThriftFuture<Object> future = new DefaultThriftFuture(thriftRequest);
		final int channelIndex = getChannelIndex();
		try {
			ThriftChannel thriftChannel = ThriftChannels.connect(serverNode, channelIndex);
			if (thriftChannel == null) {
				throw new TTransportException(serverNode);
			}
			Object thriftResponse = null;
			ThriftFutures.instance.store(seqid, future, thriftChannel.getChannelId());
			thriftChannel.send(thriftRequest);
			if (thriftChannel.isCreate()) {
				// 需要注册consumer
				ConsumerRegister.register(invoker.getInvocation().getGroup(), service, invoker.getGrayVersion());
			}
			long timeout = invoker.getInvocation().getTimeout();
			if (timeout < 0) {
				thriftResponse = future.getResponse();
			} else {
				thriftResponse = future.getResponse(timeout);
			}
			return thriftResponse;
		} catch (TTransportException cause) {
			String remoteAddr = invoker.getRemoteAddr();
			LOGGER.error("transport exception {} {} {}", remoteAddr, invoker.getGrayVersion(),
					HexCodec.toLowerHex(invoker.getSpanId()), cause);
			// 一旦发生transport的异常, 移除该链接
			// 标记该服务为临时失效节点,最可能得原因是服务提供者宕机，但是zookeeper并没有通知到,在这段时间差内，标记该服务为临时失败
			serverNode.setTemporaryFailure();
			ThriftChannels.disconnect(serverNode, channelIndex);
			throw cause;
		} catch (RpcException cause) {
			if (cause.getCause() instanceof TTransportException) {
				String reason = "invoke " + service + ":" + method + " " + invoker.getRemoteAddr() + " fail!";
				throw new RpcTransportException(reason, cause.getCause());
			} else if (cause.getCause() instanceof org.apache.thrift.transport.TTransportException) {
				String reason = "invoke " + service + ":" + method + " " + invoker.getRemoteAddr() + " fail!";
				throw new RpcTransportException(reason, cause.getCause());
			}
			throw cause.getCause();
		}
	}

	protected int getChannelIndex() {
		int index = ChannelIndex.getAndIncrement() % RpcContext.channels;
		if (index > 1000000) {
			ChannelIndex.set(0);
		}
		return index;
	}

	protected ThriftCodec<?> getThriftCodec(String codecHelper) {
		ThriftCodec<?> thriftCodec = codecCache.get(codecHelper);
		if (thriftCodec != null) {
			return thriftCodec;
		}
		try {
			Class<?> cls = Thread.currentThread().getContextClassLoader().loadClass(codecHelper);
			thriftCodec = (ThriftCodec<?>) cls.newInstance();
			codecCache.put(codecHelper, thriftCodec);
			return thriftCodec;
		} catch (ClassNotFoundException exp) {
			LOGGER.error("exception", exp);
			throw new RpcException("" + codecHelper + " do not exist", exp);
		} catch (IllegalAccessException exp) {
			LOGGER.error("exception", exp);
			throw new RpcException("" + codecHelper + " access fail", exp);
		} catch (InstantiationException exp) {
			LOGGER.error("exception", exp);
			throw new RpcException("" + codecHelper + " instant fail", exp);
		}
	}

	protected void logInfo(Throwable error, ThriftInvocation invoker) {
		LOGGER.info("thrift invoke exception of {}", HexCodec.toLowerHex(invoker.getSpanId()), error);
	}

	protected void logError(Throwable error, ThriftInvocation invoker) {
		LOGGER.info("thrift invoke exception of {}", HexCodec.toLowerHex(invoker.getSpanId()), error);
	}
}
