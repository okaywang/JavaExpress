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
 * Thrift服务器的配置
 *
 */
public class ThriftRpcServer implements BeanFactoryPostProcessor, ApplicationListener<ContextRefreshedEvent> {

	public static Logger LOGGER = LoggerFactory.getLogger(ThriftRpcServer.class);
	// bean的ID
	private String id;
	// 项目的名字
	private String name;
	// 服务启动的端口
	private String port;
	// 客户端的通道数
	private int channels;
	// 是否进行检测(主要是为了防止没有进行thrift的编译)
	private String check = "on";
	// struct和service的检测包路径
	private String checkPkg = "com.zhaopin";
	// 采样率
	private float sampleRate = 1.0f;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// 启动zipkin
		ZipkinContext.startZipkin(sampleRate, name);
		// 扫描本地的内部服务
		new InnerServiceScanner((BeanDefinitionRegistry) beanFactory).scan("com.zhaopin.thrift.tool.service");
		ThriftRpcRegistry registry = beanFactory.getBean(ThriftRpcRegistry.class);
		new ThriftInjecter().injectReference(beanFactory, registry.getZkAddr());
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() != null) {
			return;
		}
		// 检查struct,service的bean是否合法
		if ("on".equals(check)) {
			try {
				// 检测不满足要求，停止进程
				ThriftStructChecker checker = new ThriftStructChecker();
				checker.check(this.checkPkg);
			} catch (Exception exp) {
				exp.printStackTrace();
				LOGGER.error("检测struct和service异常", exp);
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
