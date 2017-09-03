package com.zhaopin.plugin.common;

import java.util.ArrayList;
import java.util.List;

public class ThriftThrows {
	// 定义的所有异常
	private final List<String> exceptions = new ArrayList<String>();

	public void addException(String exception) {
		exceptions.add(exception.trim());
	}

	public List<String> getExceptions() {
		return exceptions;
	}
}
