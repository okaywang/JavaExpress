package com.zhaopin.thrift.rpc.exception;

public class RpcNoProviderException extends RpcException {

	private static final long serialVersionUID = 1L;
	
	public RpcNoProviderException(String service, String func, String version, String group) {
		super("group: " + group + " " + service + "." + func + "#" + version + " 没有服务提供者!");
	}

}
