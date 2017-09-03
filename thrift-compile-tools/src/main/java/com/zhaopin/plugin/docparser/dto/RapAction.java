package com.zhaopin.plugin.docparser.dto;

import java.util.*;

public class RapAction implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private int disableCache;
	private String name;
	private String description;
	private String requestType = "1";
	private String requestUrl;
	private Set<RapParameter> requestParameterList = new HashSet<RapParameter>();
	private Set<RapParameter> responseParameterList = new HashSet<RapParameter>();
	private String responseTemplate;
	private Set<RapPage> pageList = new HashSet<RapPage>();
	private String remarks;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDisableCache() {
		return disableCache;
	}

	public void setDisableCache(int disableCache) {
		this.disableCache = disableCache;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public Set<RapParameter> getRequestParameterList() {
		return requestParameterList;
	}

	public void setRequestParameterList(Set<RapParameter> requestParameterList) {
		this.requestParameterList = requestParameterList;
	}

	public Set<RapParameter> getResponseParameterList() {
		return responseParameterList;
	}

	public void setResponseParameterList(Set<RapParameter> responseParameterList) {
		this.responseParameterList = responseParameterList;
	}

	public String getResponseTemplate() {
		return responseTemplate;
	}

	public void setResponseTemplate(String responseTemplate) {
		this.responseTemplate = responseTemplate;
	}

	public Set<RapPage> getPageList() {
		return pageList;
	}

	public void setPageList(Set<RapPage> pageList) {
		this.pageList = pageList;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
