package com.zhaopin.plugin.common;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 *
 */
public abstract class ThriftObject {
	// �������ڵİ�·��
	private String pkgName;
	// service�ķ���������struct������
	private String className;
	// ʵ��������е�importָ���������
	private final Set<String> imports = new TreeSet<String>();
	// ע��
	private String comment;
	
	public ThriftObject() {
		
	}

	public ThriftObject(String pkgName, String className) {
		// package���벻����null
		assert pkgName != null;
		this.pkgName = pkgName;
		this.className = className;
	}

	public void addImport(String importClass) {
		this.imports.add(importClass);
	}

	public void addImports(Set<String> importClasses) {
		this.imports.addAll(importClasses);
	}

	public String getPkgName() {
		return pkgName;
	}

	public String getClassName() {
		return className;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Set<String> getImports() {
		return imports;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
