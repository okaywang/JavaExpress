namespace java com.zhaopin.plugin.dto
include "struct.thrift"

service PersonService {
	// ����list map i32
	// @map �û��б�
	list<map<i32, UserInfo>> list(1:list<map<i32, UserInfo>> map);
	// ����list map bool
	// @map �û��б�
	list<map<bool, UserInfo>> list(1:list<map<bool, UserInfo>> map);
	// ����list map byte
	// @map �û��б�
	list<map<byte, UserInfo>> list(1:list<map<byte, UserInfo>> map);
	// ����list map i16
	// @map �û��б�
	list<map<i16, UserInfo>> list(1:list<map<i16, UserInfo>> map);
	// ����list map i32
	// @map �û��б�
	list<map<i32, UserInfo>> list(1:list<map<i32, UserInfo>> map);
	// ����list map i64
	// @map �û��б�
	list<map<i64, UserInfo>> list(1:list<map<i64, UserInfo>> map);
	// ����list map i64
	// @map �û��б�
	list<map<double, UserInfo>> list(1:list<map<double, UserInfo>> map);
	// ����list map string
	// @map �û��б�
	list<map<string, UserInfo>> list(1:list<map<string, UserInfo>> map);
}
