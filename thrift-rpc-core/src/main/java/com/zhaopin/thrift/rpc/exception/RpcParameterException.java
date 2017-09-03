package com.zhaopin.thrift.rpc.exception;

public class RpcParameterException extends RpcException {

	private static final long serialVersionUID = 1L;
	
	public RpcParameterException(String reason) {
		super(reason);
	}
}
