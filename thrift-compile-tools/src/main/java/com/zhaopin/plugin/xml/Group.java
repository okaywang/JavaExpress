package com.zhaopin.plugin.xml;

import java.util.ArrayList;
import java.util.List;

public class Group {
	// 分组的名称
	private String name;
	//
	private List<Service> services = new ArrayList<Service>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addService(Service service) {
		services.add(service);
	}

	public List<Service> getServices() {
		return services;
	}
}
