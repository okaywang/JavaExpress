package com.zhaopin.plugin.common;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * @author shunli.gao
 *
 */
public class ThriftService extends ThriftObject {
	// 服务的名称(对应@ThriftService注解)
	private String service = "";
	// 服务的名称(对应@ThriftService注解)
	private String version = "";
	// 服务的父类的名称, superName是完整路径
	private String superName;
	// 接口的实现类
	private String implementClass;
	// 服务的函数
	private Set<ThriftServiceFunc> serviceFuncs = new TreeSet<ThriftServiceFunc>();
	// 实现类的所有的import指令引入的类
	private Set<String> implementImports = new HashSet<String>();

	public ThriftService() {

	}

	public ThriftService(String serviceName) {
		this("", serviceName, null);
	}

	public ThriftService(String pkgName, String serviceName) {
		this(pkgName, serviceName, null);
	}

	public ThriftService(String pkgName, String serviceName, String superName) {
		super(pkgName, serviceName);
		this.superName = superName;
	}

	public void addImplementImport(String importClass) {
		this.implementImports.add(importClass);
	}

	public void addImplementImports(Set<String> importClasses) {
		this.implementImports.addAll(importClasses);
	}

	public boolean addServiceFunc(ThriftServiceFunc func) {
		// 判断是否已经含有相同名称的函数
		if (this.serviceFuncs.contains(func)) {
			return false;
		}
		this.serviceFuncs.add(func);
		return true;
	}

	public String getSuperName() {
		return superName;
	}

	public String getImplementClass() {
		return implementClass;
	}

	public void setImplementClass(String implementClass) {
		this.implementClass = implementClass;
	}

	public Set<ThriftServiceFunc> getServiceFuncs() {
		return serviceFuncs;
	}

	public Set<String> getImplementImports() {
		return implementImports;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setSuperName(String superName) {
		this.superName = superName;
	}

	public void setServiceFuncs(Set<ThriftServiceFunc> serviceFuncs) {
		this.serviceFuncs = serviceFuncs;
	}

	public void setImplementImports(Set<String> implementImports) {
		this.implementImports = implementImports;
	}

}
