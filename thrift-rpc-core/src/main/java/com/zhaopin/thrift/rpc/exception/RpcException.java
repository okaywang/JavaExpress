package com.zhaopin.thrift.rpc.exception;

public class RpcException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RpcException() {
		super();
	}
	
	public RpcException(String reason) {
		super(reason);
	}
	
	public RpcException(Throwable cause) {
		super(cause);
	}
	
	public RpcException(String message, Throwable cause) {
		super(message, cause);
	}

}
