package com.zhaopin.thrift.tool.finder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.type.classreading.MetadataReader;

import com.google.common.collect.Sets;
import com.zhaopin.rpc.annotation.ThriftInterface;
import com.zhaopin.rpc.annotation.ThriftService;
import com.zhaopin.rpc.annotation.ThriftStruct;

public class ThriftComponentProvider extends ClassPathScanningCandidateComponentProvider {

	private Set<String> thriftResources = new HashSet<String>();
	// ÒÀÀµµÄjar
	private Set<String> dependsJars = new HashSet<String>();

	public ThriftComponentProvider(boolean useDefaultFilters) {
		super(useDefaultFilters);
	}

	protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
		String className = metadataReader.getClassMetadata().getClassName();
		if (!isThriftObject(className)) {
			return false;
		}
		if (className != null && className.startsWith("com.zhaopin")) {
			Resource resource = metadataReader.getResource();
			if (resource instanceof FileSystemResource) {
				thriftResources.add(className);
			} else if (resource instanceof UrlResource && ((UrlResource) resource).getURL() != null) {
				String path = ((UrlResource) resource).getURL().getPath();
				int index = path.lastIndexOf("!");
				if (index >= 0) {
					int first = 0;
					for (int t = index; t > 0; --t) {
						if (path.charAt(t) == '/') {
							first = t;
							break;
						}
					}
					dependsJars.add(path.substring(first + 1, index));
				}
			}
			return true;
		}
		return false;
	}

	private boolean isThriftObject(String className) {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			Class<?> cls = classLoader.loadClass(className);
			if (cls.getAnnotation(ThriftService.class) != null) {
				return true;
			} else if (cls.getAnnotation(ThriftStruct.class) != null) {
				return true;
			} else if (cls.getAnnotation(ThriftInterface.class) != null) {
				return true;
			}
		} catch (Exception exp) {

		}
		return false;
	}

	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		Set<String> annotations = beanDefinition.getMetadata().getAnnotationTypes();
		if (annotations != null && annotations.contains(ThriftInterface.class.getName())
				|| annotations.contains(ThriftStruct.class.getName())) {
			return true;
		}
		return false;
	}

	public boolean isLocalClass(String className) {
		return thriftResources.contains(className);
	}

	public Set<String> getDependsJars() {
		return dependsJars;
	}

}
