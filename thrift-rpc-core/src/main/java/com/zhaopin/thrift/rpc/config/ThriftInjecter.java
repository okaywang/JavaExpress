package com.zhaopin.thrift.rpc.config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.common.trace.ZipkinContext;
import com.zhaopin.rpc.annotation.ThriftReference;
import com.zhaopin.rpc.config.ThriftConfigure;
import com.zhaopin.thrift.rpc.RpcContext;
import com.zhaopin.thrift.rpc.common.ServerNode;
import com.zhaopin.thrift.rpc.executor.ThreadPoolExecutorFactory;
import com.zhaopin.thrift.rpc.monitor.ConsumerRegister;
import com.zhaopin.thrift.rpc.registry.RegistryFactory;
import com.zhaopin.thrift.rpc.registry.RegistryService;
import com.zhaopin.thrift.rpc.transport.ThriftChannels;
import com.zhaopin.thrift.rpc.util.EnvUtils;

public class ThriftInjecter {

	public static final Logger LOGGER = LoggerFactory.getLogger(ThriftInjecter.class);

	public void inject(ThriftConfigure thriftConfigure, ConfigurableListableBeanFactory beanFactory) {
		// 开启zipkin
		ZipkinContext.startZipkin(thriftConfigure.getSampleRate(), thriftConfigure.getProjectName());
		// 设置线程池类型
		ThreadPoolExecutorFactory.setType(thriftConfigure.getExecutor());
		// 扫描本地的内部服务
		if (!EnvUtils.islocalEnv()) {
			new InnerServiceScanner((BeanDefinitionRegistry) beanFactory).scan("com.zhaopin.thrift.tool.service");
		}
		// 对标注有@ThriftService的接口进行扫描
		String basePkg = thriftConfigure.getBasePkg();
		if (!StringUtils.isEmpty(basePkg) && thriftConfigure.getThriftPort() > 0) {
			new ThriftScanner((BeanDefinitionRegistry) beanFactory).scan(basePkg.split(","));
		}
		injectReference(beanFactory, thriftConfigure.getZkAddr());
		// 保存所有的服务调用者
		Set<Reference> refServices = injectReference(beanFactory, thriftConfigure.getZkAddr());
		// 如果存在@ThriftReference,@ThriftService则开始监听zookeeper
		RegistryFactory.initRegistry(thriftConfigure.getZkAddr());
		// 获取监听的对象
		RegistryService registryService = RegistryFactory.getRegistry(thriftConfigure.getZkAddr());
		for (Reference ref : refServices) {
			List<ServerNode> serverNodes = registryService.loadService(ref.group, ref.service, ref.version);
			for (ServerNode serverNode : serverNodes) {
				// 创建连接
				for (int t = 0; t < RpcContext.channels; ++t) {
					try {
						ThriftChannels.connect(serverNode, t);
						ConsumerRegister.register(ref.group, ref.service, ref.version);
					} catch (Exception exp) {
						LOGGER.warn("connect to {}:{} 失败!", serverNode.getHost(), serverNode.getPort());
					}
				}
			}
		}
	}

	/**
	 * 注入reference
	 * 
	 * @param beanFactory
	 * @param zkAddr
	 * @return
	 */
	public Set<Reference> injectReference(ConfigurableListableBeanFactory beanFactory, String zkAddr) {
		// 获取所有的bean的名字
		String[] beanNames = beanFactory.getBeanDefinitionNames();
		// 保存所有的服务调用者
		Set<Reference> refServices = new HashSet<Reference>();
		if (beanNames != null) {
			try {
				// 遍历所有的注册的bean
				for (String beanName : beanNames) {
					BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
					String className = beanDefinition.getBeanClassName();
					if (StringUtils.isEmpty(className)) {
						continue;
					}
					// 需要判断该bean的类中是否存在用@ThriftReference注解的属性
					List<ThriftRefDto> refDtos = retriveRef(className, zkAddr);
					MutablePropertyValues mpv = beanDefinition.getPropertyValues();
					for (ThriftRefDto refDto : refDtos) {
						if (mpv.contains(refDto.getPropName())) {
							continue;
						}
						mpv.add(refDto.getPropName(), ThriftRefUtils.getObject(refDto, beanFactory));
						String group = refDto.getThriftReference().group();
						String version = refDto.getThriftReference().version();
						String service = refDto.getThriftInterface().getName();
						refServices.add(new Reference(group, service, version));
					}
				}
			} catch (Exception exp) {
				throw new IllegalStateException(exp);
			}
		}
		return refServices;
	}

	/**
	 * 提取所有的@ThriftReference注解的属性
	 * 
	 * @return
	 */
	private List<ThriftRefDto> retriveRef(String className, String zkAddr) {
		List<ThriftRefDto> list = new ArrayList<ThriftRefDto>();
		try {
			// TODO check
			Class<?> clz = Thread.currentThread().getContextClassLoader().loadClass(className);
			final Class<?> userType = ClassUtils.getUserClass(clz);
			clz = userType;
			while (clz != Object.class) {
				Field[] fields = clz.getDeclaredFields();
				for (Field field : fields) {
					ThriftReference thriftReference = field.getAnnotation(ThriftReference.class);
					if (thriftReference == null) {
						continue;
					}
					list.add(new ThriftRefDto(thriftReference, field.getType(), field.getName(), zkAddr.trim()));
				}
				clz = clz.getSuperclass();
			}
		} catch (Exception exp) {
			throw new IllegalArgumentException("类" + className + "不存在", exp);
		}
		return list;
	}

	private static class Reference {
		public String group;
		public String service;
		public String version;

		public Reference(String group, String service, String version) {
			this.group = group;
			this.service = service;
			this.version = version;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((group == null) ? 0 : group.hashCode());
			result = prime * result + ((service == null) ? 0 : service.hashCode());
			result = prime * result + ((version == null) ? 0 : version.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Reference other = (Reference) obj;
			if (group == null) {
				if (other.group != null) {
					return false;
				}
			} else if (!group.equals(other.group)) {
				return false;
			}
			if (service == null) {
				if (other.service != null) {
					return false;
				}
			} else if (!service.equals(other.service)) {
				return false;
			}
			if (version == null) {
				if (other.version != null) {
					return false;
				}
			} else if (!version.equals(other.version)) {
				return false;
			}
			return true;
		}

	}

}
