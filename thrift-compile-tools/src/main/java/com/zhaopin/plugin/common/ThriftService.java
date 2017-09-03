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
	// ���������(��Ӧ@ThriftServiceע��)
	private String service = "";
	// ���������(��Ӧ@ThriftServiceע��)
	private String version = "";
	// ����ĸ��������, superName������·��
	private String superName;
	// �ӿڵ�ʵ����
	private String implementClass;
	// ����ĺ���
	private Set<ThriftServiceFunc> serviceFuncs = new TreeSet<ThriftServiceFunc>();
	// ʵ��������е�importָ���������
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
		// �ж��Ƿ��Ѿ�������ͬ���Ƶĺ���
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
