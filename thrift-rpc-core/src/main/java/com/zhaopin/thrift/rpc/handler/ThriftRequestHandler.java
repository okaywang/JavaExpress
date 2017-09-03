package com.zhaopin.thrift.rpc.handler;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.thrift.rpc.common.TMessage;
import com.zhaopin.thrift.rpc.common.TMessageType;
import com.zhaopin.thrift.rpc.common.ThriftRequest;
import com.zhaopin.thrift.rpc.protocol.ThriftProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class ThriftRequestHandler extends ChannelOutboundHandlerAdapter {
	
	public static Logger LOGGER = LoggerFactory.getLogger(ThriftRequestHandler.class);

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if (msg instanceof ThriftRequest) {
			// 进行编码
			CompositeByteBuf compositeByteBuf = ctx.alloc().compositeBuffer();
			ByteBuf header = ctx.alloc().heapBuffer(4);
			ThriftProtocol oprot = new ThriftProtocol(ctx.alloc().directBuffer(1024));
			ThriftRequest thriftRequest = (ThriftRequest) msg;
			final TMessage thriftMessage = new TMessage(thriftRequest.getService(), TMessageType.CALL,
					thriftRequest.getSeqid(), thriftRequest.getTraceId(), thriftRequest.getGrayToken(),
					thriftRequest.getParentId(), thriftRequest.getSpanId());
			thriftMessage.setThriftVersion(thriftRequest.getThriftVersion());
			thriftMessage.setSampled(thriftRequest.isSampled());
			// 设置附件
			thriftMessage.setAttachment(thriftRequest.getAttachment());
			oprot.writeMessageBegin(thriftMessage);
			thriftRequest.getThriftCodec().encode(oprot, thriftRequest.getArgs());
			oprot.writeMessageEnd();
			header.writeInt(oprot.getBuffer().readableBytes());
			compositeByteBuf.addComponents(true, header, oprot.getBuffer());
			ctx.channel().writeAndFlush(compositeByteBuf).addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (!future.isSuccess()) {
						LOGGER.warn("send request fail {}", thriftMessage.getTraceId(), future.cause());
					}
				}
			});
		} else {
			super.write(ctx, msg, promise);
		}
	}

}
