package com.zhaopin.plugin.docparser.dto;

import java.util.List;

public class IfaceDto {
	// �ӿ�������
	private String name;
	// �ӿ��������� 10-get,20-postjson,30-postform
	private short requestType = RequestType.GET.getValue();
	// �ӿڶ���Url
	private String outerUrl;
	// ����url
	private String requestUrl;
	// �������
	private List<ParamDto> requestParamList;
	// �������
	private ParamDto responseParam;
	// ���demo
	private String requestParamDemo;
	// ����demo
	private String reponseParamDemo;
	// remark
	private String remark;

	public enum RequestType {
		GET((short) 10), POSTJSON((short) 20), POSTFORM((short) 30);

		private Short value;

		private RequestType(Short value) {
			this.value = value;
		}

		public Short getValue() {
			return this.value;
		}
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getRequestType() {
		return requestType;
	}

	public void setRequestType(short requestType) {
		this.requestType = requestType;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public List<ParamDto> getRequestParamList() {
		return requestParamList;
	}

	public void setRequestParamList(List<ParamDto> requestParamList) {
		this.requestParamList = requestParamList;
	}

	public ParamDto getResponseParam() {
		return responseParam;
	}

	public void setResponseParam(ParamDto responseParam) {
		this.responseParam = responseParam;
	}

	public String getRequestParamDemo() {
		return requestParamDemo;
	}

	public void setRequestParamDemo(String requestParamDemo) {
		this.requestParamDemo = requestParamDemo;
	}

	public String getReponseParamDemo() {
		return reponseParamDemo;
	}

	public void setReponseParamDemo(String reponseParamDemo) {
		this.reponseParamDemo = reponseParamDemo;
	}

	public String getOuterUrl() {
		return outerUrl;
	}

	public void setOuterUrl(String outerUrl) {
		this.outerUrl = outerUrl;
	}
	
}
