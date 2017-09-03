package com.zhaopin.thrift.rpc.server;

import java.util.Set;

/**
 * ������zk�еļ�¼
 * 
 */
public class ServerNodeInfo {
	// �����host
	private String host;
	// ����Ķ˿�
	private int port;
	// �����Ȩ��
	private int weight;
	// ����������·��
	private String path;
	// thrift�İ汾
	private int thriftVersion;
	// ���еķ�����
	private Set<String> methods;
	// http��context
	private String context;
	// http�˿�
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
