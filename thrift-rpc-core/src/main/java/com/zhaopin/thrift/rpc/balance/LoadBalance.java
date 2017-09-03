package com.zhaopin.thrift.rpc.balance;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.thrift.rpc.common.ServerNode;
import com.zhaopin.thrift.rpc.invoker.ThriftInvocation;

public interface LoadBalance {
	
	public static Logger LOGGER = LoggerFactory.getLogger(LoadBalance.class);
	
	public ServerNode select(ThriftInvocation invocation);

}
