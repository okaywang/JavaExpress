package com.zhaopin.plugin.parser;

import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zhaopin.plugin.common.ThriftProject;
import com.zhaopin.plugin.docparser.DocumentParser;
import com.zhaopin.plugin.docparser.impl.DocumentParserImpl;
import com.zhaopin.plugin.wiki.ThriftWiki;

public class DocumentParserTester {

	@Test
	public void test() {
		String dir = "D:\\develop\\projects\\thrift-4.x\\plat_lib_thrift\\thrift-rpc-admin";
		String srcDir = dir + "\\src\\main\\java";
		String resDir = dir + "\\src\\main\\resources";
		ThriftParser thriftParser = new AnnotationThriftParser(srcDir, resDir);
		ThriftProject thriftProject = thriftParser.parse();
		System.out.println(JSON.toJSONString(thriftProject));
		DocumentParser wikiParser = new DocumentParserImpl();
		List<ThriftWiki> wikis = wikiParser.paserToWiki(thriftProject);
		wikiParser.writeThriftPaserWikiResult(wikis, "./test");
		System.out.println();
	}

}
