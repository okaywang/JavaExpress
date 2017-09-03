package com.zhaopin.thrift.rpc.logger;

import com.zhaopin.common.log.Logger;

public  class ThriftLoggerFactory {

	public static Logger getLogger(Class<?> clazz) {
		return com.zhaopin.common.log.LoggerFactory.getLogger(clazz);
	}

}
