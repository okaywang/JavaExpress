package com.zhaopin.thrift.rpc;

public class Version {
	// ��ǰ�İ汾��
	public static final Version VERSION = new Version("3", "0", "5");
	// ���汾��
	private final String major;
	// �ΰ汾��
	private final String minor;
	// �޶��汾��
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
