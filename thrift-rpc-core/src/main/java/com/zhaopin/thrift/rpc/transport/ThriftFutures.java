package com.zhaopin.thrift.rpc.transport;

import java.util.concurrent.ConcurrentHashMap;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class ThriftFutures {

	public static ThriftFutures instance = new ThriftFutures();

	private ConcurrentHashMap<Integer, ThriftFuture<Object>> syncCallback = new ConcurrentHashMap<Integer, ThriftFuture<Object>>();

	private Cache<Integer, ThriftFuture<Object>> asyncCallback = CacheBuilder.newBuilder().maximumSize(5000000).build();

	private ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, ThriftFuture<Object>>> channelFutures = new ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, ThriftFuture<Object>>>();

	public void store(final int seqid, ThriftFuture<Object> callback, int channelId) {
		if (seqid % 2 == 0) {
			asyncCallback.put(seqid, callback);
		} else {
			syncCallback.put(seqid, callback);
		}
		ConcurrentHashMap<Integer, ThriftFuture<Object>> futures = channelFutures.get(channelId);
		if (futures == null) {
			futures = new ConcurrentHashMap<Integer, ThriftFuture<Object>>();
			futures.put(seqid, callback);
			channelFutures.put(channelId, futures);
		} else {
			futures.put(seqid, callback);
		}
	}

	public ThriftFuture<Object> get(final int seqid) {
		if (seqid % 2 == 0) {
			return asyncCallback.getIfPresent(seqid);
		} else {
			return syncCallback.get(seqid);
		}
	}

	public void remove(final int seqid, int channelId) {
		if (seqid % 2 == 0) {
			asyncCallback.invalidate(seqid);
		} else {
			syncCallback.remove(seqid);
		}
		ConcurrentHashMap<Integer, ThriftFuture<Object>> futures = channelFutures.get(channelId);
		if (futures != null) {
			futures.remove(seqid);
			if (futures.size() <= 0) {
				channelFutures.remove(channelId);
			}
		}
	}

	public ConcurrentHashMap<Integer, ThriftFuture<Object>> getFutures(int channelId) {
		return this.channelFutures.get(channelId);
	}

	public ConcurrentHashMap<Integer, ThriftFuture<Object>> removeFutures(int channelId) {
		return this.channelFutures.remove(channelId);
	}
}
