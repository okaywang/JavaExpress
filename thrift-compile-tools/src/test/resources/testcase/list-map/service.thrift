namespace java com.zhaopin.plugin.dto
include "struct.thrift"

service PersonService {
	// 测试list map i32
	// @map 用户列表
	list<map<i32, UserInfo>> list(1:list<map<i32, UserInfo>> map);
	// 测试list map bool
	// @map 用户列表
	list<map<bool, UserInfo>> list(1:list<map<bool, UserInfo>> map);
	// 测试list map byte
	// @map 用户列表
	list<map<byte, UserInfo>> list(1:list<map<byte, UserInfo>> map);
	// 测试list map i16
	// @map 用户列表
	list<map<i16, UserInfo>> list(1:list<map<i16, UserInfo>> map);
	// 测试list map i32
	// @map 用户列表
	list<map<i32, UserInfo>> list(1:list<map<i32, UserInfo>> map);
	// 测试list map i64
	// @map 用户列表
	list<map<i64, UserInfo>> list(1:list<map<i64, UserInfo>> map);
	// 测试list map i64
	// @map 用户列表
	list<map<double, UserInfo>> list(1:list<map<double, UserInfo>> map);
	// 测试list map string
	// @map 用户列表
	list<map<string, UserInfo>> list(1:list<map<string, UserInfo>> map);
}
