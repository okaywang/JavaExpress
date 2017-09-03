package com.zhaopin.thrift.rpc.util;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

/**
 * 获取一个类实现的所有的接口
 *
 */
public class InterfaceHelper {

	/**
	 * 获取一个类实现的所有的接口
	 * 
	 * @param objCls
	 *            实现类
	 * @return 接口的列表
	 */
	public static Class<?>[] getClassInterfaces(Class<?> objCls) {
		Set<Class<?>> implInterfs = new HashSet<Class<?>>();
		do {
			Class<?>[] interfs = objCls.getInterfaces();
			if (interfs != null && interfs.length > 0) {
				for (Class<?> cls : interfs) {
					implInterfs.add(cls);
				}
			}
			objCls = objCls.getSuperclass();
		} while (objCls != Object.class && objCls != null);
		return implInterfs.toArray(new Class<?>[implInterfs.size()]);
	}

	public static Class<?>[] getClassInterfacesWithinPkg(Class<?> objCls, String pkg) {
		Set<Class<?>> result = new HashSet<Class<?>>();
		Class<?>[] implInterfs = getClassInterfaces(objCls);
		for (Class<?> implInterf : implInterfs) {
			if (implInterf.getName().startsWith(pkg)) {
				result.add(implInterf);
			}
		}
		return result.toArray(new Class<?>[result.size()]);
	}

	/**
	 * 获取接口里面的所有的方法
	 * 
	 * @param interfaceClass
	 *            接口类
	 * @return
	 */
	public static Method[] getInterfaceMethods(Class<?> interfaceClass) {
		Set<Method> interfaceMethods = new HashSet<Method>();
		// 遍历接口中的方法
		Method[] methods = interfaceClass.getDeclaredMethods();
		for (Method method : methods) {
			if (("toString".equals(method.getName()) || "hashCode".equals(method.getName()))
					&& method.getParameterTypes().length == 0) {
				continue;
			} else if ("equals".equals(method.getName()) && method.getParameterTypes().length == 1) {
				continue;
			}
			int modifier = method.getModifiers();
			// PUBLIC: 1 （二进制 0000 0001）
			if ((modifier & 0x01) != 0) {
				interfaceMethods.add(method);
			}
		}
		return interfaceMethods.toArray(new Method[interfaceMethods.size()]);
	}

	public static Set<String> getInterfaceMethodNames(Class<?> interfaceClass) {
		Method[] methods = getInterfaceMethods(interfaceClass);
		Set<String> methodNames = Sets.newHashSet();
		for (Method method : methods) {
			methodNames.add(method.getName());
		}
		return methodNames;
	}
}
