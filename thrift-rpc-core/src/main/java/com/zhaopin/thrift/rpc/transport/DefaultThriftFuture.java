package com.zhaopin.thrift.rpc.transport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.zhaopin.thrift.rpc.common.ThriftRequest;
import com.zhaopin.thrift.rpc.exception.RpcException;

public class DefaultThriftFuture implements ThriftFuture<Object> {

	private final Lock lock = new ReentrantLock();

	private final Condition done = lock.newCondition();

	private Object response = null;

	private Throwable exception;
	// 对应的请求
	private ThriftRequest thriftRequest;
	// 是否发生了错误
	private final AtomicBoolean hasError = new AtomicBoolean(false);

	private final AtomicBoolean hasResult = new AtomicBoolean(false);

	private int levelId = 0;

	public DefaultThriftFuture(ThriftRequest thriftRequest) {
		this.thriftRequest = thriftRequest;
	}

	public ThriftRequest getThriftRequest() {
		return thriftRequest;
	}

	@Override
	public void onComplete(Object response) {
		lock.lock();
		try {
			this.response = response;
			hasResult.set(true);
			done.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public void onError(Throwable exception) {
		lock.lock();
		try {
			hasError.getAndSet(true);
			this.exception = exception;
			this.response = null;
			done.signalAll();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Object getResponse(long timeout) throws TimeoutException {
		if (!isDone()) {
			long start = System.currentTimeMillis();
			try {
				lock.lock();
				while (!isDone()) {
					try {
						done.await(timeout, TimeUnit.MILLISECONDS);
						if (isDone() || System.currentTimeMillis() - start >= timeout) {
							break;
						}
					} catch (InterruptedException fail) {
						onError(fail);
					}
				}
			} finally {
				lock.unlock();
			}
		}
		if (!isDone()) {
			throw new TimeoutException();
		}
		if (this.hasError.get()) {
			throw new RpcException(exception);
		}
		return this.response;
	}

	@Override
	public Object getResponse() throws TimeoutException {
		if (!isDone()) {
			try {
				lock.lock();
				while (!isDone()) {
					try {
						done.await();
						if (isDone()) {
							break;
						}
					} catch (InterruptedException fail) {
						onError(fail);
					}
				}
			} finally {
				lock.unlock();
			}
		}
		if (this.hasError.get()) {
			throw new RpcException(exception);
		}
		return this.response;
	}

	private boolean isDone() {
		return hasResult.get() || hasError.get();
	}

	public Throwable getException() {
		return exception;
	}

	public boolean occurError() {
		return hasError.get();
	}

	public void setThriftRequest(ThriftRequest thriftRequest) {
		this.thriftRequest = thriftRequest;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

}
