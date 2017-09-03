package com.zhaopin.thrift.rpc.checker;

import java.util.Map;

import com.zhaopin.thrift.rpc.common.ThriftStructField;

public interface IStructFields {

	/**
	 * 获取类的所有的字段
	 * 
	 * @return
	 */
	public Map<Integer, ThriftStructField> getFields();

}
