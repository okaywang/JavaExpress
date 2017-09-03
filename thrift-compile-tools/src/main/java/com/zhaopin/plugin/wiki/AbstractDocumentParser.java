package com.zhaopin.plugin.wiki;

import java.io.File;

public abstract class AbstractDocumentParser implements DocumentParser {
	
	private final File srcDir;
	
	private final File resDir;
	
	public AbstractDocumentParser(File srcDir, File resDir) {
		this.srcDir = srcDir;
		this.resDir = resDir;
	}

	public File getResDir() {
		return resDir;
	}

	public File getSrcDir() {
		return srcDir;
	}
}
