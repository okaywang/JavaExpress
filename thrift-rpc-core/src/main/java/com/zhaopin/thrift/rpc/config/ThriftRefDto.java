package com.zhaopin.thrift.rpc.config;

import com.zhaopin.rpc.annotation.ThriftReference;

public class ThriftRefDto {
	// ���Ե�thrift referenceע��
	private ThriftReference thriftReference;
	// thrift�Ľӿ�
	private Class<?> thriftInterface;
	// ���Ե�����
	private String propName;
	// ע�����ĵĵ�ַ
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
