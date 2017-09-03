package com.zhaopin.thrift.rpc.invoker;

/**
 * 调用服务的一个节点失败后抛出异常返回
 *
 */
public class FailFastInvoker extends DefaultInvoker {
	
	@Override
	public Object invoke(ThriftInvocation invoker) throws Throwable {
		try {
			return super.invoke(invoker);
		} catch (Throwable exp) {
			LOGGER.error("[thrift]exception", exp);
			throw exp;
		}
	}
}
