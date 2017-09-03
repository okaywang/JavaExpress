package com.zhaopin.thrift.config.parser;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.thrift.config.dto.ThriftRpcRegistry;
import com.zhaopin.thrift.config.dto.ThriftRpcServer;
import com.zhaopin.thrift.config.dto.ThriftRpcService;
import com.zhaopin.thrift.rpc.Constants;
import com.zhaopin.thrift.rpc.util.NumberUtil;

public class ThriftServiceParser implements BeanDefinitionParser {

	public static final Logger LOGGER = LoggerFactory.getLogger(ThriftServiceParser.class);

	public static final AtomicInteger index = new AtomicInteger(0);

	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String id = null;
		if (element.hasAttribute("id")) {
			id = element.getAttribute("id");
		} else {
			// Ĭ�ϵ�ID
			id = "thrift_service_" + index.getAndIncrement();
		}
		String ref = element.getAttribute("ref");
		String version = element.getAttribute("version");
		String group = element.getAttribute("group");
		int weight = 5;
		if (element.hasAttribute("weight")) {
			weight = NumberUtil.getInt(element.getAttribute("weight"), weight);
			if (weight < 1) {
				weight = Constants.MIN_WEIGHT;
			} else if (weight > 9) {
				weight = Constants.MAX_WEIGHT;
			}
		}
		RootBeanDefinition definition = new RootBeanDefinition();
		definition.setBeanClass(ThriftRpcService.class);
		definition.setLazyInit(false);
		definition.getPropertyValues().add("id", id);
		definition.getPropertyValues().add("version", version);
		definition.getPropertyValues().add("weight", weight);
		String registry = ThriftRpcRegistry.class.getName();
		if (!parserContext.getRegistry().containsBeanDefinition(registry)) {
			String reason = "spring�����ļ���û������<thrift:registry></thrift:registry>" + "���߸�������û�з��������е�thrift:service��ǩ֮ǰ!";
			throw new IllegalStateException(reason);
		}
		String server = ThriftRpcServer.class.getName();
		if (!parserContext.getRegistry().containsBeanDefinition(server)) {
			String reason = "spring�����ļ���û������<thrift:server></thrift:server>" + "���߸�������û�з��������е�thrift:service��ǩ֮ǰ!";
			throw new IllegalStateException(reason);
		}
		definition.getPropertyValues().add("registry", new RuntimeBeanReference(registry));
		definition.getPropertyValues().add("server", new RuntimeBeanReference(server));
		definition.getPropertyValues().add("ref", ref);
		if (!StringUtils.isEmpty(group)) {
			definition.getPropertyValues().add("group", group);
		}
		parserContext.getRegistry().registerBeanDefinition(id, definition);
		return definition;
	}

}
