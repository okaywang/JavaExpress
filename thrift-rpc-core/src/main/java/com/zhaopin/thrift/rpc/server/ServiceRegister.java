package com.zhaopin.thrift.rpc.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import com.alibaba.fastjson.JSON;
import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.thrift.rpc.Constants;
import com.zhaopin.thrift.rpc.RpcContext;
import com.zhaopin.thrift.rpc.WikiUrlConstants;
import com.zhaopin.thrift.rpc.common.ServiceRegisty;
import com.zhaopin.thrift.rpc.processor.TProcessor;
import com.zhaopin.thrift.rpc.protocol.TProtocol;
import com.zhaopin.thrift.rpc.registry.RegistryFactory;
import com.zhaopin.thrift.rpc.registry.RegistryService;
import com.zhaopin.thrift.rpc.util.EnvUtils;
import com.zhaopin.thrift.rpc.util.InterfaceHelper;

public class ServiceRegister {

	public static Logger LOGGER = LoggerFactory.getLogger(ServiceRegister.class);
	// 服务启动的单一实例
	public final static ServiceRegister instance = new ServiceRegister();
	// 系统所有的服务
	private final Map<String, TProcessor> processors = new HashMap<String, TProcessor>();
	// 所有注册的服务, 服务的详细信息记录
	private final Map<String, ServiceRegisty> services = new HashMap<String, ServiceRegisty>();
	// thrift是否已经启动
	public static final AtomicBoolean thriftStartup = new AtomicBoolean(false);

	public synchronized void startService(int listenPort) {
		if (!thriftStartup.getAndSet(true)) {
			ServiceStartup.startup.startService(listenPort);
		}
	}

	public void registerProcessor(ServiceRegisty registy) throws Exception {
		List<Class<?>> interfs = registy.getInterfs();
		for (Class<?> interf : interfs) {
			String service = interf.getName();
			// 首先判断是否已经有相同的service已经注册，不允许一个服务的多个版本同时在一个项目中，这样会有问题
			if (services.get(service) != null) {
				throw new IllegalStateException(service + "接口存在多个版本的实现类!");
			}
			services.put(service, registy);
			TProcessor processor = getProcessor(service, registy.getImpl(), interf);
			// 记录服务的详细信息
			processors.put(service, processor);
		}
	}

	/**
	 * 在注册中心注册服务(可能会出现一个服务多个版本的问题)
	 * 
	 * @param registy
	 * @throws Exception
	 */
	public synchronized void registerService(ServiceRegisty registy) throws Exception {
		List<Class<?>> interfs = registy.getInterfs();
		for (Class<?> interf : interfs) {
			String service = interf.getName();
			services.put(service, registy);
			Set<String> methods = InterfaceHelper.getInterfaceMethodNames(interf);
			registerZookeeper(service, registy, methods);
		}
	}

	public void registerZookeeper(String service, ServiceRegisty registy, Set<String> methods) throws Exception {
		RegistryService registryService = RegistryFactory.getRegistry(registy.getRegistry());
		ServerNodeInfo zkNodeInfo = new ServerNodeInfo();
		zkNodeInfo.setHost(registy.getHost());
		zkNodeInfo.setPort(registy.getPort());
		zkNodeInfo.setWeight(registy.getWeight());
		zkNodeInfo.setPath(System.getProperty("user.dir"));
		if (EnvUtils.isUseV4()) {
			zkNodeInfo.setThriftVersion(TProtocol.V4);
		} else {
			zkNodeInfo.setThriftVersion(TProtocol.V5);
		}
		zkNodeInfo.setMethods(methods);
		zkNodeInfo.setContext(registy.getContext());
		zkNodeInfo.setHttpPort(registy.getHttpPort());
		// 注册的时候，两个目录下同时注册
		String path = getZkPath(service, registy.getVersion(), registy.getGroup(), registy.getHost(),
				registy.getPort());
		boolean result = registryService.registerService(path, zkNodeInfo);
		if (!result) {
			LOGGER.error("register server node fail {} {}", path, JSON.toJSONString(zkNodeInfo));
		}
		if (registy.getGroup() == null || registy.getGroup().trim().length() <= 0) {
			path = getZkPath(service, registy.getVersion(), Constants.DEF_GROUP, registy.getHost(), registy.getPort());
			result = registryService.registerService(path, zkNodeInfo);
			if (!result) {
				LOGGER.error("register server node fail {} {}", path, JSON.toJSONString(zkNodeInfo));
			}
		}
	}

	protected TProcessor getProcessor(String name, Object impl, Class<?> interf) {
		int index = name.lastIndexOf(".");
		if (index < 0) {
			throw new IllegalStateException("" + name + " is invalid!");
		}
		String processorClass = name.substring(0, index) + "." + Constants.ROCESSOR_CLASS_PREFIX
				+ name.substring(index + 1) + Constants.ROCESSOR_CLASS_SUFFIX;
		try {
			Class<?> processorCls = RpcContext.getGenerateClass(processorClass);
			return (TProcessor) processorCls.getConstructor(interf).newInstance(impl);
		} catch (Exception exp) {
			LOGGER.error("[thrift]exception", exp);
			final String reason = "加载" + processorClass + "失败, 参考wiki, " + WikiUrlConstants.Compile;
			throw new IllegalStateException(reason, exp);
		}
	}

	private String getZkPath(String service, String version, String group, String host, int port) {
		// rpc节点的路径
		// 服务带分组的路径 /thrift/service/${group}/${service}/${provider}
		// 兼容以前的路径 /HSF/${service}/${provider}
		StringBuffer path = new StringBuffer();
		if (group == null || group.trim().length() <= 0) {
			path.append("/HSF" + "/").append(service.trim() + "#" + version.trim());
			path.append("/" + "provider_" + host + ":" + port);
		} else {
			path.append("/thrift/service/").append(group.trim()).append("/");
			path.append(service.trim() + "#" + version.trim()).append("/" + "provider_" + host + ":" + port + "_");
		}
		return path.toString();
	}

	public final Map<String, ServiceRegisty> getServices() {
		return services;
	}

	public ServiceRegisty getServiceDetail(String service) {
		return services.get(service);
	}

	public TProcessor getProcessors(String service) {
		return processors.get(service);
	}
}
