namespace java com.zhaopin.plugin.dto
include "struct.thrift"

service PersonService {
	// ����list dto
	// @list �û��б�
	list<UserInfo> list(1:list<UserInfo> list);
}
