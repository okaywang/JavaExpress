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

struct BasicListDto {
	//这是一个list<string>类型参数
	1:list<string> list_string;
	//这是一个list<i16>类型参数
	2:list<i16> list_short;
	//这是一个list<i32>类型参数
	3:list<i32> list_int;
	//这是一个list<i64>类型参数
	4:list<i64> list_long;
	//这是一个list<byte>类型参数
	5:list<byte> list_byte;
	//这是一个list<double>类型参数
	6:list<double> list_double;
	//这是一个list<bool>类型参数
	7:list<bool> list_bool;
}

struct BasicMapDto {
	//这是一个map<string,string>类型参数
	1:map<string,string> map_string;
	//这是一个map<string,short>类型参数
	2:map<string,i16> map_short;
	//这是一个map<string,int>类型参数
	3:map<string,i32> map_int;
	//这是一个map<string,long>类型参数
	4:map<string,i64> map_long;
	//这是一个map<string,byte>类型参数
	5:map<string,byte> map_byte;
	//这是一个map<string,double>类型参数
	6:map<string,double> map_double;
	//这是一个map<string,boolean>类型参数
	7:map<string,bool> map_bool;
}

struct DtoTypeDto {
	//这个是一个简单实体的实体参数
	1:BasicTypeDto dto_basic;
	//这是一个简单list实体的实体参数
	2:BasicListDto dto_basic_list;
	//这是一个简单map实体的实体参数
	3:BasicMapDto dto_basic_map;
}

struct ListDtoTypeDto {
	//这是一个基本类型实体的list参数
	1:list<BasicTypeDto> list_basic_dto;
	//这是一个基本list类型实体的list参数
	2:list<BasicListDto> list_basic_list;
	//这是一个基本map类型实体的list参数
	3:list<BasicMapDto> list_basic_map;
	//这是一个实体类型实体的list参数
	4:list<DtoTypeDto> list_dto_dto;
}

struct MapDtoTypeDto {
	//这是一个基本类型实体的map参数
	1:map<string,BasicTypeDto> map_basic_dto;
	//这是一个基本list类型实体的map参数
	2:map<string,BasicListDto> map_basic_list;
	//这是一个基本map类型实体的map参数
	3:map<string,BasicMapDto> map_basic_map;
	//这是一个实体类型实体的map参数
	4:map<string,DtoTypeDto> map_dto_dto;
}

service DtoTypeService {
	// 测试用例-简单实体
	//@dto 这是一个简单类型的实体参数
	BasicTypeDto getBasicTypeDto(1:BasicTypeDto dto);
	// 测试用例-简单list实体
	//@dto 这是一个简单list类型的实体参数
	BasicListDto getBasicListDto(1:BasicListDto dto);
	// 测试用例-简单map实体
	//@dto 这是一个简单map类型的实体参数
	BasicMapDto getBasicMapDto(1:BasicMapDto dto);
	// 测试用例-实体类型的实体参数
	//@dto 这是一个实体类型的实体参数
	DtoTypeDto getDtoTypeDto(1:DtoTypeDto dto)
	// 测试用例-list实体类型的实体参数
	//@dto 这是一个list实体类型的实体参数
	ListDtoTypeDto getListDtoTypeDto(1:ListDtoTypeDto dto);
	// 测试用例-map实体类型的实体参数
	//@dto 这是一个map实体类型的实体参数
	MapDtoTypeDto getMapDtoTypeDto(1:MapDtoTypeDto dto);
}