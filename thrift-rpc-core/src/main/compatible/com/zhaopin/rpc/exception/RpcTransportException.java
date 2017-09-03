package com.zhaopin.rpc.exception;

import com.zhaopin.rpc.RpcException;

public class RpcTransportException extends RpcException {

	private static final long serialVersionUID = 1L;

	public RpcTransportException(Throwable cause) {
		super(cause);
	}

	public RpcTransportException(String reason, Throwable fail) {
		super(reason, fail);
	}

}
