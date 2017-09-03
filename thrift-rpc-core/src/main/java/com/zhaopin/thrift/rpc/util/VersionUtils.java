package com.zhaopin.thrift.rpc.util;

public final class VersionUtils {

	public static final String getRpcVersion(String version) {
		String versions = version;
		if (!versions.contains(",")) {
			// 这个时候http版本和rpc版本是一致的
			return versions.trim();
		} else {
			// version的格式是1.0.0,1.2.0, 前面是rpc版本，后面是http版本
			return versions.split(",")[0].trim();
		}
	}

	public static final String getHttpVersion(String version) {
		String versions = version;
		if (!versions.contains(",")) {
			// 这个时候http版本和rpc版本是一致的
			return versions.trim();
		} else {
			// version的格式是1.0.0,1.2.0, 前面是rpc版本，后面是http版本
			return versions.split(",")[1].trim();
		}
	}

}
