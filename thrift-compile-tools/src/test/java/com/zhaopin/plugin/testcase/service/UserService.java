package com.zhaopin.plugin.testcase.service;

import com.zhaopin.plugin.testcase.dto.UserInfo;
import com.zhaopin.rpc.annotation.ThriftInterface;

@ThriftInterface
public interface UserService {
	
	public UserInfo getUserInfo();
	
	public com.zhaopin.plugin.testcase.entity.UserInfo getUser();
	
}
