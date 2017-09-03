package com.zhaopin.thrift.rpc.server;

import java.util.Set;

/**
 * 服务在zk中的记录
 * 
 */
public class ServerNodeInfo {
	// 服务的host
	private String host;
	// 服务的端口
	private int port;
	// 服务的权重
	private int weight;
	// 服务启动的路径
	private String path;
	// thrift的版本
	private int thriftVersion;
	// 所有的方法名
	private Set<String> methods;
	// http的context
	private String context;
	// http端口
	private int httpPort;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getThriftVersion() {
		return thriftVersion;
	}

	public void setThriftVersion(int thriftVersion) {
		this.thriftVersion = thriftVersion;
	}

	public Set<String> getMethods() {
		return methods;
	}

	public void setMethods(Set<String> methods) {
		this.methods = methods;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public int getHttpPort() {
		return httpPort;
	}

	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}

}
