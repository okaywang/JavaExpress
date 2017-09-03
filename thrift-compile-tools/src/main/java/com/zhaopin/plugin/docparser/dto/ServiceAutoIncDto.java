package com.zhaopin.plugin.docparser.dto;

import java.util.Date;

public class ServiceAutoIncDto {
	private long id;
	private String outerurl;
	private String innerurl;
	private String mockurl;
	private String requesttype;
	private String wikiurl;
	private String description;
	private String name;
	private Date createdate;
	private Date modifydate;
	private long createuserid;
	private long modifyuserid;
	private String productname;
	private String groupname;
	
	public enum REQUESTTYPEENUM {
		GET("GET"),
		POST("POST");
		
		private String value;
		private REQUESTTYPEENUM(String value) {
			this.value = value;
		}
		public String getValue() {
			return this.value;
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOuterurl() {
		return outerurl;
	}

	public void setOuterurl(String outerurl) {
		this.outerurl = outerurl;
	}

	public String getInnerurl() {
		return innerurl;
	}

	public void setInnerurl(String innerurl) {
		this.innerurl = innerurl;
	}

	public String getMockurl() {
		return mockurl;
	}

	public void setMockurl(String mockurl) {
		this.mockurl = mockurl;
	}

	public String getRequesttype() {
		return requesttype;
	}

	public void setRequesttype(String requesttype) {
		this.requesttype = requesttype;
	}

	public String getWikiurl() {
		return wikiurl;
	}

	public void setWikiurl(String wikiurl) {
		this.wikiurl = wikiurl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getModifydate() {
		return modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	public long getCreateuserid() {
		return createuserid;
	}

	public void setCreateuserid(long createuserid) {
		this.createuserid = createuserid;
	}

	public long getModifyuserid() {
		return modifyuserid;
	}

	public void setModifyuserid(long modifyuserid) {
		this.modifyuserid = modifyuserid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
}
