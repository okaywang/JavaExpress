package com.zhaopin.thrift.rpc.handler;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.util.HashedWheelTimer;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class ServerTimer extends HashedWheelTimer implements Closeable {
	
	public ServerTimer(String prefix) {
		this(prefix, 100, TimeUnit.MILLISECONDS, 512);
	}
	
	public ServerTimer(String prefix, long tickDuration, TimeUnit unit, int ticksPerWheel) {
		
		super(new ThreadFactoryBuilder().setNameFormat(prefix + "-timer-%s").setDaemon(true).build(), tickDuration, unit, ticksPerWheel);
	}

	public void close() throws IOException {
		this.stop();
	}

}
