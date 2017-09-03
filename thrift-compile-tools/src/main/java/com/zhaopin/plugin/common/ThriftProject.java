package com.zhaopin.plugin.common;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author shunli.gao
 *
 */
public class ThriftProject {
	// 所有的struct
	private final Set<ThriftStruct> thriftStructs = new HashSet<ThriftStruct>();
	//  所有的service
	private final Set<ThriftService> thriftServices = new HashSet<ThriftService>();
	
	public void addThriftObject(ThriftObject thriftObject) {
		if (thriftObject instanceof ThriftStruct) {
			addThriftStruct((ThriftStruct)thriftObject);
		} else if (thriftObject instanceof ThriftService) {
			addThriftService((ThriftService)thriftObject);
		}
	}
	
	public void addThriftStruct(ThriftStruct thriftStruct) {
		this.thriftStructs.add(thriftStruct);
	}
	
	public void addThriftStructs(Set<ThriftStruct> thriftStruct) {
		this.thriftStructs.addAll(thriftStruct);
	}
	
	public void addThriftService(ThriftService thriftService) {
		this.thriftServices.add(thriftService);
	}
	
	public void addThriftServices(Set<ThriftService> thriftService) {
		this.thriftServices.addAll(thriftService);
	}

	public Set<ThriftStruct> getThriftStructs() {
		return thriftStructs;
	}

	public Set<ThriftService> getThriftServices() {
		return thriftServices;
	}
}
