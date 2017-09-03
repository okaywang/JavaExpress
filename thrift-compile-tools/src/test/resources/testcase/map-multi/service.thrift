namespace java com.zhaopin.plugin.dto
include "struct.thrift"

service PersonService {
	// 测试map bool UserInfo
	// @map 用户列表
	map<bool, UserInfo> listBoolDto(1:map<bool, UserInfo> map);
	// 测试map byte UserInfo
	// @map 用户列表
	map<byte, UserInfo> listByteDto(1:map<byte, UserInfo> map);
	// 测试map i16 UserInfo
	// @map 用户列表
	map<i16, UserInfo> listI16Dto(1:map<i16, UserInfo> map);
	// 测试map i32 UserInfo
	// @map 用户列表
	map<i32, UserInfo> listI32Dto(1:map<i32, UserInfo> map);
	// 测试map i64 UserInfo
	// @map 用户列表
	map<i64, UserInfo> listI64Dto(1:map<i64, UserInfo> map);
	// 测试map double UserInfo
	// @map 用户列表
	map<double, UserInfo> listDoubleDto(1:map<double, UserInfo> map);
	// 测试map string UserInfo
	// @map 用户列表
	map<string, UserInfo> listStringDto(1:map<string, UserInfo> map);
}
