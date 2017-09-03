package com.zhaopin.thrift.config;

import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.thrift.config.parser.ThriftClientParser;
import com.zhaopin.thrift.config.parser.ThriftRegistryParser;
import com.zhaopin.thrift.config.parser.ThriftServerParser;
import com.zhaopin.thrift.config.parser.ThriftServiceParser;

public class ThriftNamespaceHandler extends NamespaceHandlerSupport {

	public static Logger LOGGER = LoggerFactory.getLogger(ThriftNamespaceHandler.class);

	public void init() {
		registerBeanDefinitionParser("server", new ThriftServerParser());
		registerBeanDefinitionParser("registry", new ThriftRegistryParser());
		registerBeanDefinitionParser("service", new ThriftServiceParser());
		registerBeanDefinitionParser("reference", new ThriftClientParser());
		// 判断是否具有ThriftHttpParser类
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try {
			String parser = ThriftServerParser.class.getPackage().getName() + ".ThriftHttpParser";
			Class<?> cls = classLoader.loadClass(parser);
			registerBeanDefinitionParser("http", (BeanDefinitionParser) cls.newInstance());
		} catch (Exception exp) {
			LOGGER.warn("没有加载thrift-rpc-http相关的jar包");
		}
	}

}
