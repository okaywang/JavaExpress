package com.zhaopin.plugin.docparser.dto;

import java.util.*;

public class RapParameter implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String mockData;
	private String name;
	private String identifier;
	private String identifierChange;
	private String remarkChange;
	private String dataType;
	private String remark;
	private Set<RapAction> actionRequestList = new HashSet<RapAction>();
	private Set<RapAction> actionResponseList = new HashSet<RapAction>();
	private String validator = "";
	private Set<RapParameter> parameterList = new HashSet<RapParameter>();
	private Set<RapParameter> complexParamerterList = new HashSet<RapParameter>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMockData() {
		return mockData;
	}

	public void setMockData(String mockData) {
		this.mockData = mockData;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifierChange() {
		return identifierChange;
	}

	public void setIdentifierChange(String identifierChange) {
		this.identifierChange = identifierChange;
	}

	public String getRemarkChange() {
		return remarkChange;
	}

	public void setRemarkChange(String remarkChange) {
		this.remarkChange = remarkChange;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<RapAction> getActionRequestList() {
		return actionRequestList;
	}

	public void setActionRequestList(Set<RapAction> actionRequestList) {
		this.actionRequestList = actionRequestList;
	}

	public Set<RapAction> getActionResponseList() {
		return actionResponseList;
	}

	public void setActionResponseList(Set<RapAction> actionResponseList) {
		this.actionResponseList = actionResponseList;
	}

	public String getValidator() {
		return validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

	public Set<RapParameter> getParameterList() {
		return parameterList;
	}

	public void setParameterList(Set<RapParameter> parameterList) {
		this.parameterList = parameterList;
	}

	public Set<RapParameter> getComplexParamerterList() {
		return complexParamerterList;
	}

	public void setComplexParamerterList(Set<RapParameter> complexParamerterList) {
		this.complexParamerterList = complexParamerterList;
	}

}
