package com.zhaopin.thrift.rpc.config;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.context.ApplicationContext;

import com.google.common.collect.Lists;
import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.common.trace.ZipkinContext;
import com.zhaopin.rpc.annotation.ThriftService;
import com.zhaopin.rpc.config.ThriftConfigure;
import com.zhaopin.rpc.http.service.HttpBootService;
import com.zhaopin.thrift.config.dto.ThriftRpcService;
import com.zhaopin.thrift.config.dto.ThriftRpcRegistry;
import com.zhaopin.thrift.config.dto.ThriftRpcServer;
import com.zhaopin.thrift.rpc.Constants;
import com.zhaopin.thrift.rpc.RpcContext;
import com.zhaopin.thrift.rpc.common.ServiceRegisty;
import com.zhaopin.thrift.rpc.monitor.ConsumerRegister;
import com.zhaopin.thrift.rpc.monitor.InvokeChainMonitor;
import com.zhaopin.thrift.rpc.monitor.InvokeStatistics;
import com.zhaopin.thrift.rpc.registry.RegistryFactory;
import com.zhaopin.thrift.rpc.server.ServiceRegister;
import com.zhaopin.thrift.rpc.util.EnvUtils;
import com.zhaopin.thrift.rpc.util.IPResolver;
import com.zhaopin.thrift.rpc.util.InterfaceHelper;
import com.zhaopin.thrift.rpc.util.NumberUtil;
import com.zhaopin.thrift.rpc.util.VersionUtils;
import com.zhaopin.thrift.tool.service.ThriftServiceReport;

public class ThriftServiceUtils {

	public static final Logger LOGGER = LoggerFactory.getLogger(ThriftServiceUtils.class);
	// 启动标志
	private static AtomicBoolean first = new AtomicBoolean(true);

	/**
	 * 兼容历史版本的函数
	 * 
	 * @param context
	 */
	public static void startThrift(ApplicationContext context) {
		// 没有该bean，直接返回
		if (!context.containsBeanDefinition(ThriftRpcServer.class.getName())) {
			return;
		}
		if (!first.getAndSet(false)) {
			return;
		}
		ThriftRpcServer thriftRpcServer = context.getBean(ThriftRpcServer.class);
		ZipkinContext.startZipkin(thriftRpcServer.getSampleRate(), thriftRpcServer.getName());
		final int listenPort = NumberUtil.getInt(thriftRpcServer.getPort(), Constants.DEFAULT_LISTEN_PORT);
		ThriftRpcRegistry thriftRpcRegistry = context.getBean(ThriftRpcRegistry.class);
		// 如果存在@ThriftReference,@ThriftService则开始监听zookeeper
		RegistryFactory.initRegistry(thriftRpcRegistry.getZkAddr());
		startInnerService(context, listenPort, thriftRpcRegistry.getZkAddr());
		// 启动所有的服务
		Map<String, ThriftRpcService> services = context.getBeansOfType(ThriftRpcService.class);
		// 注册服务，启动服务监听
		List<ServiceRegisty> serviceRegistyList = Lists.newArrayList();
		if (!EnvUtils.isRegZk()) {
			System.out.println("由于本机配置了JVM的reg_zk环境变量为on，不注册zookeeper!");
		}
		for (Entry<String, ThriftRpcService> entry : services.entrySet()) {
			ThriftRpcService thriftRpcService = entry.getValue();
			Object impl = context.getBean(entry.getValue().getRef());
			Class<?> userType = AopProxyUtils.ultimateTargetClass(impl);
			Class<?>[] interfs = InterfaceHelper.getClassInterfacesWithinPkg(userType, "com.zhaopin");
			for (Class<?> interf : interfs) {
				ServiceRegisty registy = new ServiceRegisty();
				registy.addInterf(interf);
				registy.setVersion(thriftRpcService.getVersion());
				registy.setGroup(thriftRpcService.getGroup());
				registy.setHost(IPResolver.getIP());
				registy.setPort(listenPort);
				registy.setRegistry(thriftRpcService.getRegistry().getZkAddr());
				registy.setImpl(impl);
				registy.setWeight(thriftRpcService.getWeight());
				try {
					if (EnvUtils.isRegZk()) {
						ServiceRegister.instance.registerProcessor(registy);
					}
				} catch (Exception exp) {
					LOGGER.error("[thrift] exception", exp);
					exp.printStackTrace();
					// 失败后立即退出
					System.exit(-1);
				}
				serviceRegistyList.add(registy);
			}
		}
		// 启动服务
		ServiceRegister.instance.startService(listenPort);
		for (ServiceRegisty serviceRegisty : serviceRegistyList) {
			try {
				ServiceRegister.instance.registerService(serviceRegisty);
			} catch (Exception exp) {
				LOGGER.error("[thrift] exception", exp);
				exp.printStackTrace();
				// 失败后立即退出
				System.exit(-1);
			}
		}
	}

