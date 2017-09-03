package com.zhaopin.plugin.xml;

import java.util.ArrayList;
import java.util.List;

public class Services {
	// ESB中的分组(apiopen_group)
	private String apiOpenGroup;
	// 服务的名字(name)
	private String name;
	// 接口服务的版本(version)
	private String version;
	// 接口类(interface)
	private String interf;
	// wiki的文件名
	private String wiki;
	// 接口包含的方法
	private List<Service> services = new ArrayList<Service>();
	
	public String getApiOpenGroup() {
		return apiOpenGroup;
	}
	
	public void setApiOpenGroup(String apiOpenGroup) {
		this.apiOpenGroup = apiOpenGroup;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getInterf() {
		return interf;
	}
	
	public void setInterf(String interf) {
		this.interf = interf;
	}
	
	public String getWiki() {
		return wiki;
	}
	
	public void setWiki(String wiki) {
		this.wiki = wiki;
	}
	
	public List<Service> getServices() {
		return services;
	}
	
	public void addServices(Service service) {
		this.services.add(service);
	}

}
