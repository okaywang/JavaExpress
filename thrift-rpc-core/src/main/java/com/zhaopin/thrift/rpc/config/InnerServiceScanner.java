package com.zhaopin.thrift.rpc.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.zhaopin.thrift.tool.annotation.InnerService;

public class InnerServiceScanner extends ClassPathBeanDefinitionScanner {

	public InnerServiceScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}

	public void registerDefaultFilters() {
		this.addIncludeFilter(new AnnotationTypeFilter(InnerService.class));
	}

	public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		return super.doScan(basePackages);
	}

	public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return super.isCandidateComponent(beanDefinition)
				&& beanDefinition.getMetadata().hasAnnotation(InnerService.class.getName());
	}

}
