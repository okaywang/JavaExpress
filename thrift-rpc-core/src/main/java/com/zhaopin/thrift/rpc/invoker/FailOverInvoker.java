package com.zhaopin.thrift.rpc.invoker;

import java.util.concurrent.TimeoutException;

import com.zhaopin.thrift.rpc.exception.RpcTimeoutException;
import com.zhaopin.thrift.rpc.exception.TTransportException;

/**
 * ���÷����һ���ڵ�ʧ�ܺ�᳢�Ե��������һ���ڵ�
 *
 */
public class FailOverInvoker extends DefaultInvoker {

	@Override
	public Object invoke(ThriftInvocation invoker) throws Throwable {
		// �ڷ��������쳣������£���Ҫ����,�������͵Ĵ����糬ʱ�쳣,���׳�
		Throwable fail = null;
		int retry = 0;
		while (retry < invoker.getInvocation().getRetryCount()) {
			try {
				return super.invoke(invoker);
			} catch (TTransportException exp) {
				fail = exp;
				logInfo(exp, invoker);
			} catch (TimeoutException exp) {
				logError(exp, invoker);
				fail = new RpcTimeoutException(exp);
				break;
			} catch (Throwable exp) {
				fail = exp;
				break;
			}
			++retry;
		}
		logError(fail, invoker);
		throw fail;
	}
}
