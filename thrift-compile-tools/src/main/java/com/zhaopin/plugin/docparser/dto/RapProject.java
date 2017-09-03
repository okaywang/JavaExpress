package com.zhaopin.plugin.docparser.dto;

import java.util.HashSet;
import java.util.Set;

public class RapProject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String createDateStr;
	private String introduction;
	private RapUser user;
	private Set<RapModule> moduleList = new HashSet<RapModule>();
	private String version;

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

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public RapUser getUser() {
		return user;
	}

	public void setUser(RapUser user) {
		this.user = user;
	}

	public Set<RapModule> getModuleList() {
		return moduleList;
	}

	public void setModuleList(Set<RapModule> moduleList) {
		this.moduleList = moduleList;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
