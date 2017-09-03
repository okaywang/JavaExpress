package com.zhaopin.plugin.wiki;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.zhaopin.plugin.parser.AnnotationThriftParser;
import com.zhaopin.plugin.parser.ThriftParser;

public class ThriftDocumentParser extends AbstractDocumentParser {
	
	public ThriftDocumentParser(File srcDir, File resDir) {
		super(srcDir, resDir);
	}

	@Override
	public List<ThriftWiki> generateWiki() {
		ThriftParser thriftParser = new AnnotationThriftParser(getSrcDir(), getResDir());
		thriftParser.parse();
		List<ThriftWiki> wikis = new ArrayList<ThriftWiki>();
		return wikis;
	}
}
