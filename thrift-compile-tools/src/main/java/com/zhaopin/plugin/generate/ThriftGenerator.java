package com.zhaopin.plugin.generate;

import com.zhaopin.plugin.common.ThriftProject;

public interface ThriftGenerator {
	/**
	 * 生成thrift的处理文件，调用文件，代理文件
	 * 
	 * @param thriftProject
	 */
	public void generate(ThriftProject thriftProject);
}
