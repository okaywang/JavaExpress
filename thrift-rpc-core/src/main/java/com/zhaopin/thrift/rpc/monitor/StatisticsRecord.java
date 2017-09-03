package com.zhaopin.thrift.rpc.monitor;

public class StatisticsRecord {
	// ���õķ���
	private String service;
	// ���õķ���
	private String method;
	// ����İ汾
	private String version;
	// ����ķ���
	private String group;
	// ������ṩ��
	private String provider;
	// ������õ�ʱ��
	private long startTime;
	// ����������ĵ�ʱ��
	private long cost;
	// ��������Ƿ�ɹ�
	private boolean success;
	// ������õĿͻ��˵�ַ
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
