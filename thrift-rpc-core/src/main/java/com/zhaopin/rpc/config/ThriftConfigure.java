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
	// ��Ŀ������
	private String projectName;
	// http�����ĵ�ַ
	private String context = null;
	// http�Ķ˿�
	private int httpPort = 0;
	// ����ֵͳһ��ʽ�淶
	private Object httpReply;
	// thrift������
	private int thriftPort = 0;
	// ע�����ĵ�����
	private String zkAddr;
	// ����ͨ������
	private int channels = 1;
	// �����·��
	private String basePkg;
	// ������̳߳ط���(cache, simple, pool)
	private String executor = "simple";
	// �����߳���
	private int threadCoreSize = Runtime.getRuntime().availableProcessors() * 2;
	// ����߳���
	private int threadMaxSize = Math.max(Runtime.getRuntime().availableProcessors() * 16, 128);
	// ������
	private float sampleRate = 1.0f;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Preconditions.checkArgument(!StringUtils.isEmpty(zkAddr), "��������ע�����ĵ�ַ");
		Preconditions.checkNotNull(projectName, "��������ThriftConfigure������projectName");
		// ��thrift�������г�ʼ��
		RpcContext.listenPort = thriftPort;
		RpcContext.Context = context;
		RpcContext.httpPort = httpPort;
		RpcContext.registy = zkAddr;
		RpcContext.channels = channels;
		// �����߳���
		RpcContext.ThreadCoreSize = threadCoreSize;
		// ����߳���
		RpcContext.ThreadMaxSize = threadMaxSize;
		new ThriftInjecter().inject(this, beanFactory);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() != null) {
			return;
		}
		// ����http
		if (getHttpPort() > 0) {
			ThriftServiceUtils.startHttp(event.getApplicationContext(), this);
		}
		// ����thrift
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
