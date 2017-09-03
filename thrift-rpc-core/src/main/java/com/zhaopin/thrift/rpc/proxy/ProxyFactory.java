package com.zhaopin.thrift.rpc.proxy;

import java.lang.reflect.Proxy;

import com.zhaopin.thrift.rpc.common.Invocation;

/**
 * ����Ĺ�����
 *
 */
public class ProxyFactory {

	public static Object getProxy(Invocation invoker, Class<?>[] types) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		return Proxy.newProxyInstance(classLoader, types, new InvokerInvocationHandler(invoker));
	}

}
