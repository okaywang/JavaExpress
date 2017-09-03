package com.zhaopin.thrift.rpc.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;

public class RpcService implements Serializable, Comparable<RpcService> {

	public static Logger LOGGER = LoggerFactory.getLogger(RpcService.class);

	private static final long serialVersionUID = 1L;

	private final String serviceName;

	private final String version;
	// 服务Provider节点
	private List<ServerNode> serverNodes;

	public RpcService(String serviceName, String version) {
		this.serviceName = serviceName;
		this.version = version;
		this.serverNodes = new ArrayList<ServerNode>();
	}

	public List<ServerNode> getServerNodes() {
		// 将临时失败节点列表中的节点添加到服务节点中
		return serverNodes;
	}

	public void setServerNodes(List<ServerNode> serverNodes) {
		this.serverNodes = serverNodes;
	}

	public String getServiceName() {
		return serviceName;
	}

	public String getVersion() {
		return version;
	}

	public int compareTo(RpcService service) {
		int result = 0;
		if ((result = serviceName.compareTo(service.serviceName)) != 0) {
			return result;
		}
		if ((result = version.compareTo(service.version)) != 0) {
			return result;
		}
		return 0;
	}
}
