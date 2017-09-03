package com.zhaopin.plugin.common;

import com.zhaopin.plugin.Constants;

/**
 * 
 * @author shunli.gao
 *
 */
public class ThriftServiceFuncParam {
	// 函数的参数序号
	private int paramIndex;
	// 字段类型
	private String paramType;
	// 字段名字
	private String paramName;
	// 是否必须
	private boolean require = false;
	// 方法参数的注释
	private String comment;

	public int getParamIndex() {
		return paramIndex;
	}

	public void setParamIndex(int paramIndex) {
		this.paramIndex = paramIndex;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		String thriftType = Constants.TYPE_MAPPING.get(paramType);
		if (thriftType != null) {
			this.paramType = thriftType;
		} else {
			this.paramType = paramType;
		}
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public boolean isRequire() {
		return require;
	}

	public void setRequire(boolean require) {
		this.require = require;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
