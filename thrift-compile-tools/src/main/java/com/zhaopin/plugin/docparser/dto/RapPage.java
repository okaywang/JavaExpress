package com.zhaopin.plugin.docparser.dto;

import java.util.*;

public class RapPage implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String introduction;
	// private RapModule module;
	private Set<RapAction> actionList = new HashSet<RapAction>();
	private String template;

	private int moduleId;
	private boolean isIdGenerated;

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public boolean isIdGenerated() {
		return isIdGenerated;
	}

	public void setIdGenerated(boolean isIdGenerated) {
		this.isIdGenerated = isIdGenerated;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Set<RapAction> getActionList() {
		return actionList;
	}

	public void setActionList(Set<RapAction> actionList) {
		this.actionList = actionList;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}
