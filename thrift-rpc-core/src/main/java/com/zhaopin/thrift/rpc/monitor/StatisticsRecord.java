package com.zhaopin.thrift.rpc.monitor;

public class StatisticsRecord {
	// 调用的服务
	private String service;
	// 调用的方法
	private String method;
	// 服务的版本
	private String version;
	// 服务的分组
	private String group;
	// 服务的提供者
	private String provider;
	// 服务调用的时刻
	private long startTime;
	// 服务调用消耗的时间
	private long cost;
	// 服务调用是否成功
	private boolean success;
	// 服务调用的客户端地址
	private String client;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
}
