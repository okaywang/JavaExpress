package com.zhaopin.plugin.parser;

import java.io.IOException;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zhaopin.plugin.common.ThriftProject;

public class AnnotationThriftParserTester {

	@Test
	public void test_parse() throws IOException {
		String dir = "D:\\develop\\projects\\thrift-4.x\\plat_lib_thrift\\thrift-rpc-admin";
		//String dir = new File(".").getCanonicalPath();
		String srcDir = dir + "\\src\\main\\java";
		String resDir = dir + "\\src\\main\\resources";
		ThriftParser thriftParser = new AnnotationThriftParser(srcDir, resDir);
		ThriftProject thriftProject = thriftParser.parse();
		System.out.println(JSON.toJSONString(thriftProject));
	}
}
