package com.zhaopin.plugin.parser;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;

import com.zhaopin.plugin.util.ThriftDetermine;

/**
 * 对源代码路径下的文件，判断该类是否有@ThriftService和@ThriftStruct注解
 * 
 * @author shunli.gao
 *
 */
class SourceFilter implements IOFileFilter {

	@Override
	public boolean accept(File file) {
		String fileName = file.getName();
		// 如果该文件是一个目录
		if (file.isDirectory()) {
			return true;
		}
		if (!fileName.endsWith(".java")) {
			return false;
		}
		return ThriftDetermine.isThriftFile(file);
	}

	@Override
	public boolean accept(File dir, String name) {
		return true;
	}
}
