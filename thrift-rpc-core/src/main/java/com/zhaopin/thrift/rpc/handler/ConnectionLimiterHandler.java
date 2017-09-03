package com.zhaopin.thrift.rpc.handler;

import java.util.concurrent.atomic.AtomicInteger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class ConnectionLimiterHandler extends SimpleChannelUpstreamHandler {
	// 连接总数
	private AtomicInteger connectionCount;
	// 最大连接数
	private final int maxConnection;
	
	public ConnectionLimiterHandler(int maxConnection) {
		this.maxConnection = maxConnection;
		this.connectionCount = new AtomicInteger(0);
	}

	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent event) throws Exception {
		if (this.maxConnection > 0) {
			if (this.connectionCount.incrementAndGet() > this.maxConnection) {
				ctx.getChannel().close();
				return;
			}
		}
		super.channelOpen(ctx, event);
	}

	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent event) throws Exception {
		if (this.maxConnection > 0) {
			this.connectionCount.decrementAndGet();
		}
		super.channelClosed(ctx, event);
	}
}
