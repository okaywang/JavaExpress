package com.zhaopin.plugin.xml;

import java.util.ArrayList;
import java.util.List;

public class Services {
	// ESB�еķ���(apiopen_group)
	private String apiOpenGroup;
	// ���������(name)
	private String name;
	// �ӿڷ���İ汾(version)
	private String version;
	// �ӿ���(interface)
	private String interf;
	// wiki���ļ���
	private String wiki;
	// �ӿڰ����ķ���
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
