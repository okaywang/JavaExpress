package com.zhaopin.thrift.rpc.registry;

public class DefaultRegistryTester {

	public static void main(String[] args) throws InterruptedException {
		RegistryService registryService = new DefaultRegistry("172.17.5.81:2181");
		registryService.listen();
		Thread.currentThread().join();
	}

}
