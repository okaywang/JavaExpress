package com.zhaopin.plugin.xml;

public class OpenService {
	// ���ڵĵ�ַ
	private String innerUrl;
	// �����ͳһ��ַ
	private String outerUrl;
	// wiki��ַ
	private String wikiUrl;
	// ����������
	private String httpMethod;
	// ����������
	private String desc;
	// ��Ʒ������
	private String productName;
	// ���������
	private String groupName;
	
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
}
