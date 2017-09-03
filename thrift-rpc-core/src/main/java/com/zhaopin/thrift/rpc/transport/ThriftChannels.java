package com.zhaopin.thrift.rpc.transport;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.google.common.collect.Maps;
import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.thrift.rpc.common.ServerNode;

public class ThriftChannels {

	public static Logger LOGGER = LoggerFactory.getLogger(ThriftChannels.class);

	static {
		closeConnectionOnShutdown();
	}
	// 所有的读写锁
	private static Map<String, ReadWriteLock> readWriteLocks = Maps.newConcurrentMap();
	// 所有的连接通道
	private static volatile Map<String, ThriftChannel> thriftChannels = Maps.newConcurrentMap();

	public static ThriftChannel connect(ServerNode serverNode, int channelIndex) {
		return connect(serverNode.getHost(), serverNode.getPort(), channelIndex);
	}

	public static void disconnect(ServerNode serverNode, int channelIndex) {
		disconnect(serverNode.getHost(), serverNode.getPort(), channelIndex);
	}

	private synchronized static ReadWriteLock requireLock(String key) {
		ReadWriteLock readWriteLock = readWriteLocks.get(key);
		if (readWriteLock == null) {
			readWriteLock = new ReentrantReadWriteLock(false);
			readWriteLocks.put(key, readWriteLock);
		}
		return readWriteLock;
	}

	public static ThriftChannel connect(String host, int port, int channelIndex) {
		// 全部同步代码块的逻辑有问题
		String key = String.format("%s:%d-%d", host, port, channelIndex);
		ReadWriteLock readWriteLock = requireLock(key);
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			ThriftChannel instance = thriftChannels.get(key);
			if (instance != null && instance.isConnected()) {
				instance.setCreate(false);
				return instance;
			}
		} finally {
			readLock.unlock();
		}
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			ThriftChannel instance = thriftChannels.get(key);
			if (instance != null && instance.isConnected()) {
				instance.setCreate(false);
				return instance;
			}
			instance = new ThriftChannel(host, port);
			if (instance.isConnected()) {
				thriftChannels.put(key, instance);
				instance.setCreate(true);
				return instance;
			}
			return null;
		} finally {
			writeLock.unlock();
		}
	}

	public static void disconnect(String host, int port, int channelIndex) {
		final String key = String.format("%s:%d-%d", host, port, channelIndex);
		ReadWriteLock readWriteLock = requireLock(key);
		ThriftChannel thriftChannel = null;
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			thriftChannel = thriftChannels.remove(key);
		} finally {
			writeLock.unlock();
		}
		if (thriftChannel != null) {
			thriftChannel.close();
		}
	}

	public static void closeConnectionOnShutdown() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				for (Entry<String, ThriftChannel> entry : thriftChannels.entrySet()) {
					ThriftChannel thriftChannel = entry.getValue();
					if (thriftChannel != null) {
						thriftChannel.close();
					}
				}

			}
		}));
	}
}
