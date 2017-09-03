package com.zhaopin.thrift.rpc.transport;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.thrift.rpc.Constants;
import com.zhaopin.thrift.rpc.common.ThriftRequest;
import com.zhaopin.thrift.rpc.exception.TTransportException;
import com.zhaopin.thrift.rpc.handler.NamedThreadFactory;
import com.zhaopin.thrift.rpc.handler.ThriftRequestHandler;
import com.zhaopin.thrift.rpc.handler.ThriftResponseHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.AttributeKey;

public class ThriftChannel {

	public static Logger LOGGER = LoggerFactory.getLogger(ThriftChannel.class);

	public static final AttributeKey<Integer> CHANNEL_ID_ATTR = AttributeKey.newInstance("channelId");

	private static final int MAX_PKG_SIZE = Constants.MAX_FRAME_SIZE;

	private static AtomicInteger channelIndex = new AtomicInteger();

	private static Bootstrap bootstrap = bootstrap();

	private Channel channel = null;
	// 是否是新创建的通道
	private boolean create = false;

	public ThriftChannel(String host, int port) {
		connect(host, port);
	}

	private static Bootstrap bootstrap() {
		EventLoopGroup group = new NioEventLoopGroup(Constants.DEFAULT_IO_THREADS,
				new NamedThreadFactory("nettyworker", true));
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.TCP_NODELAY, true)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
				.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel channel) throws Exception {
				channel.pipeline().addLast(new ThriftRequestHandler());
				channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(MAX_PKG_SIZE, 0, 4));
				channel.pipeline().addLast(new ThriftResponseHandler());
			}
		});
		return bootstrap;
	}

	private void connect(String host, int port) {
		if (channel != null && channel.isActive()) {
			return;
		}
		long start = System.currentTimeMillis();
		ChannelFuture channleFuture = bootstrap.connect(new InetSocketAddress(host, port));
		while (!channleFuture.isDone()) {
			try {
				channleFuture.await(100, TimeUnit.MILLISECONDS);
			} catch (InterruptedException exp) {
				LOGGER.warn("exception", exp);
			}
		}
		long cost = System.currentTimeMillis() - start;
		LOGGER.info("connect to {}:{} cost {} ms!", host, port, cost);
		if (!channleFuture.isSuccess()) {
			LOGGER.warn("connect to " + host + ":" + port + " fail!", channleFuture.cause());
			throw new TTransportException(channleFuture.cause());
		}
		this.channel = channleFuture.channel();
		this.channel.attr(CHANNEL_ID_ATTR).set(channelIndex.getAndIncrement());
		LOGGER.info("connect to {}:{} result {}", host, port, this.channel.isActive());
	}

	public void send(final ThriftRequest request) throws TTransportException {
		ChannelFuture future = this.channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (!future.isSuccess()) {
					LOGGER.warn("send request fail {}", request.getTraceId(), future.cause());
				}
			}
		});
		Throwable cause = future.cause();
		if (cause != null) {
			LOGGER.warn("exception", cause);
			throw new TTransportException(cause);
		}
	}

	public boolean isConnected() {
		return this.channel != null && this.channel.isActive();
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public int getChannelId() {
		return this.channel.attr(CHANNEL_ID_ATTR).get();
	}

	public void close() {
		try {
			if (this.channel != null) {
				this.channel.close();
			}
		} catch (Throwable fail) {
			LOGGER.warn("exception", fail);
		}
	}

	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}

}
