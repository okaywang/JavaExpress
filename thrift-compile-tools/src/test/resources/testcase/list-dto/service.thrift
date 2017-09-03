namespace java com.zhaopin.plugin.dto
include "struct.thrift"

service PersonService {
	// 测试list dto
	// @list 用户列表
	list<UserInfo> list(1:list<UserInfo> list);
}
