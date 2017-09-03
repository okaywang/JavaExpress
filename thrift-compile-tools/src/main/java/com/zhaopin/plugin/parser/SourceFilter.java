package com.zhaopin.plugin.parser;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;

import com.zhaopin.plugin.util.ThriftDetermine;

/**
 * ��Դ����·���µ��ļ����жϸ����Ƿ���@ThriftService��@ThriftStructע��
 * 
 * @author shunli.gao
 *
 */
class SourceFilter implements IOFileFilter {

	@Override
	public boolean accept(File file) {
		String fileName = file.getName();
		// ������ļ���һ��Ŀ¼
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
