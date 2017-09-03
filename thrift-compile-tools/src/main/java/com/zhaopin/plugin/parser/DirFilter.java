package com.zhaopin.plugin.parser;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;

class DirFilter implements IOFileFilter {

	@Override
	public boolean accept(File file) {
		return true;
	}

	@Override
	public boolean accept(File dir, String name) {
		return true;
	}
}
