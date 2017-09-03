namespace java com.zhaopin.plugin.dto
include "struct.thrift"

service PersonService {
	// ����map bool UserInfo
	// @map �û��б�
	map<bool, UserInfo> listBoolDto(1:map<bool, UserInfo> map);
	// ����map byte UserInfo
	// @map �û��б�
	map<byte, UserInfo> listByteDto(1:map<byte, UserInfo> map);
	// ����map i16 UserInfo
	// @map �û��б�
	map<i16, UserInfo> listI16Dto(1:map<i16, UserInfo> map);
	// ����map i32 UserInfo
	// @map �û��б�
	map<i32, UserInfo> listI32Dto(1:map<i32, UserInfo> map);
	// ����map i64 UserInfo
	// @map �û��б�
	map<i64, UserInfo> listI64Dto(1:map<i64, UserInfo> map);
	// ����map double UserInfo
	// @map �û��б�
	map<double, UserInfo> listDoubleDto(1:map<double, UserInfo> map);
	// ����map string UserInfo
	// @map �û��б�
	map<string, UserInfo> listStringDto(1:map<string, UserInfo> map);
}
