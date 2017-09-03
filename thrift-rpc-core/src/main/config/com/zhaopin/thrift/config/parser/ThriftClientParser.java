package com.zhaopin.thrift.config.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.zhaopin.thrift.config.dto.ThriftRpcReference;
import com.zhaopin.thrift.config.dto.ThriftRpcRegistry;
import com.zhaopin.thrift.rpc.Constants;
import com.zhaopin.thrift.rpc.util.NumberUtil;

public class ThriftClientParser implements BeanDefinitionParser {

	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String id = element.getAttribute("id");
		String target = element.getAttribute("target");
		String version = element.getAttribute("version");
		String interf = element.getAttribute("interface");
		int retry = NumberUtil.getInt(element.getAttribute("retry"), 3);
		int async = NumberUtil.getInt(element.getAttribute("async"), 0);
		long timeout = NumberUtil.getLong(element.getAttribute("timeout"), Constants.DEF_CALL_WAIT_TIME);
		String strategy = element.getAttribute("strategy");
		String group = element.getAttribute("group");
		String selector = Constants.TOKEN_BASED;
		if (element.hasAttribute("selector")) {
			selector = element.getAttribute("selector");
			if (Constants.WEIGHT_ROUND.equals(selector)) {
				selector = Constants.WEIGHT_ROUND;
			} else if (Constants.RANDOM_ROUND.equals(selector)) {
				selector = Constants.RANDOM_ROUND;
			} else if (Constants.TOKEN_BASED.equals(selector)) {
				selector = Constants.TOKEN_BASED;
			}
		}
		RootBeanDefinition definition = new RootBeanDefinition();
		definition.setBeanClass(ThriftRpcReference.class);
		definition.setLazyInit(false);
		definition.getPropertyValues().add("id", id);
		// 设置目标bean的类型
		definition.setTargetType(getTargetType(interf));
		// 采取直接连接的方式
		if (!StringUtils.isEmpty(target)) {
			definition.getPropertyValues().add("target", target);
		}
		definition.getPropertyValues().add("version", version);
		definition.getPropertyValues().add("interfaceClass", interf);
		definition.getPropertyValues().add("retry", retry);
		definition.getPropertyValues().add("timeout", timeout);
		definition.getPropertyValues().add("async", async);
		if (element.hasAttribute("balance")) {
			definition.getPropertyValues().add("round", Constants.USER_DEFINE);
			String balance = element.getAttribute("balance");
			definition.getPropertyValues().add("selector", new RuntimeBeanReference(balance));
		} else {
			definition.getPropertyValues().add("round", selector);
		}
		if (StringUtils.isEmpty(strategy)) {
			definition.getPropertyValues().add("strategy", Constants.CLUSTER_FAILOVER);
		} else {
			definition.getPropertyValues().add("strategy", strategy);
		}
		if (!StringUtils.isEmpty(group)) {
			definition.getPropertyValues().add("group", group);
		}
		String registry = ThriftRpcRegistry.class.getName();
		if (!parserContext.getRegistry().containsBeanDefinition(registry)) {
			String reason = "spring配置文件中没有配置thrift:registry或者该项配置没有放置在所有的thrift:reference标签之前!";
			throw new IllegalStateException(reason);
		}
		definition.getPropertyValues().add("registry", new RuntimeBeanReference(registry));
		parserContext.getRegistry().registerBeanDefinition(id, definition);
		return definition;
	}

	private Class<?> getTargetType(final String interfaceClass) {
		try {
			return Thread.currentThread().getContextClassLoader().loadClass(interfaceClass);
		} catch (ClassNotFoundException exp) {
			throw new IllegalStateException(exp.getMessage(), exp);
		}
	}
}
