package com.zhaopin.thrift.rpc.util;

public final class VersionUtils {

	public static final String getRpcVersion(String version) {
		String versions = version;
		if (!versions.contains(",")) {
			// ���ʱ��http�汾��rpc�汾��һ�µ�
			return versions.trim();
		} else {
			// version�ĸ�ʽ��1.0.0,1.2.0, ǰ����rpc�汾��������http�汾
			return versions.split(",")[0].trim();
		}
	}

	public static final String getHttpVersion(String version) {
		String versions = version;
		if (!versions.contains(",")) {
			// ���ʱ��http�汾��rpc�汾��һ�µ�
			return versions.trim();
		} else {
			// version�ĸ�ʽ��1.0.0,1.2.0, ǰ����rpc�汾��������http�汾
			return versions.split(",")[1].trim();
		}
	}

}
