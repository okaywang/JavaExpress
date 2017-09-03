package com.zhaopin.thrift.config.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.zhaopin.thrift.config.dto.ThriftRpcRegistry;

public class ThriftRegistryParser implements BeanDefinitionParser {

	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String zkAddr = element.getAttribute("zkAddr");
		RootBeanDefinition definition = new RootBeanDefinition();
		definition.setBeanClass(ThriftRpcRegistry.class);
		definition.setLazyInit(false);
		String id = ThriftRpcRegistry.class.getName();
		definition.getPropertyValues().add("id", id);
		definition.getPropertyValues().add("zkAddr", zkAddr);
		parserContext.getRegistry().registerBeanDefinition(id, definition);
		return definition;

	}

}
