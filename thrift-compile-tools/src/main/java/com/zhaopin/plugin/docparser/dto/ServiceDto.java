package com.zhaopin.plugin.docparser.dto;

import java.util.List;

public class ServiceDto {
	// 服务中文名
	private String name;
	// 服务类名
	private String identifier;
	// 服务描述 备注
	private String remark;
	// Service 包完整路径
	private String fullPath;
	// 服务接口
	private List<IfaceDto> ifaceList;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<IfaceDto> getIfaceList() {
		return ifaceList;
	}

	public void setIfaceList(List<IfaceDto> ifaceList) {
		this.ifaceList = ifaceList;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
}
