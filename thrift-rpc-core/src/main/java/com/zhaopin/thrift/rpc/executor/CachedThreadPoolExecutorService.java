package com.zhaopin.thrift.rpc.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolExecutorService implements ThreadPoolService {
	
	@Override
	public ExecutorService getExecutorService() {
		return Executors.newCachedThreadPool();
	}

}
