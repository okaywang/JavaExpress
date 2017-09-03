package com.zhaopin.plugin.common;

import java.util.Set;
import java.util.TreeSet;

public class ThriftStruct extends ThriftObject {
	// ����ĸ��������, superName������·��
	private String superName;
	// dto�����ֶεĶ���
	private Set<ThriftStructField> structFields = new TreeSet<ThriftStructField>();

	public ThriftStruct() {

	}

	public ThriftStruct(String pkgName, String className) {
		this(pkgName, className, null);
	}

	public ThriftStruct(String pkgName, String className, String superName) {
		super(pkgName, className);
		this.superName = superName;
	}

	public boolean addStructField(ThriftStructField structField) {
		if (structFields.contains(structField)) {
			return false;
		}
		structFields.add(structField);
		return true;
	}

	public String getSuperName() {
		return superName;
	}

	public Set<ThriftStructField> getStructFields() {
		return structFields;
	}

	public void setSuperName(String superName) {
		this.superName = superName;
	}

	public void setStructFields(Set<ThriftStructField> structFields) {
		this.structFields = structFields;
	}

}
