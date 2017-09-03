package com.zhaopin.thrift.rpc.invoker;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;

public interface ThriftInvoker {
	
	public static Logger LOGGER = LoggerFactory.getLogger(ThriftInvoker.class);
	
	public Object invoke(ThriftInvocation invoker) throws Throwable;

}
