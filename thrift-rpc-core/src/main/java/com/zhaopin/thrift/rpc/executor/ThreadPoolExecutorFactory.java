package com.zhaopin.thrift.rpc.executor;

import java.util.concurrent.ExecutorService;

public class ThreadPoolExecutorFactory {
	// 服务端线程池方案(cache, simple, pool)
	private static String type = "simple";
	/**
	 * 三种实现方案 CachedThreadPoolExecutorService().getExecutorService()
	 * SimpleExecutorService().getExecutorService();
	 * ThreadPoolExecutorService.getExecutorService();
	 */
	private static ExecutorService executorService = null;

	/**
	 * 获取线程池的具体实现服务
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
