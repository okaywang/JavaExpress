package com.zhaopin.plugin.template;

import com.zhaopin.plugin.common.ThriftService;
import com.zhaopin.plugin.common.ThriftServiceFunc;
import com.zhaopin.plugin.common.ThriftServiceFuncParam;
import com.zhaopin.plugin.common.ThriftStruct;
import com.zhaopin.plugin.common.ThriftStructField;

public interface ThriftCodeTemplate {

	/**
	 * ��������ֵ���л�
	 * 
	 * @param func
	 * @param space
	 * @param service
	 * @return
	 */
	public String genEncode(ThriftServiceFunc func, String space, ThriftService service);

	/**
	 * ��������ֵ�����л�
	 * 
	 * @param func
	 * @param space
	 * @param service
	 * @return
	 */
	public String genDecode(ThriftServiceFunc func, String space, ThriftService service);

	/**
	 * �������������л�
	 * 
	 * @param param
	 * @param space
	 * @param service
	 * @return
	 */
	public String genEncode(ThriftServiceFuncParam param, String space, ThriftService service);

	/**
	 * ���������ķ����л�
	 * 
	 * @param param
	 * @param space
	 * @param service
	 * @return
	 */
	public String genDecode(ThriftServiceFuncParam param, String space, ThriftService service);

	/**
	 * struct���������л�
	 * 
	 * @param field
	 * @param space
	 * @param struct
	 * @return
	 */
	public String genEncode(ThriftStructField field, String space, ThriftStruct struct);

	/**
	 * struct�����Է����л�
	 * 
	 * @param field
	 * @param space
	 * @param struct
	 * @return
	 */
	public String genDecode(ThriftStructField field, String space, ThriftStruct struct);

}
