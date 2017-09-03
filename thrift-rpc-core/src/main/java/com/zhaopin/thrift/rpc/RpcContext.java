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
	// rpc�汾��
	public static String thriftVersion = Version.VERSION.toString();
	// rpc�����Ķ˿�
	public static int listenPort = 0;
	// http�Ķ˿�
	public static int httpPort = 0;
	// ע������λ��
	public static String registy = null;
	// ͨ����
	public static int channels = 1;
	// http����������
	public static String Context = "";
	// ��������Ϣ��dumpĿ¼
	public static String dumpPath = "/data/logs";
	// ����ִ��ʱ�侯�����ֵ
	public static long Threshhold_WARN = 500;
	// ����ִ��ʱ��������ֵ
	public static long Threshhold_ERROR = 1000;
	// �����߳���
	public static int ThreadCoreSize = Runtime.getRuntime().availableProcessors() * 2;
	// ����߳���
	public static int ThreadMaxSize = Runtime.getRuntime().availableProcessors() * 16;
	// �߳�keep aliveʱ��
	public static int ThreadKeepAlive = 30;
	// ���еĳ���
	public static int ThreadQueueSize = 50000;
	// ͳ����־��¼λ��
	public static String logPath = "/data/logs/statics/statistics.log";
	// ɨ��·��
	public static String scanPaths[] = new String[] { "com.zhaopin" };
	// �Ƿ��¼ͳ����־
	public static boolean recordStatics = true;
	// ͳ�Ƽ��
	public static long interval = 30L * 1000L;
	// ֹͣ����ı�־
	public static int stopService = 0;
	// struct����Ĭ��Ŀ¼
	public static String[] thriftCheckPkgs = new String[] { "com.zhaopin" };
	// rpc�Ƿ���У����
	public static boolean thriftMethodCheck = true;
	// �Ƿ��Ѿ���̬����
	public static AtomicBoolean compileEnvInit = new AtomicBoolean(false);
	// ���ж�̬�������
	public static Map<String, Class<?>> generateClasses = new TreeMap<String, Class<?>>();
	// ��¼������(������ֶ���̲߳������������ÿһ������)
	private static Cache<String, Set<String>> invokeChain = CacheBuilder.newBuilder().maximumSize(5000).build();
	// �����������
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
			// �����治�����,Ŀ����Ϊ�˼��
			LOGGER.error("invoke chain exception", exp);
		}
	}

	/**
	 * ׷�ӻ��߳�ʼ��ĳһtaskId�ĵ�����
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
			// �����治�����,Ŀ����Ϊ�˼��
			LOGGER.error("invoke chain exception", exp);
		}
	}

	/**
	 * �Ƴ�����ĵ�����
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
