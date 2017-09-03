package com.zhaopin.thrift.rpc.zipkin;

import org.springframework.beans.factory.BeanFactory;

import com.zhaopin.common.trace.ZipkinContext;
import com.zhaopin.rpc.config.ThriftConfigure;
import com.zhaopin.thrift.config.dto.ThriftRpcServer;

public class ZipkinContextWraper {

	public static void startZipkin(BeanFactory beanFactory) {
		String projectName = "invoker";
		float sampleRate = 1.0f;
		if (beanFactory.containsBean(ThriftRpcServer.class.getName())) {
			ThriftRpcServer server = beanFactory.getBean(ThriftRpcServer.class);
			projectName = server.getName();
			sampleRate = server.getSampleRate();
		} else if (beanFactory.containsBean(ThriftConfigure.class.getName())) {
			ThriftConfigure thriftConfig = beanFactory.getBean(ThriftConfigure.class);
			projectName = thriftConfig.getProjectName();
			sampleRate = thriftConfig.getSampleRate();
		}
		ZipkinContext.startZipkin(sampleRate, projectName);
	}
}
