package com.zhaopin.thrift.rpc.registry;

import org.junit.Test;

public class _GrayPubRegistryTester {
	
	@Test
	public void test() {
		new DefaultRegistry("172.17.5.81:2181").listen();
	}

}
