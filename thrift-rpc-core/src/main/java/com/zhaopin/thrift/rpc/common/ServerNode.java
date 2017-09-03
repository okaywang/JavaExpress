package com.zhaopin.thrift.rpc.common;

public class ServerNode implements Comparable<ServerNode> {
	// 在zookeeper中的唯一临时节点名称
	private transient String uniqueTag;
	// 服务所在的host
	private String host;
	// 服务所在的port
	private int port;
	// 服务的权重
	private int weight = 5;
	// thrift的版本
	private int thriftVersion;
	// 临时失败标志
	protected transient boolean temporaryFailure = false;
	// 第一次临时失败的时刻
	private transient long failureTime;

	public ServerNode() {
		this("", 0);
	}

	public ServerNode(String host, int port) {
		this(host, port, 5);
	}

	public ServerNode(String host, int port, int weight) {
		this.host = host;
		this.port = port;
		this.weight = weight;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isTemporaryFailure() {
		if (System.currentTimeMillis() - this.failureTime < 10 * 1000) {
			if (this.failureTime > 0) {
				resetTemporaryFailure();
			}
			return true;
		} else {
			return false;
		}
	}

	private void resetTemporaryFailure() {
		this.failureTime = -1;
		this.temporaryFailure = false;
	}

	public void setTemporaryFailure() {
		this.failureTime = System.currentTimeMillis();
		this.temporaryFailure = true;
	}

	public int compareTo(ServerNode other) {
		int result = 0;
		if ((result = host.compareTo(other.host)) != 0) {
			return result > 0 ? 1 : -1;
		}
		if ((result = port - other.port) != 0) {
			return result > 0 ? 1 : -1;
		}
		return 0;
	}

	public String getUniqueTag() {
		return uniqueTag;
	}

	public void setUniqueTag(String uniqueTag) {
		this.uniqueTag = uniqueTag;
	}

	public int getThriftVersion() {
		return thriftVersion;
	}

	public void setThriftVersion(int thriftVersion) {
		this.thriftVersion = thriftVersion;
	}

}
