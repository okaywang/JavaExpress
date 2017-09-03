package com.zhaopin.plugin.generate;

import java.io.File;

public abstract class AbstractThriftGenerator implements ThriftGenerator {
	// Դ�ļ�Ŀ¼
	private final File srcDir;

	public AbstractThriftGenerator(String srcDir) {
		this(new File(srcDir));
	}

	public AbstractThriftGenerator(File srcDir) {
		this.srcDir = srcDir;
	}

	public File getSrcDir() {
		return srcDir;
	}
	
	public String getSrcPath() {
		try {
			return srcDir.getCanonicalPath();
		} catch (Exception exp) {
			throw new IllegalStateException(exp);
		}
	}
}
