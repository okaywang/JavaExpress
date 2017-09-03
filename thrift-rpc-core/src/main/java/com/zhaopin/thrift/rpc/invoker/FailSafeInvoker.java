package com.zhaopin.thrift.rpc.invoker;

import com.zhaopin.thrift.rpc.exception.RpcException;

/**
 * 调用服务的一个节点失败后不会抛出异常，返回null
 *
 */
public class FailSafeInvoker extends DefaultInvoker {

	/**
	 * failsafe集群策略下，如果调用失败，则返回null给客户端
	 */
	@Override
	public Object invoke(ThriftInvocation invoker) throws Throwable {
		try {
			return super.invoke(invoker);
		} catch (RpcException exp) {
			LOGGER.error("[thrift]exception", exp);
			throw exp;
		} catch (Throwable exp) {
			LOGGER.error("[thrift]exception", exp);
			return null;
		}
	}

}
