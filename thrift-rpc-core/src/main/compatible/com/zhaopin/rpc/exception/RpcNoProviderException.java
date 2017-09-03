package com.zhaopin.rpc.exception;

import com.zhaopin.rpc.RpcException;

public class RpcNoProviderException extends RpcException {

	private static final long serialVersionUID = 1L;
	
	public RpcNoProviderException() {
		
	}
	
	public RpcNoProviderException(String message) {
		super(message);
	}

}
