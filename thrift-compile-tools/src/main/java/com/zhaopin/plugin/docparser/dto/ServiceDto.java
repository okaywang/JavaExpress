package com.zhaopin.plugin.docparser.dto;

import java.util.List;

public class ServiceDto {
	// ����������
	private String name;
	// ��������
	private String identifier;
	// �������� ��ע
	private String remark;
	// Service ������·��
	private String fullPath;
	// ����ӿ�
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
