package com.zhaopin.thrift.rpc.executor;

import java.util.concurrent.ExecutorService;

public class ThreadPoolExecutorFactory {
	// ������̳߳ط���(cache, simple, pool)
	private static String type = "simple";
	/**
	 * ����ʵ�ַ��� CachedThreadPoolExecutorService().getExecutorService()
	 * SimpleExecutorService().getExecutorService();
	 * ThreadPoolExecutorService.getExecutorService();
	 */
	private static ExecutorService executorService = null;

	/**
	 * ��ȡ�̳߳صľ���ʵ�ַ���
	 * 
	 * @return
	 */
	public synchronized static ExecutorService getThreadPoolExecutor() {
		if (executorService == null) {
			if ("simple".equals(type)) {
				executorService = new SimpleExecutorService().getExecutorService();
			} else if ("pool".equals(type)) {
				executorService = new ThreadPoolExecutorService().getExecutorService();
			} else {
				executorService = new CachedThreadPoolExecutorService().getExecutorService();
			}
		}
		return executorService;
	}

	public static void setType(String type) {
		ThreadPoolExecutorFactory.type = type;
	}

}
