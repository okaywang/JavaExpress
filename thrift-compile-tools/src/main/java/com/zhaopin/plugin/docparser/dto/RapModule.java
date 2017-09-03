package com.zhaopin.plugin.docparser.dto;

import java.util.*;

public class RapModule implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private int projectId;
	private String name;
	private String introduction;
	private RapProject project;
	private Set<RapPage> pageList = new HashSet<RapPage>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
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

	public RapProject getProject() {
		return project;
	}

	public void setProject(RapProject project) {
		this.project = project;
	}

	public Set<RapPage> getPageList() {
		return pageList;
	}

	public void setPageList(Set<RapPage> pageList) {
		this.pageList = pageList;
	}

}
