package com.zhaopin.rpc.exception;

import com.zhaopin.rpc.RpcException;

public class RpcTimeoutException extends RpcException {

	private static final long serialVersionUID = 1L;
	
	public RpcTimeoutException() {
		
	}
	
	public RpcTimeoutException(String reason) {
		super(reason);
	}
	
	public RpcTimeoutException(Throwable fail) {
		super(fail);
	}
	
	public RpcTimeoutException(String reason, Throwable fail) {
		super(reason, fail);
	}

}
