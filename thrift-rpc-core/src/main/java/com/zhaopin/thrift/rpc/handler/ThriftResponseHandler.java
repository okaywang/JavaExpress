package com.zhaopin.thrift.rpc.handler;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.apache.thrift.TException;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.thrift.rpc.codec.ThriftCodec;
import com.zhaopin.thrift.rpc.common.TMessage;
import com.zhaopin.thrift.rpc.common.TMessageType;
import com.zhaopin.thrift.rpc.exception.ChannelCloseException;
import com.zhaopin.thrift.rpc.protocol.TProtocol;
import com.zhaopin.thrift.rpc.protocol.ThriftProtocol;
import com.zhaopin.thrift.rpc.transport.ThriftChannel;
import com.zhaopin.thrift.rpc.transport.ThriftFuture;
import com.zhaopin.thrift.rpc.transport.ThriftFutures;
import com.zhaopin.thrift.rpc.util.RuntimeExceptionCodec;
import com.zhaopin.thrift.rpc.util.ThriftExceptionCodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ThriftResponseHandler extends ChannelInboundHandlerAdapter {

	public static Logger LOGGER = LoggerFactory.getLogger(ThriftResponseHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof ByteBuf) {
			ByteBuf bytebuf = (ByteBuf) msg;
			bytebuf.skipBytes(4);
			bytebuf.markReaderIndex();
			ThriftProtocol iprot = new ThriftProtocol(bytebuf);
			TMessage tmsg = iprot.readMessageBegin();
			// 进行消息的解码
			try {
				ThriftFuture<Object> future = ThriftFutures.instance.get(tmsg.getSeqid());
				if (tmsg.getType() == TMessageType.REPLY) {
					// 对结果进行解码
					ThriftCodec<?> codec = future.getThriftRequest().getThriftCodec();
					Object result = codec.decode(iprot);
					future.onComplete(result);
				} else if (tmsg.getType() == TMessageType.EXCEPTION) {
					// 抛出的异常
					TException cause = getThriftException(iprot, bytebuf);
					future.onError(cause);
				} else if (tmsg.getType() == TMessageType.RUNTIME_EXCEPTION) {
					// 抛出的异常
					Throwable cause = getRuntimeException(iprot, bytebuf);
					future.onError(cause);
				}
			} finally {
				int channelId = ctx.channel().attr(ThriftChannel.CHANNEL_ID_ATTR).get();
				ThriftFutures.instance.remove(tmsg.getSeqid(), channelId);
			}
		}
		super.channelRead(ctx, msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
		super.channelReadComplete(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		int channelId = ctx.channel().attr(ThriftChannel.CHANNEL_ID_ATTR).get();
		try {
			ConcurrentHashMap<Integer, ThriftFuture<Object>> futures = ThriftFutures.instance.getFutures(channelId);
			if (futures != null) {
				notifyCaller(futures);
			}
		} finally {
			ThriftFutures.instance.removeFutures(channelId);
		}
		super.channelInactive(ctx);
	}

	private void notifyCaller(ConcurrentHashMap<Integer, ThriftFuture<Object>> futures) {
		try {
			for (ThriftFuture<Object> future : futures.values()) {
				future.onError(new ChannelCloseException());
			}
		} catch (Exception exp) {
			LOGGER.error("notify exception", exp);
		}
	}

	private TException getThriftException(TProtocol iprot, ByteBuf bytebuf) {
		TException cause = null;
		String errorFile = null;
		try {
			cause = ThriftExceptionCodec.read(iprot);
			LOGGER.warn("exception", cause);
			return cause;
		} catch (Throwable exp) {
			LOGGER.error("exception", exp);
			errorFile = "runtime-" + UUID.randomUUID().toString().replace("-", "") + ".err";
			dumpMessage(errorFile, bytebuf);
			return new TException("Unknown exception, please check " + errorFile + " file.");
		}
	}

	private Throwable getRuntimeException(TProtocol iprot, ByteBuf bytebuf) {
		String errorFile = null;
		try {
			Throwable cause = RuntimeExceptionCodec.read(iprot);
			LOGGER.warn("exception", cause);
			return cause;
		} catch (Throwable exp) {
			// 如果出现问题,需要将这里的消息dump到文件中
			LOGGER.error("exception", exp);
			errorFile = "runtime-" + UUID.randomUUID().toString().replace("-", "") + ".err";
			dumpMessage(errorFile, bytebuf);
			return new IllegalStateException("Unknown exception, please check " + errorFile + " file.");
		}
	}

	private void dumpMessage(String errorFile, ByteBuf bytebuf) {
		bytebuf.resetReaderIndex();
		byte[] message = new byte[bytebuf.readableBytes()];
		bytebuf.readBytes(message);
		try {
			FileUtils.writeByteArrayToFile(new File(errorFile), message);
		} catch (IOException exp) {
			LOGGER.error("exception", exp);
		}
	}
}
