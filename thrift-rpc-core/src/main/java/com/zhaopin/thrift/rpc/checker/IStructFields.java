package com.zhaopin.thrift.rpc.checker;

import java.util.Map;

import com.zhaopin.thrift.rpc.common.ThriftStructField;

public interface IStructFields {

	/**
	 * ��ȡ������е��ֶ�
	 * 
	 * @return
	 */
	public Map<Integer, ThriftStructField> getFields();

}
