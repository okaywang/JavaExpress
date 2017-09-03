package com.zhaopin.plugin.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public final class ThriftDetermine {

	/**
	 * �жϸ����Ƿ���@ThriftService��@ThriftStruct
	 * 
	 * @param file
	 *            ��Ҫ�жϵ�java�ļ�
	 * @return
	 */
	public final static boolean isThriftFile(File file) {
		try {
			// ����ÿһ�н��з���
			// �жϸ����Ƿ���@ThriftService��@ThriftStruct
			List<String> list = FileUtils.readLines(file);
			boolean result = false;
			for (String line : list) {
				// �ж�import֮��,�ඨ��֮ǰ�����Ƿ���@ThriftService��@ThriftStruct
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
