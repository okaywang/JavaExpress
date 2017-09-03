package com.zhaopin.thrift.rpc.common;

import java.util.ArrayList;
import java.util.List;

public class ServiceRegisty {
	// 服务实现的接口
	private final List<Class<?>> interfs = new ArrayList<Class<?>>();
	// 注册中心的地址
	private String registry;
	// 服务所属的分组
	private String group;
	// 服务的版本
	private String version;
	// 服务的IP地址
	private String host;
	// 服务的端口
	private int port;
	// 服务的实现
	private Object impl;
	// 服务的权重
	private int weight = 5;
	// http的context
	private String context;
	// http端口
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
