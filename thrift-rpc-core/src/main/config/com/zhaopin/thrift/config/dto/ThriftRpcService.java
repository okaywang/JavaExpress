package com.zhaopin.thrift.config.dto;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;

public class ThriftRpcService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ThriftRpcService.class);
	// bean��ID
	private String id;
	// ����İ汾
	private String version;
	// ����ķ���
	private String group;
	// �����ʵ����
	private String ref;
	// �����Ȩ��(1-9)
	private int weight = 5;
	// ��Ŀ����
	private ThriftRpcServer server;
	// ע������
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
