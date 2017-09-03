package com.zhaopin.plugin.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public final class ThriftDetermine {

	/**
	 * 判断该类是否有@ThriftService和@ThriftStruct
	 * 
	 * @param file
	 *            需要判断的java文件
	 * @return
	 */
	public final static boolean isThriftFile(File file) {
		try {
			// 读入每一行进行分析
			// 判断该类是否有@ThriftService和@ThriftStruct
			List<String> list = FileUtils.readLines(file);
			boolean result = false;
			for (String line : list) {
				// 判断import之后,类定义之前该类是否有@ThriftService和@ThriftStruct
				Pattern serviePattern = Pattern.compile("^@ThriftInterface[ \t]*(.*)");
				Pattern structPattern = Pattern.compile("^@ThriftStruct[ \t]*(.*)");
				Matcher matcher = serviePattern.matcher(line);
				if (matcher.matches()) {
					result = true;
				}
				matcher = structPattern.matcher(line);
				if (matcher.matches()) {
					result = true;
				}
			}
			return result;
		} catch (IOException exp) {
			throw new IllegalStateException(exp);
		}
	}

}
