package com.zhaopin.thrift.rpc.config;

import com.zhaopin.rpc.annotation.ThriftReference;

public class ThriftRefDto {
	// 属性的thrift reference注解
	private ThriftReference thriftReference;
	// thrift的接口
	private Class<?> thriftInterface;
	// 属性的名字
	private String propName;
	// 注册中心的地址
	private String zkAddr;

	public ThriftRefDto(ThriftReference thriftReference, Class<?> thriftInterface, String propName, String zkAddr) {
		this.thriftReference = thriftReference;
		this.thriftInterface = thriftInterface;
		this.propName = propName;
		this.zkAddr = zkAddr;
	}

	public ThriftReference getThriftReference() {
		return thriftReference;
	}

	public void setThriftReference(ThriftReference thriftReference) {
		this.thriftReference = thriftReference;
	}

	public Class<?> getThriftInterface() {
		return thriftInterface;
	}

	public void setThriftInterface(Class<?> thriftInterface) {
		this.thriftInterface = thriftInterface;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getZkAddr() {
		return zkAddr;
	}

	public void setZkAddr(String zkAddr) {
		this.zkAddr = zkAddr;
	}

}