	public static void startThrift(ApplicationContext context, ThriftConfigure thriftConfigure) {
		if (!first.getAndSet(false)) {
			return;
		}
		ZipkinContext.startZipkin(thriftConfigure.getSampleRate(), thriftConfigure.getProjectName());
		startInnerService(context, thriftConfigure.getThriftPort(), thriftConfigure.getZkAddr());
		// 开启thrift的服务
		Map<String, Object> beans = context.getBeansWithAnnotation(ThriftService.class);
		// 先注册processor
		for (Entry<String, Object> entry : beans.entrySet()) {
			Class<?> userClass = AopProxyUtils.ultimateTargetClass(entry.getValue());
			ThriftService thriftService = userClass.getAnnotation(ThriftService.class);
			ThriftServiceUtils.registerProcessor(entry.getValue(), entry.getKey(), thriftService);
		}
		// 启动服务
		// 如果开启监听rpc，启动服务
		if (thriftConfigure.getThriftPort() > 0) {
			ServiceRegister.instance.startService(thriftConfigure.getThriftPort());
		}
		// 注册zookeeper
		if (EnvUtils.isRegZk()) {
			for (Entry<String, Object> entry : beans.entrySet()) {
				Class<?> userClass = AopProxyUtils.ultimateTargetClass(entry.getValue());
				ThriftService thriftService = userClass.getAnnotation(ThriftService.class);
				ThriftServiceUtils.registerService(entry.getValue(), entry.getKey(), thriftService);
			}
		} else {
			System.out.println("您本机设置了JVM的reg_zk环境变量为on,现在不注册zookeeper!");
		}

	}

	public static void startHttp(ApplicationContext context, ThriftConfigure thriftConfigure) {
		// 如果没有初始化zipkin,则初始化zipkin
		ZipkinContext.startZipkin(thriftConfigure.getSampleRate(), thriftConfigure.getProjectName());
		// 是否包含thrift
		// 首先判断是否存在
		Map<String, Object> beans = context.getBeansWithAnnotation(ThriftService.class);
		HttpBootService boot = null;
		String impl = HttpBootService.class.getPackage().getName() + ".DefaultHttpBootService";
		try {
			Class<?> cls = Thread.currentThread().getContextClassLoader().loadClass(impl);
			boot = (HttpBootService) cls.newInstance();
		} catch (Exception exp) {
			throw new IllegalStateException("无法加载" + impl + ",可能是没有加载thrift-http包的缘故!", exp);
		}
		boot.start(thriftConfigure, beans);

	}

	private static void startInnerService(ApplicationContext context, final int port, String zkAddr) {
		if (!EnvUtils.islocalEnv()) {
			ThriftServiceReport thriftReport = context.getBean(ThriftServiceReport.class);
			// 开启服务调用监控
			InvokeChainMonitor invokeChainMonitor = new InvokeChainMonitor();
			invokeChainMonitor.setInvokeReport(thriftReport.getInvokeReport());
			Thread monitorThread = new Thread(invokeChainMonitor);
			monitorThread.setName("invokerChainThread");
			monitorThread.setDaemon(true);
			monitorThread.start();
			// 开启服务调用统计
			InvokeStatistics.instance.setPort(port);
			InvokeStatistics.instance.setThriftReport(thriftReport.getReport());
			Thread statisticsThread = new Thread(InvokeStatistics.instance);
			statisticsThread.setName("statisticsThread");
			statisticsThread.setDaemon(true);
			statisticsThread.start();
		} else {
			System.out.println("本机测试环境下不开启内部服务调用统计上报和依赖上报功能!");
		}
		Thread consumer = new Thread(new ConsumerRegister(zkAddr));
		consumer.setName("consumer-register");
		consumer.setDaemon(true);
		consumer.start();
	}

	/**
	 * 注册processor
	 * 
	 * @param ref
	 * @param beanName
	 * @param thriftService
	 */
	public static void registerProcessor(Object ref, String beanName, ThriftService thriftService) {
		register(ref, beanName, thriftService, new RegisterCallback() {

			@Override
			public void callback(ServiceRegisty registy) throws Exception {
				ServiceRegister.instance.registerProcessor(registy);
			}

		});
	}

	public static void registerService(Object ref, String beanName, ThriftService thriftService) {
		register(ref, beanName, thriftService, new RegisterCallback() {

			@Override
			public void callback(ServiceRegisty registy) throws Exception {
				ServiceRegister.instance.registerService(registy);
			}

		});
	}

	private static void register(Object ref, String beanName, ThriftService thriftService,
			RegisterCallback registerCallback) {
		Class<?>[] interfs = InterfaceHelper.getClassInterfaces(ref.getClass());
		if (interfs == null || interfs.length < 1) {
			throw new IllegalStateException(ref.getClass() + "没有实现相应的接口!");
		}
		// 判断实现的接口的数量是否正确
		int total = 0;
		StringBuffer reason = new StringBuffer();
		for (Class<?> interf : interfs) {
			if (interf.getName().startsWith("com.zhaopin")) {
				++total;
				reason.append("" + interf.getName() + ",");
			}
		}
		if (total > 1) {
			System.err.println("bean of name \"" + beanName + "\" " + ref + "实现的接口为" + reason + "大于一个!");
			System.exit(-1);
		}
		for (Class<?> interf : interfs) {
			if (!interf.getName().startsWith("com.zhaopin")) {
				LOGGER.warn("bean of name \"" + beanName + "\" " + ref + "实现了非com.zhaopin包以及子包下的接口!");
			} else {
				ServiceRegisty registy = new ServiceRegisty();
				registy.addInterf(interfs[0]);
				// 这个时候http版本和rpc版本是一致的
				registy.setVersion(VersionUtils.getRpcVersion(thriftService.version()));
				registy.setGroup(thriftService.group());
				registy.setHost(IPResolver.getIP());
				registy.setPort(RpcContext.listenPort);
				registy.setHttpPort(RpcContext.httpPort);
				registy.setContext(RpcContext.Context);
				registy.setRegistry(RpcContext.registy);
				registy.setImpl(ref);
				registy.setWeight(thriftService.weight());
				try {
					registerCallback.callback(registy);
				} catch (Exception exp) {
					LOGGER.error("[thrift] exception", exp);
					exp.printStackTrace();
					// 失败后立即退出
					System.exit(-1);
				}
			}
		}
	}

	private static interface RegisterCallback {

		public void callback(ServiceRegisty registy) throws Exception;

	}

}
