package com.zhaopin.thrift.rpc.registry;

import com.alibaba.fastjson.JSON;

public class GrayPubRegistryServiceTester {

	public static void main(String[] args) throws Exception {
		GrayPubRegistry grayPubRegistryService = new GrayPubRegistry("127.0.0.1:2181");
		grayPubRegistryService.listen();
		GrayPubService grayPub = grayPubRegistryService.getGrayPubService("1000", "plat",
				"com.zhaopin.user.UserService", "1.1.0");
		System.out.println(JSON.toJSONString(grayPub));
		Thread.currentThread().join();
	}

}
