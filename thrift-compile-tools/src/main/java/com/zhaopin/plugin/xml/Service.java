package com.zhaopin.plugin.xml;

public class Service {
	// 方法的名称
	private String name;
	// http类型
	private String method;
	// 服务描述
	private String desc;
	// apiopen中方法的名称
	private String serviceName;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
