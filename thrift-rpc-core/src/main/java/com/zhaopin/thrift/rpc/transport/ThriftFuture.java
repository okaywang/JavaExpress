package com.zhaopin.thrift.rpc.transport;

import java.util.concurrent.TimeoutException;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.thrift.rpc.common.ThriftRequest;

public interface ThriftFuture<T> {
	
	public static Logger LOGGER = LoggerFactory.getLogger(ThriftFuture.class);

	public void onComplete(T response);

	public void onError(Throwable exception);

	public T getResponse(long timeout) throws TimeoutException;

	public T getResponse() throws TimeoutException;

	public boolean occurError();
	
	public ThriftRequest getThriftRequest();

}
