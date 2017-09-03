package com.zhaopin.thrift.rpc.protocol;

import com.zhaopin.thrift.rpc.executor.ThriftEventExecutor;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class ThriftDispatcher extends ChannelInboundHandlerAdapter {

	private ThriftEventExecutor thriftEventExecutor = new ThriftEventExecutor();

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof ByteBuf) {
			ByteBuf byteBuf = (ByteBuf) msg;
			// 在业务线程中复用
			byteBuf.retain();
			byteBuf.skipBytes(4);
			processRequest(ctx, byteBuf);
		}
		super.channelRead(ctx, msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	private void processRequest(final ChannelHandlerContext ctx, final ByteBuf byteBuf) {
		thriftEventExecutor.processRequest(ctx, byteBuf);
	}

}
