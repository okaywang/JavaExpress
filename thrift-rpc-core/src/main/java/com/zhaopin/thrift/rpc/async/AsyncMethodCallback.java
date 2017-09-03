package com.zhaopin.thrift.rpc.async;

public interface AsyncMethodCallback<T> {
	
	public void onComplete(T result);
	
	public void onError(Throwable exception);

}
