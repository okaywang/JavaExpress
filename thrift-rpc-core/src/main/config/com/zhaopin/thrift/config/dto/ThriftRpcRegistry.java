package com.zhaopin.thrift.config.dto;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.zhaopin.thrift.rpc.zipkin.ZipkinContextWraper;

public class ThriftRpcRegistry implements BeanFactoryPostProcessor {
	// bean的ID
	private String id;
	// 注册中心的完整地址
	private String zkAddr;

	public ThriftRpcRegistry() {

	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		ZipkinContextWraper.startZipkin(beanFactory);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getZkAddr() {
		return zkAddr;
	}

	public void setZkAddr(String zkAddr) {
		this.zkAddr = zkAddr;
	}

}
