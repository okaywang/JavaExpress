namespace java com.zhaopin.rpcdemo

struct BasicTypeDto {
	//这是一个string类型参数
	1:string a;
	//这是一个short类型参数
	2:i16 b;
	//这是一个int类型参数
	3:i32 c;
	//这是一个long类型参数
	4:i64 d;
	//这是一个byte类型参数
	5:byte e;
	//这是一个double类型参数
	6:double f;
	//这是一个boolean类型参数
	7:bool g;
}

struct DtoTyDto {
	//这是一个基本类型参数
	1:string str;
	//基本类型list
	2:list<string> list_string;
	//基本类型map
	3:map<string,string> map_string;
	//基本类型实体
	4:BasicTypeDto dto;
	//实体类型list
	5:list<BasicTypeDto> list_dto;
	//实体类型map
	6:map<string,BasicTypeDto> map_dto;
}

service ListTypeService {
	// 测试用例-list-string
	//@list_string 这是一个list<string>类型参数
	list<string> getListString(1:list<string> list_string);
	// 测试用例-list-short
	//@list_short 这是一个list<i16>类型参数
	list<i16> getListI16(1:list<i16> list_short);
	// 测试用例-list-int
	//@list_int 这是一个list<i32>类型参数
	list<i32> getListI32(1:list<i32> list_int);
	// 测试用例-list-long
	//@list_long 这是一个list<i64>类型参数
	list<i64> getListI64(1:list<i64> list_long);
	// 测试用例-list-byte
	//@list_byte 这是一个list<byte>类型参数
	list<byte> getListByte(1:list<byte> list_byte);
	// 测试用例-list-double
	//@list_double 这是一个list<double>类型参数
	list<double> getListDouble(1:list<double> list_double);
	// 测试用例-list-bool
	//@list_bool 这是一个list<bool>类型参数
	list<bool> getListBool(1:list<bool> list_bool); 
	// 测试用例-list-BasicTypeDto
	//@list_basic_dto 这是一个list<BasicTypeDto>类型参数
	list<BasicTypeDto> getListBasicTypeDto(1:list<BasicTypeDto> list_basic_dto);
	// 测试用例-list-DtoTyDto
	//@list_dto_dto 这是一个list<DtoTyDto>类型参数
	list<DtoTyDto> getListDtoTyDto(1:list<DtoTyDto> list_dto_dto);
}