package com.zhaopin.thrift.rpc.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.zhaopin.thrift.rpc.RpcContext;

public class SimpleExecutorService implements ThreadPoolService {

	@Override
	public ExecutorService getExecutorService() {
		// ���ȴ������ļ��в鿴�̳߳صĻ������ã����û�и����ã���ʹ��Ĭ��ֵ������
		// CPU���߳���
		int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
		if (corePoolSize < RpcContext.ThreadCoreSize) {
			corePoolSize = RpcContext.ThreadCoreSize;
		}
		int maximumPoolSize = Runtime.getRuntime().availableProcessors() * 16;
		if (maximumPoolSize < RpcContext.ThreadMaxSize) {
			maximumPoolSize = RpcContext.ThreadMaxSize;
		}
		int keepAliveTime = RpcContext.ThreadKeepAlive;
		BlockingQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();
		return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue,
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

}
