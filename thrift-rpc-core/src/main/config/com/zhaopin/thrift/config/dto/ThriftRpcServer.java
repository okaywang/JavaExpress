package com.zhaopin.thrift.config.dto;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.common.trace.ZipkinContext;
import com.zhaopin.thrift.rpc.checker.ThriftStructChecker;
import com.zhaopin.thrift.rpc.config.InnerServiceScanner;
import com.zhaopin.thrift.rpc.config.ThriftInjecter;
import com.zhaopin.thrift.rpc.config.ThriftServiceUtils;

/**
 * Thrift������������
 *
 */
public class ThriftRpcServer implements BeanFactoryPostProcessor, ApplicationListener<ContextRefreshedEvent> {

	public static Logger LOGGER = LoggerFactory.getLogger(ThriftRpcServer.class);
	// bean��ID
	private String id;
	// ��Ŀ������
	private String name;
	// ���������Ķ˿�
	private String port;
	// �ͻ��˵�ͨ����
	private int channels;
	// �Ƿ���м��(��Ҫ��Ϊ�˷�ֹû�н���thrift�ı���)
	private String check = "on";
	// struct��service�ļ���·��
	private String checkPkg = "com.zhaopin";
	// ������
	private float sampleRate = 1.0f;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// ����zipkin
		ZipkinContext.startZipkin(sampleRate, name);
		// ɨ�豾�ص��ڲ�����
		new InnerServiceScanner((BeanDefinitionRegistry) beanFactory).scan("com.zhaopin.thrift.tool.service");
		ThriftRpcRegistry registry = beanFactory.getBean(ThriftRpcRegistry.class);
		new ThriftInjecter().injectReference(beanFactory, registry.getZkAddr());
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() != null) {
			return;
		}
		// ���struct,service��bean�Ƿ�Ϸ�
		if ("on".equals(check)) {
			try {
				// ��ⲻ����Ҫ��ֹͣ����
				ThriftStructChecker checker = new ThriftStructChecker();
				checker.check(this.checkPkg);
			} catch (Exception exp) {
				exp.printStackTrace();
				LOGGER.error("���struct��service�쳣", exp);
				System.exit(-1);
			}
		}
		ThriftServiceUtils.startThrift(event.getApplicationContext());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getCheckPkg() {
		return checkPkg;
	}

	public void setCheckPkg(String checkPkg) {
		this.checkPkg = checkPkg;
	}

	public int getChannels() {
		return channels;
	}

	public void setChannels(int channels) {
		this.channels = channels;
	}

	public float getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(float sampleRate) {
		this.sampleRate = sampleRate;
	}

}
