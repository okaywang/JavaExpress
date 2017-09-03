package com.zhaopin.thrift.rpc.invoker;

import com.zhaopin.thrift.rpc.exception.RpcException;

/**
 * ���÷����һ���ڵ�ʧ�ܺ󲻻��׳��쳣������null
 *
 */
public class FailSafeInvoker extends DefaultInvoker {

	/**
	 * failsafe��Ⱥ�����£��������ʧ�ܣ��򷵻�null���ͻ���
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
