namespace java com.zhaopin.rpcdemo

struct BasicTypeDto {
	//����һ��string���Ͳ���
	1:string a;
	//����һ��short���Ͳ���
	2:i16 b;
	//����һ��int���Ͳ���
	3:i32 c;
	//����һ��long���Ͳ���
	4:i64 d;
	//����һ��byte���Ͳ���
	5:byte e;
	//����һ��double���Ͳ���
	6:double f;
	//����һ��boolean���Ͳ���
	7:bool g;
}

struct DtoTyDto {
	//����һ���������Ͳ���
	1:string str;
	//��������list
	2:list<string> list_string;
	//��������map
	3:map<string,string> map_string;
	//��������ʵ��
	4:BasicTypeDto dto;
	//ʵ������list
	5:list<BasicTypeDto> list_dto;
	//ʵ������map
	6:map<string,BasicTypeDto> map_dto;
}

service ListTypeService {
	// ��������-list-string
	//@list_string ����һ��list<string>���Ͳ���
	list<string> getListString(1:list<string> list_string);
	// ��������-list-short
	//@list_short ����һ��list<i16>���Ͳ���
	list<i16> getListI16(1:list<i16> list_short);
	// ��������-list-int
	//@list_int ����һ��list<i32>���Ͳ���
	list<i32> getListI32(1:list<i32> list_int);
	// ��������-list-long
	//@list_long ����һ��list<i64>���Ͳ���
	list<i64> getListI64(1:list<i64> list_long);
	// ��������-list-byte
	//@list_byte ����һ��list<byte>���Ͳ���
	list<byte> getListByte(1:list<byte> list_byte);
	// ��������-list-double
	//@list_double ����һ��list<double>���Ͳ���
	list<double> getListDouble(1:list<double> list_double);
	// ��������-list-bool
	//@list_bool ����һ��list<bool>���Ͳ���
	list<bool> getListBool(1:list<bool> list_bool); 
	// ��������-list-BasicTypeDto
	//@list_basic_dto ����һ��list<BasicTypeDto>���Ͳ���
	list<BasicTypeDto> getListBasicTypeDto(1:list<BasicTypeDto> list_basic_dto);
	// ��������-list-DtoTyDto
	//@list_dto_dto ����һ��list<DtoTyDto>���Ͳ���
	list<DtoTyDto> getListDtoTyDto(1:list<DtoTyDto> list_dto_dto);
}