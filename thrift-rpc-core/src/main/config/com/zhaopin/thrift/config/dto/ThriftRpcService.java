package com.zhaopin.thrift.config.dto;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;

public class ThriftRpcService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ThriftRpcService.class);
	// bean的ID
	private String id;
	// 服务的版本
	private String version;
	// 服务的分组
	private String group;
	// 服务的实现类
	private String ref;
	// 服务的权重(1-9)
	private int weight = 5;
	// 项目定义
	private ThriftRpcServer server;
	// 注册中心
	private ThriftRpcRegistry registry;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ThriftRpcRegistry getRegistry() {
		return registry;
	}

	public void setRegistry(ThriftRpcRegistry registry) {
		this.registry = registry;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public ThriftRpcServer getServer() {
		return server;
	}

	public void setServer(ThriftRpcServer server) {
		this.server = server;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
