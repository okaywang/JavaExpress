package com.zhaopin.thrift.rpc.transport;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import com.zhaopin.common.ThriftContext;
import com.zhaopin.common.container.RequestID;
import com.zhaopin.thrift.rpc.async.AsyncMethodCallback;
import com.zhaopin.thrift.rpc.common.ThriftRequest;
import com.zhaopin.thrift.rpc.handler.NamedThreadFactory;

public class AsyncThriftFuture implements ThriftFuture<Object> {

	/**
	 * 全异步的流程下,线程数是CPU的核数
	 */
	public static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
			new NamedThreadFactory("business", true));

	private final AsyncMethodCallback<Object> callback;
	// 对应的请求
	private final ThriftRequest thriftRequest;

	public AsyncThriftFuture(ThriftRequest thriftRequest, AsyncMethodCallback<Object> callback) {
		this.thriftRequest = thriftRequest;
		this.callback = callback;
	}

	@Override
	public void onComplete(final Object response) {
		executor.submit(new Runnable() {
			@Override
			public void run() {
				RequestID.setRequestID(thriftRequest.getTraceId());
				ThriftContext.setGrayToken(thriftRequest.getGrayToken());
				callback.onComplete(response);
			}
		});
	}

	@Override
	public void onError(final Throwable exception) {
		executor.submit(new Runnable() {
			@Override
			public void run() {
				RequestID.setRequestID(thriftRequest.getTraceId());
				ThriftContext.setGrayToken(thriftRequest.getGrayToken());
				callback.onError(exception);
			}
		});
	}

	@Override
	public byte[] getResponse(long timeout) throws TimeoutException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] getResponse() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean occurError() {
		return false;
	}

	@Override
	public ThriftRequest getThriftRequest() {
		return this.thriftRequest;
	}
}
