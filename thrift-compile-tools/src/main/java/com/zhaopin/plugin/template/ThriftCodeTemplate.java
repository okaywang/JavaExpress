package com.zhaopin.plugin.template;

import com.zhaopin.plugin.common.ThriftService;
import com.zhaopin.plugin.common.ThriftServiceFunc;
import com.zhaopin.plugin.common.ThriftServiceFuncParam;
import com.zhaopin.plugin.common.ThriftStruct;
import com.zhaopin.plugin.common.ThriftStructField;

public interface ThriftCodeTemplate {

	/**
	 * 函数返回值序列化
	 * 
	 * @param func
	 * @param space
	 * @param service
	 * @return
	 */
	public String genEncode(ThriftServiceFunc func, String space, ThriftService service);

	/**
	 * 函数返回值反序列化
	 * 
	 * @param func
	 * @param space
	 * @param service
	 * @return
	 */
	public String genDecode(ThriftServiceFunc func, String space, ThriftService service);

	/**
	 * 函数参数的序列化
	 * 
	 * @param param
	 * @param space
	 * @param service
	 * @return
	 */
	public String genEncode(ThriftServiceFuncParam param, String space, ThriftService service);

	/**
	 * 函数参数的反序列化
	 * 
	 * @param param
	 * @param space
	 * @param service
	 * @return
	 */
	public String genDecode(ThriftServiceFuncParam param, String space, ThriftService service);

	/**
	 * struct的属性序列化
	 * 
	 * @param field
	 * @param space
	 * @param struct
	 * @return
	 */
	public String genEncode(ThriftStructField field, String space, ThriftStruct struct);

	/**
	 * struct的属性反序列化
	 * 
	 * @param field
	 * @param space
	 * @param struct
	 * @return
	 */
	public String genDecode(ThriftStructField field, String space, ThriftStruct struct);

}
