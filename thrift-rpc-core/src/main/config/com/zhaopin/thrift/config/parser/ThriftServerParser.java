package com.zhaopin.thrift.config.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.zhaopin.thrift.config.dto.ThriftRpcServer;
import com.zhaopin.thrift.rpc.Constants;
import com.zhaopin.thrift.rpc.util.NumberUtil;

public class ThriftServerParser implements BeanDefinitionParser {

	public BeanDefinition parse(Element element, ParserContext parserContext) {
		// 获取服务端默认监听端口
		int port = NumberUtil.getInt(element.getAttribute("port"), Constants.DEFAULT_LISTEN_PORT);
		String project = element.getAttribute("name");
		if (StringUtils.isEmpty(project)) {
			project = "default";
		}
		RootBeanDefinition definition = new RootBeanDefinition();
		definition.setBeanClass(ThriftRpcServer.class);
		definition.setLazyInit(false);
		String id = ThriftRpcServer.class.getName();
		definition.getPropertyValues().add("id", id);
		definition.getPropertyValues().add("port", port);
		definition.getPropertyValues().add("name", project);
		String check = element.getAttribute("check");
		definition.getPropertyValues().add("check", check);
		String checkPkg = element.getAttribute("checkPkg");
		definition.getPropertyValues().add("checkPkg", checkPkg);
		String channels = element.getAttribute("channels");
		definition.getPropertyValues().add("channels", NumberUtil.getInt(channels, 1));
		definition.getPropertyValues().add("sampleRate", element.getAttribute("sampleRate"));
		// 判断是否有
		parserContext.getRegistry().registerBeanDefinition(id, definition);
		return definition;
	}

}
