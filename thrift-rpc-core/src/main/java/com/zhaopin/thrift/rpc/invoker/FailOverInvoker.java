package com.zhaopin.thrift.rpc.invoker;

import java.util.concurrent.TimeoutException;

import com.zhaopin.thrift.rpc.exception.RpcTimeoutException;
import com.zhaopin.thrift.rpc.exception.TTransportException;

/**
 * 调用服务的一个节点失败后会尝试调用另外的一个节点
 *
 */
public class FailOverInvoker extends DefaultInvoker {

	@Override
	public Object invoke(ThriftInvocation invoker) throws Throwable {
		// 在发生传输异常的情况下，需要重试,其他类型的错误，如超时异常,则抛出
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
