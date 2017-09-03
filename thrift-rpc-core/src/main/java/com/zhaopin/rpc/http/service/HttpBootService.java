package com.zhaopin.rpc.http.service;

import java.util.Map;

import com.zhaopin.rpc.config.ThriftConfigure;

public interface HttpBootService {
	
	public void start(ThriftConfigure thriftConfigure, Map<String, Object> beans);

}
