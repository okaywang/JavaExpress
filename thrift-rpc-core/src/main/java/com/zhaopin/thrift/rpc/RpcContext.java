package com.zhaopin.thrift.rpc;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.common.base.Joiner;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;

public final class RpcContext {

	public static final Logger LOGGER = LoggerFactory.getLogger(RpcContext.class);
	// rpc版本号
	public static String thriftVersion = Version.VERSION.toString();
	// rpc监听的端口
	public static int listenPort = 0;
	// http的端口
	public static int httpPort = 0;
	// 注册中心位置
	public static String registy = null;
	// 通道数
	public static int channels = 1;
	// http的虚拟主机
	public static String Context = "";
	// 二进制消息的dump目录
	public static String dumpPath = "/data/logs";
	// 服务执行时间警告的阈值
	public static long Threshhold_WARN = 500;
	// 服务执行时间错误的阈值
	public static long Threshhold_ERROR = 1000;
	// 核心线程数
	public static int ThreadCoreSize = Runtime.getRuntime().availableProcessors() * 2;
	// 最大线程数
	public static int ThreadMaxSize = Runtime.getRuntime().availableProcessors() * 16;
	// 线程keep alive时间
	public static int ThreadKeepAlive = 30;
	// 队列的长度
	public static int ThreadQueueSize = 50000;
	// 统计日志记录位置
	public static String logPath = "/data/logs/statics/statistics.log";
	// 扫描路径
	public static String scanPaths[] = new String[] { "com.zhaopin" };
	// 是否记录统计日志
	public static boolean recordStatics = true;
	// 统计间隔
	public static long interval = 30L * 1000L;
	// 停止服务的标志
	public static int stopService = 0;
	// struct检测的默认目录
	public static String[] thriftCheckPkgs = new String[] { "com.zhaopin" };
	// rpc是否开启校验框架
	public static boolean thriftMethodCheck = true;
	// 是否已经动态编译
	public static AtomicBoolean compileEnvInit = new AtomicBoolean(false);
	// 所有动态编译的类
	public static Map<String, Class<?>> generateClasses = new TreeMap<String, Class<?>>();
	// 记录调用链(不会出现多个线程并发处理里面的每一项的情况)
	private static Cache<String, Set<String>> invokeChain = CacheBuilder.newBuilder().maximumSize(5000).build();
	// 服务调用依赖
	private static Map<String, String> dependents = Maps.newConcurrentMap();

	public static Map<String, String> getDependents() {
		return dependents;
	}

	/**
	 * 
	 * @param taskId
	 * @param service
	 * @param check
	 */
	public static void appendInvokeChainWithCheck(final String taskId, final String service) {
		try {
			Set<String> invokeChains = invokeChain.getIfPresent(taskId);
			if (invokeChains != null) {
				invokeChains.add(service);
			}
		} catch (Exception exp) {
			// 这里面不会出现,目的是为了监控
			LOGGER.error("invoke chain exception", exp);
		}
	}

	/**
	 * 追加或者初始化某一taskId的调用链
	 * 
	 * @param taskId
	 * @param service
	 */
	public static void appendInvokeChain(final String taskId, final String service) {
		try {
			Set<String> invokeChains = invokeChain.getIfPresent(taskId);
			if (invokeChains == null) {
				invokeChains = Sets.newConcurrentHashSet();
				invokeChain.put(taskId, invokeChains);
			}
			invokeChains.add(service);
		} catch (Exception exp) {
			// 这里面不会出现,目的是为了监控
			LOGGER.error("invoke chain exception", exp);
		}
	}

	/**
	 * 移除服务的调用链
	 * 
	 * @param taskId
	 */
	public static void removeInvokeChain(final String taskId) {
		try {
			Set<String> serviceChain = invokeChain.getIfPresent(taskId);
			if (serviceChain != null) {
				invokeChain.invalidate(taskId);
				String strServiceChain = Joiner.on(";").join(serviceChain);
				dependents.put(strServiceChain, "");
			}
		} catch (Exception exp) {
			LOGGER.error("invoke chain exception", exp);
		}
	}

	public static void addGenerateClass(Class<?> clz) {
		generateClasses.put(clz.getName(), clz);
	}

	public static Class<?> getGenerateClass(String className) {
		try {
			Class<?> generateClass = null;
			generateClass = Thread.currentThread().getContextClassLoader().loadClass(className);
			generateClasses.put(className, generateClass);
		} catch (ClassNotFoundException exp) {
			throw new IllegalStateException(exp);
		}
		return generateClasses.get(className);
	}
}
