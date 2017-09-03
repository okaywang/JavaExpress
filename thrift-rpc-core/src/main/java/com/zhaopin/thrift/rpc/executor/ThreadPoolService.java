package com.zhaopin.thrift.rpc.executor;

import java.util.concurrent.ExecutorService;

public interface ThreadPoolService {
	
	public ExecutorService getExecutorService();
}
