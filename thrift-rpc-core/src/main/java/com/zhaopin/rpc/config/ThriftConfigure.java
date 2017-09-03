package com.zhaopin.rpc.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.StringUtils;

import com.google.common.base.Preconditions;
import com.zhaopin.thrift.rpc.RpcContext;
import com.zhaopin.thrift.rpc.config.ThriftServiceUtils;
import com.zhaopin.thrift.rpc.config.ThriftInjecter;

public class ThriftConfigure implements BeanFactoryPostProcessor, ApplicationListener<ContextRefreshedEvent> {
	// 项目的名称
	private String projectName;
	// http上下文地址
	private String context = null;
	// http的端口
	private int httpPort = 0;
	// 返回值统一格式规范
	private Object httpReply;
	// thrift的配置
	private int thriftPort = 0;
	// 注册中心的配置
	private String zkAddr;
	// 连接通道个数
	private int channels = 1;
	// 服务的路径
	private String basePkg;
	// 服务端线程池方案(cache, simple, pool)
	private String executor = "simple";
	// 核心线程数
	private int threadCoreSize = Runtime.getRuntime().availableProcessors() * 2;
	// 最大线程数
	private int threadMaxSize = Math.max(Runtime.getRuntime().availableProcessors() * 16, 128);
	// 采样率
	private float sampleRate = 1.0f;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Preconditions.checkArgument(!StringUtils.isEmpty(zkAddr), "必须配置注册中心地址");
		Preconditions.checkNotNull(projectName, "必须配置ThriftConfigure的属性projectName");
		// 对thrift环境进行初始化
		RpcContext.listenPort = thriftPort;
		RpcContext.Context = context;
		RpcContext.httpPort = httpPort;
		RpcContext.registy = zkAddr;
		RpcContext.channels = channels;
		// 核心线程数
		RpcContext.ThreadCoreSize = threadCoreSize;
		// 最大线程数
		RpcContext.ThreadMaxSize = threadMaxSize;
		new ThriftInjecter().inject(this, beanFactory);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() != null) {
			return;
		}
		// 开启http
		if (getHttpPort() > 0) {
			ThriftServiceUtils.startHttp(event.getApplicationContext(), this);
		}
		// 开启thrift
		if (getThriftPort() > 0) {
			ThriftServiceUtils.startThrift(event.getApplicationContext(), this);
		}
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public int getHttpPort() {
		return httpPort;
	}

	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}

	public int getThriftPort() {
		return thriftPort;
	}

	public void setThriftPort(int thriftPort) {
		this.thriftPort = thriftPort;
	}

	public String getZkAddr() {
		return zkAddr;
	}

	public void setZkAddr(String zkAddr) {
		this.zkAddr = zkAddr;
	}

	public int getChannels() {
		return channels;
	}

	public void setChannels(int channels) {
		this.channels = channels;
	}

	public String getBasePkg() {
		return basePkg;
	}

	public void setBasePkg(String basePkg) {
		this.basePkg = basePkg;
	}

	public int getThreadCoreSize() {
		return threadCoreSize;
	}

	public void setThreadCoreSize(int threadCoreSize) {
		this.threadCoreSize = threadCoreSize;
	}

	public int getThreadMaxSize() {
		return threadMaxSize;
	}

	public void setThreadMaxSize(int threadMaxSize) {
		this.threadMaxSize = threadMaxSize;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public float getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(float sampleRate) {
		this.sampleRate = sampleRate;
	}

	public Object getHttpReply() {
		return httpReply;
	}

	public void setHttpReply(Object httpReply) {
		this.httpReply = httpReply;
	}

}
