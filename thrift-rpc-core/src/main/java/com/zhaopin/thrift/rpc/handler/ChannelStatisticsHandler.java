package com.zhaopin.thrift.rpc.handler;

import java.util.concurrent.atomic.AtomicInteger;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelState;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.ChannelGroup;

public class ChannelStatisticsHandler extends SimpleChannelHandler {

	public static final String NAME = ChannelStatisticsHandler.class.getSimpleName();

	private static final AtomicInteger channelCount = new AtomicInteger(0);

	private final ChannelGroup channels;

	public ChannelStatisticsHandler(ChannelGroup channels) {
		this.channels = channels;
	}

	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent event) throws Exception {

		if (event instanceof ChannelStateEvent) {
			ChannelStateEvent channelStateEvent = (ChannelStateEvent) event;
			if (channelStateEvent.getState() == ChannelState.OPEN) {
				if (Boolean.TRUE.equals(channelStateEvent.getValue())) {
					channelCount.incrementAndGet();
					channels.add(event.getChannel());
				} else {
					channelCount.decrementAndGet();
					channels.remove(event.getChannel());
				}
			}
		} else {
			ctx.sendUpstream(event);
		}

	}

}
