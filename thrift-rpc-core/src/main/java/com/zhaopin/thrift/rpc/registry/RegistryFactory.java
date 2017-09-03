package com.zhaopin.thrift.rpc.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RegistryFactory {

	private static volatile Map<String, RegistryService> registryServices = new HashMap<String, RegistryService>();

	private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);

	/**
	 * 系统运行前进行初始化
	 * 
	 * @param zkAddr
	 */
	public static void initRegistry(final String zkAddr) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			RegistryService registryService = registryServices.get(zkAddr);
			if (registryService == null) {
				registryService = new GrayPubRegistry(zkAddr);
				registryServices.put(zkAddr, registryService);
				registryService.listen();
			}
		} finally {
			writeLock.unlock();
		}
	}

	@Deprecated
	public static RegistryService newInstance(String zkAddr) {
		return getRegistry(zkAddr);
	}

	/**
	 * 获取注册中心,一定是经过系统初始化后的
	 * 
	 * @param zkAddr
	 * @return
	 */
	public static RegistryService getRegistry(String zkAddr) {
		RegistryService registryService = null;
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			registryService = registryServices.get(zkAddr);
			if (registryService != null) {
				return registryService;
			}
		} finally {
			readLock.unlock();
		}
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			registryService = registryServices.get(zkAddr);
			if (registryService != null) {
				return registryService;
			} else {
				registryService = new GrayPubRegistry(zkAddr);
				registryServices.put(zkAddr, registryService);
				registryService.listen();
				return registryService;
			}
		} finally {
			writeLock.unlock();
		}
	}
}
