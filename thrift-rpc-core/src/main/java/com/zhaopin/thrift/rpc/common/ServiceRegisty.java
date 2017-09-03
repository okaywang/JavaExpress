package com.zhaopin.thrift.rpc.common;

import java.util.ArrayList;
import java.util.List;

public class ServiceRegisty {
	// ����ʵ�ֵĽӿ�
	private final List<Class<?>> interfs = new ArrayList<Class<?>>();
	// ע�����ĵĵ�ַ
	private String registry;
	// ���������ķ���
	private String group;
	// ����İ汾
	private String version;
	// �����IP��ַ
	private String host;
	// ����Ķ˿�
	private int port;
	// �����ʵ��
	private Object impl;
	// �����Ȩ��
	private int weight = 5;
	// http��context
	private String context;
	// http�˿�
	private int httpPort;

	public void addInterf(Class<?> clz) {
		interfs.add(clz);
	}

	public List<Class<?>> getInterfs() {
		return interfs;
	}

	public String getRegistry() {
		return registry;
	}

	public void setRegistry(String registry) {
		this.registry = registry;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Object getImpl() {
		return impl;
	}

	public void setImpl(Object impl) {
		this.impl = impl;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
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
