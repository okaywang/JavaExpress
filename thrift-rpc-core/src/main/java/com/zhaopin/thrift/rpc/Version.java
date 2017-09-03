package com.zhaopin.thrift.rpc;

public class Version {
	// 当前的版本号
	public static final Version VERSION = new Version("3", "0", "5");
	// 主版本号
	private final String major;
	// 次版本号
	private final String minor;
	// 修订版本号
	private final String revise;

	public Version(String major, String minor, String revise) {
		this.major = major;
		this.minor = minor;
		this.revise = revise;
	}

	public String getMajor() {
		return major;
	}

	public String getMinor() {
		return minor;
	}

	public String getRevise() {
		return revise;
	}

	@Override
	public String toString() {
		return String.format("%s.%s.%s", major, minor, revise);
	}
}
