package com.zhaopin.rpc.exception;

import com.zhaopin.rpc.RpcException;

public class RpcBusinessException extends RpcException {

	private static final long serialVersionUID = 1L;

	public RpcBusinessException(Throwable fail) {
		super(fail);
	}

}
