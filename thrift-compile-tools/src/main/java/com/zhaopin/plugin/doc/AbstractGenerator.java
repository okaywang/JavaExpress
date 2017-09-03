package com.zhaopin.plugin.doc;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.google.common.base.Joiner;
import com.zhaopin.plugin.Constants;
import com.zhaopin.rpc.annotation.ThriftService;
import com.zhaopin.rpc.annotation.ThriftStruct;
import com.zhaopin.thrift.rpc.util.InterfaceHelper;

public abstract class AbstractGenerator {

	protected ThriftCandidateBean getCandidates(String basePkg) {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(ThriftService.class));
		provider.addIncludeFilter(new AnnotationTypeFilter(ThriftStruct.class));
		Set<BeanDefinition> candidates = provider.findCandidateComponents(basePkg);
		ThriftCandidateBean thriftCandidateBean = new ThriftCandidateBean();
		for (BeanDefinition candidate : candidates) {
			Class<?> beanClass = null;
			try {
				beanClass = Thread.currentThread().getContextClassLoader().loadClass(candidate.getBeanClassName());
			} catch (Exception exp) {
				// ���ﲻ������쳣
				exp.printStackTrace();
			}
			if (beanClass == null) {
				continue;
			}
			if (beanClass.getAnnotation(com.zhaopin.rpc.annotation.ThriftStruct.class) != null) {
				String resPath = "/META-INF/thrift/cfg/" + beanClass.getName().replace(".", "_") + ".json";
				String resource = getResourceContent(resPath);
				if (resource != null && resource.length() > 0) {
					thriftCandidateBean.interfDefines.put(beanClass.getName(), resource);
				}
				thriftCandidateBean.structs.add(beanClass);
			} else if (beanClass.getAnnotation(com.zhaopin.rpc.annotation.ThriftService.class) != null) {
				// ��ȡ��������еĽӿ�
				Class<?>[] interfaces = InterfaceHelper.getClassInterfacesWithinPkg(beanClass, "com.zhaopin");
				for (Class<?> interf : interfaces) {
					if (!thriftCandidateBean.interfDefines.containsKey(interf.getName())) {
						String resPath = "/META-INF/thrift/cfg/" + interf.getName().replace(".", "_") + ".json";
						String resource = getResourceContent(resPath);
						if (resource != null && resource.length() > 0) {
							thriftCandidateBean.interfDefines.put(interf.getName(), resource);
						}
					}
				}
				thriftCandidateBean.services.add(new CandidateService(beanClass, interfaces,
						beanClass.getAnnotation(com.zhaopin.rpc.annotation.ThriftService.class)));
			}
		}
		return thriftCandidateBean;
	}

	protected String getHttpMethod(Method method) {
		String httpMethod = Constants.GET;
		Class<?>[] paramTypes = method.getParameterTypes();
		Annotation[][] annotations = method.getParameterAnnotations();
		for (int t = 0; t < paramTypes.length; ++t) {
			Class<?> paramType = paramTypes[t];
			// ���ȼ���Ƿ����ض���ע��
			if (annotations != null && annotations[t] != null) {
				for (Annotation annotation : annotations[t]) {
					if (annotation instanceof com.zhaopin.rpc.annotation.FormParam) {
						// �������FormParam��һ����form����
						httpMethod = Constants.POST_FORM;
						break;
					} else if (annotation instanceof com.zhaopin.rpc.annotation.RequestBody
							|| annotation instanceof org.springframework.web.bind.annotation.RequestBody) {
						// �������RequestBody��һ����json����
						httpMethod = Constants.POST_JSON;
						break;
					}
				}
			}
			if (!isBasicType(paramType)) {
				httpMethod = Constants.POST_JSON;
				break;
			}
		}
		return httpMethod;
	}

	private boolean isBasicType(Class<?> cls) {
		if (cls.isPrimitive()) {
			return true;
		}
		Class<?>[] basicClasses = { Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Double.class,
				String.class, Date.class };
		for (Class<?> basicClass : basicClasses) {
			if (cls == basicClass) {
				return true;
			}
		}
		return false;
	}

	protected static class ThriftCandidateBean {
		// thrift���еĵĶ���
		public Map<String, String> interfDefines = new HashMap<String, String>();
		public List<CandidateService> services = new ArrayList<CandidateService>();
		public List<Class<?>> structs = new ArrayList<Class<?>>();
	}

	protected static class CandidateService {
		// ע����@ThriftService����
		public final Class<?> beanClass;
		// ʵ�����ע��
		public ThriftService thriftService;
		// ʵ�ֵĽӿ�
		public final Class<?>[] interfaces;

		public CandidateService(Class<?> beanClass, Class<?>[] interfaces, ThriftService thriftService) {
			this.beanClass = beanClass;
			this.interfaces = interfaces;
			this.thriftService = thriftService;
		}
	}

	protected String getResourceContent(String resPath) {
		InputStream input = this.getClass().getResourceAsStream(resPath);
		if (input == null) {
			return null;
		}
		String content = null;
		try {
			content = Joiner.on("\r\n").join(IOUtils.readLines(input, "utf-8"));
			input.close();
		} catch (Exception exp) {

		} finally {
			closeStream(input);
		}
		return content;
	}

	private void closeStream(InputStream input) {
		try {
			input.close();
		} catch (Exception exp) {

		}
	}
}
