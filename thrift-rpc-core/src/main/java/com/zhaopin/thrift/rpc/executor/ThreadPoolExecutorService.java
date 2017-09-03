package com.zhaopin.thrift.rpc.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.zhaopin.thrift.rpc.RpcContext;

public class ThreadPoolExecutorService implements ThreadPoolService {

	@Override
	public ExecutorService getExecutorService() {
		// 首先从配置文件中查看线程池的基本配置，如果没有该配置，则使用默认值的配置
		// CPU的线程数
		int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
		if (corePoolSize < RpcContext.ThreadCoreSize) {
			corePoolSize = RpcContext.ThreadCoreSize;
		}
		int maximumPoolSize = Runtime.getRuntime().availableProcessors() * 16;
		if (maximumPoolSize < RpcContext.ThreadMaxSize) {
			maximumPoolSize = RpcContext.ThreadMaxSize;
		}
		int keepAliveTime = RpcContext.ThreadKeepAlive;
		int queueSize = RpcContext.ThreadQueueSize;
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(queueSize);
		return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue,
				new ThreadPoolExecutor.CallerRunsPolicy());
	}
}
