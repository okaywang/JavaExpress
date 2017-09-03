package com.zhaopin.thrift.rpc.util;

import java.util.UUID;

public final class UniqueIdUtils {
	
	public static String generate() {
		String requestId = UUID.randomUUID().toString().replace("-", "");
		return requestId;
	}
	
}
