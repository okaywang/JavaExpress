package com.zhaopin.thrift.rpc.server;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.thrift.rpc.config.ServerDefinition;
import com.zhaopin.thrift.rpc.protocol.ThriftDispatcher;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

public class ThriftServerTransport {

	public static final Logger LOGGER = LoggerFactory.getLogger(ThriftServerTransport.class);
	// ∑˛ŒÒ∂À∆Ù∂Ø ≈‰÷√
	private ServerDefinition defination;

	private EventLoopGroup bossGroup = null;

	private EventLoopGroup workerGroup = null;

	private Channel channel;

	public ThriftServerTransport(final ServerDefinition defination) {
		this.defination = defination;
	}

	public void start() {
		ServerBootstrap bootstrap = new ServerBootstrap();
		String osName = System.getProperty("os.name");
		Class<? extends ServerChannel> channelClass;
		if (osName != null && osName.toLowerCase().startsWith("linux")) {
			channelClass = EpollServerSocketChannel.class;
			this.bossGroup = new EpollEventLoopGroup();
			this.workerGroup = new EpollEventLoopGroup();
		} else {
			channelClass = NioServerSocketChannel.class;
			this.bossGroup = new NioEventLoopGroup();
			this.workerGroup = new NioEventLoopGroup();
		}
		applyTcpOptions(bootstrap);
		bootstrap.group(bossGroup, workerGroup);
		bootstrap.channel(channelClass);
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(defination.getMaxFrmSize(), 0, 4));
				ch.pipeline().addLast(new ThriftDispatcher());
			}
		});
		final int serverPort = this.defination.getServerPort();
		try {
			this.channel = bootstrap.bind(serverPort).addListener(new FutureListener<Void>() {
				@Override
				public void operationComplete(Future<Void> future) throws Exception {
					if (!future.isSuccess()) {
						LOGGER.info("thrift startup with port " + serverPort + " fail!");
						System.exit(-1);
					} else {
						System.out.println("thrift startup with port " + serverPort + " success.");
						LOGGER.info("thrift startup with port " + serverPort + " success.");
					}
				}
			}).sync().channel();
		} catch (Exception cause) {
			throw new IllegalStateException("thrift startup fail!", cause);
		}
	}

	private void applyTcpOptions(ServerBootstrap bootstrap) {
		bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		if (this.defination.getTcpSendBufferSize() > 0) {
			bootstrap.childOption(ChannelOption.SO_SNDBUF, this.defination.getTcpSendBufferSize());
		}
		if (this.defination.getTcpReceiveBufferSize() > 0) {
			bootstrap.childOption(ChannelOption.SO_RCVBUF, this.defination.getTcpReceiveBufferSize());
			bootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator());
		}
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, this.defination.isTcpKeepAlive());
		bootstrap.option(ChannelOption.SO_BACKLOG, this.defination.getAcceptBackLog());
	}

	public void stop() {
		try {
			channel.close().awaitUninterruptibly();
			if (workerGroup != null) {
				workerGroup.shutdownGracefully();
			}
			if (bossGroup != null) {
				bossGroup.shutdownGracefully();
			}
		} catch (Exception exp) {
			LOGGER.info("socket.io startup fail!", exp);
		}
	}
}
