package com.zhaopin.plugin.generate;

import com.zhaopin.plugin.common.ThriftProject;

public interface ThriftGenerator {
	/**
	 * ����thrift�Ĵ����ļ��������ļ��������ļ�
	 * 
	 * @param thriftProject
	 */
	public void generate(ThriftProject thriftProject);
}
