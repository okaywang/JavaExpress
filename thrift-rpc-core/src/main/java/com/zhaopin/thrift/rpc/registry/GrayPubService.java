package com.zhaopin.thrift.rpc.registry;

public class GrayPubService {
	// 分组的名称
	private String group;
	// 服务具体的版本
	private String version;
	// 修改时间
	private long time;

	public GrayPubService() {

	}

	public GrayPubService(String group, String version) {
		this.group = group;
		this.version = version;
		this.time = 0;
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

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
