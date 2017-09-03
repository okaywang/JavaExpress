package com.zhaopin.thrift.rpc.validate;

import java.lang.reflect.Method;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;

public interface IValidator {
	
	public static Logger LOGGER = LoggerFactory.getLogger(IValidator.class);
	
	public void validate(Object target, Method method, Object[] params);

}
