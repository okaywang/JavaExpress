package com.zhaopin.thrift.rpc.invoker;

/**
 * ���÷����һ���ڵ�ʧ�ܺ��׳��쳣����
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
