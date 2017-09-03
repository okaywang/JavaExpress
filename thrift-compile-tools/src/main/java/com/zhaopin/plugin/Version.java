package com.zhaopin.plugin;

public class Version {

	public static final Version version = new Version("3", "1", "2");

	private final String major;

	private final String minor;

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
