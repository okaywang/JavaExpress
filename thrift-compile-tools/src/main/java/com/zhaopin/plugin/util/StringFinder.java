package com.zhaopin.plugin.util;

public class StringFinder {
	public static int findIndex(String value, String tag, int pos) {
		if (pos < 1) {
			throw new IllegalArgumentException("第三个参数不能小于1!");
		}
		int fromIndex = 0, index = -1;
		do {
			index = value.indexOf(tag, fromIndex);
			if (index < 0) {
				return -1;
			}
			fromIndex = index + tag.length();
		} while (--pos > 0);
		return index;
	}
}
