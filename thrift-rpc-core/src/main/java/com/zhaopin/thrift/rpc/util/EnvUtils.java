package com.zhaopin.thrift.rpc.util;

import java.io.File;

public final class EnvUtils {

	private static boolean localEnv = detectEnv();

	private static boolean useV4 = detectIsUseV4();

	public static boolean isUseV4() {
		return useV4;
	}

	private static boolean detectIsUseV4() {
		String runEnv = System.getProperty("use_v4", "0");
		if ("1".equals(runEnv)) {
			return true;
		}
		return false;
	}

	public static boolean islocalEnv() {
		return localEnv;
	}

	public static boolean detectEnv() {
		// 如果是windows环境，或者mac环境，基本上是本机电脑
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			return true;
		} else if (os.toLowerCase().startsWith("mac")) {
			return true;
		}
		boolean env = false;
		// 判断当前运行的环境
		File file = new File("src");
		if (file.exists()) {
			file = new File(file, "main");
			if (file.exists()) {
				file = new File(file, "java");
				if (file.exists()) {
					env = true;
				}
			}
		}
		return env;
	}

	public static boolean isRegZk() {
		String runEnv = System.getProperty("reg_zk", "on");
		if ("off".equals(runEnv)) {
			return false;
		}
		return true;
	}

}
