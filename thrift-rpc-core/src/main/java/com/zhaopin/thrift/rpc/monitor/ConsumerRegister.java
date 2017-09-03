package com.zhaopin.thrift.rpc.monitor;

import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.common.collect.Sets;
import com.zhaopin.thrift.rpc.registry.RegistryFactory;
import com.zhaopin.thrift.rpc.registry.RegistryService;

public class ConsumerRegister implements Runnable {
	// 所有的consumer
	private static volatile Set<String> existsConsumers = Sets.newConcurrentHashSet();
	// 到来的consumer(容量足够了)
	private static volatile LinkedBlockingQueue<String> commingConsumers = new LinkedBlockingQueue<String>(2000);

	private String zkAddr;

	public ConsumerRegister(String zkAddr) {
		this.zkAddr = zkAddr;
	}

	@Override
	public void run() {
		RegistryService registryService = RegistryFactory.getRegistry(zkAddr);
		while (true) {
			try {
				String item = commingConsumers.take();
				String[] parts = item.split(":");
				existsConsumers.add(item);
				registryService.registerConsumer(parts[0], parts[1], parts[2]);
			} catch (Exception exp) {
				// 忽略异常
			}
		}

	}

	/**
	 * 只有在新建连接的时候才会运行到这里
	 * 
	 * @param group
	 * @param service
	 * @param version
	 */
	public static void register(String group, String service, String version) {
		String key = String.format("%s:%s:%s", group, service, version);
		if (!existsConsumers.contains(key)) {
			commingConsumers.offer(key);
		}
	}

}
