package com.zhaopin.plugin.xml;

import java.io.File;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.zhaopin.plugin.common.ThriftProject;
import com.zhaopin.plugin.parser.AnnotationThriftParser;
import com.zhaopin.plugin.parser.ThriftParser;

public class ServiceXmlGenerateTester {

	@Test
	public void test() {
		String dir = "D:\\develop\\projects\\thrift-4.x\\plat_lib_thrift\\thrift-rpc-core";
		//String dir = new File(".").getCanonicalPath();
		String srcDir = dir + "\\src\\main\\java";
		String resDir = dir + "\\src\\main\\resources";
		ThriftParser thriftParser = new AnnotationThriftParser(srcDir, resDir);
		ThriftProject thriftProject = thriftParser.parse();
		System.out.println(JSON.toJSONString(thriftProject));
		new ServiceXmlGenerate().resolve(Sets.newHashSet(new File(srcDir)), "", thriftProject);
		System.out.println("");
		
	}

}
