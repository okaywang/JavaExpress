package com.zhaopin.thrift.rpc.lock;

import org.junit.Test;

import com.zhaopin.thrift.rpc.transport.ThriftChannels;

public class ThriftChannelTester {

	@Test
	public void test() {
		for (int t = 0; t < 2000; ++t) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					ThriftChannels.connect("172.17.6.50", 32289, 1);

				}
			}).start();
		}
	}

}
