package com.zhaopin.plugin.common;

import java.util.ArrayList;
import java.util.List;

import com.zhaopin.plugin.Constants;

import com.google.common.base.Preconditions;

public class ThriftServiceFunc implements Comparable<ThriftServiceFunc> {
	// ����������
	private String funcName;
	// �����ķ���ֵ����
	private String retType;
	// ����������б�
	private List<ThriftServiceFuncParam> funcParams = new ArrayList<ThriftServiceFuncParam>();
	// �����׳����쳣
	private List<String> exceptions = new ArrayList<String>();
	// ������ע��
	private String comment = "";
	// ���ڵĵ�ַ(esbʹ��)
	private String innerUrl;
	// �����ͳһ��ַ(esbʹ��)
	private String outerUrl;
	// wiki��ַ(esbʹ��)
	private String wikiUrl;
	// post/form, post/json, get
	private String httpMethod;
	// ��Ʒ������
	private String productName;
	// ���������
	private String groupName;

	public ThriftServiceFunc() {

	}

	public ThriftServiceFunc(String funcName) {
		Preconditions.checkNotNull(funcName);
		this.funcName = funcName;
	}

	public ThriftServiceFunc(String funcName, String retType) {
		Preconditions.checkNotNull(funcName);
		Preconditions.checkNotNull(retType);
		this.funcName = funcName;
		String thriftType = Constants.TYPE_MAPPING.get(retType);
		if (thriftType != null) {
			this.retType = thriftType;
		} else {
			this.retType = retType;
		}
	}

	@Override
	public int compareTo(ThriftServiceFunc other) {
		Preconditions.checkNotNull(other);
		return getFuncName().compareTo(other.getFuncName());
	}

	public void addFuncParam(ThriftServiceFuncParam funcParam) {
		this.funcParams.add(funcParam);
	}

	public void addException(String exception) {
		if (!this.exceptions.contains(exception) && exception != null) {
			this.exceptions.add(exception);
		}
	}

	public void addExceptions(List<String> exceptions) {
		if (exceptions != null) {
			for (String exception : exceptions) {
				addException(exception);
			}
		}
	}

	public String getFuncName() {
		return funcName;
	}

	public String getRetType() {
		return retType;
	}

	public List<ThriftServiceFuncParam> getFuncParams() {
		return funcParams;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<String> getExceptions() {
		return exceptions;
	}

	public String getInnerUrl() {
		return innerUrl;
	}

	public void setInnerUrl(String innerUrl) {
		this.innerUrl = innerUrl;
	}

	public String getOuterUrl() {
		return outerUrl;
	}

	public void setOuterUrl(String outerUrl) {
		this.outerUrl = outerUrl;
	}

	public String getWikiUrl() {
		return wikiUrl;
	}

	public void setWikiUrl(String wikiUrl) {
		this.wikiUrl = wikiUrl;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setRetType(String retType) {
		this.retType = retType;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public void setFuncParams(List<ThriftServiceFuncParam> funcParams) {
		this.funcParams = funcParams;
	}

	public void setExceptions(List<String> exceptions) {
		this.exceptions = exceptions;
	}

}
