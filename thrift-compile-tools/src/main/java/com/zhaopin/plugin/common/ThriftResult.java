package com.zhaopin.plugin.common;

public class ThriftResult {
	
	private final String path;

	private final String content;

	public ThriftResult(String path, String content) {
		this.path = path;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public String getPath() {
		return path;
	}
}
