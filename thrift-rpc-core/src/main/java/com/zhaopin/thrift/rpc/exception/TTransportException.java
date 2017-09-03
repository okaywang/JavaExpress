package com.zhaopin.thrift.rpc.exception;

import com.zhaopin.thrift.rpc.common.ServerNode;

public class TTransportException extends RpcException {

	private static final long serialVersionUID = 1L;

	public TTransportException() {

	}

	public TTransportException(String message) {
		super(message);
	}

	public TTransportException(ServerNode serverNode) {
		this(serverNode.getHost() + ":" + serverNode.getPort());
	}

	public TTransportException(Throwable cause) {
		super(cause);
	}

	public TTransportException(String service, String func, String version, String group) {
		super("group: " + group + " " + service + "." + func + "#" + version + " 无法远程调用!");
	}

	public TTransportException(String message, Throwable cause) {
		super(message, cause);
	}
}
