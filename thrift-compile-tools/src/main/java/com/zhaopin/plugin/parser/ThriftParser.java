package com.zhaopin.plugin.parser;

import com.zhaopin.plugin.common.ThriftProject;

public interface ThriftParser {
	/**
	 * ������Ŀ�еı�ע��@ThriftInterface��@ThriftStruct���� ��@ThriftInterface����ת���ɷ�����úʹ���ʵ��
	 * ��@ThriftStruct����ת����dto�����л�ʵ��
	 * 
	 * @return �������thrift���ݽṹ, ����struct��service
	 */
	public ThriftProject parse();
}
