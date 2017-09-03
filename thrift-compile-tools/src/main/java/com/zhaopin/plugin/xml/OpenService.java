package com.zhaopin.plugin.xml;

public class OpenService {
	// 对内的地址
	private String innerUrl;
	// 对外的统一地址
	private String outerUrl;
	// wiki地址
	private String wikiUrl;
	// 方法的类型
	private String httpMethod;
	// 方法的描述
	private String desc;
	// 产品的名称
	private String productName;
	// 分组的名字
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
