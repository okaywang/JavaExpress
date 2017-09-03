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

struct BasicListDto {
	//����һ��list<string>���Ͳ���
	1:list<string> list_string;
	//����һ��list<i16>���Ͳ���
	2:list<i16> list_short;
	//����һ��list<i32>���Ͳ���
	3:list<i32> list_int;
	//����һ��list<i64>���Ͳ���
	4:list<i64> list_long;
	//����һ��list<byte>���Ͳ���
	5:list<byte> list_byte;
	//����һ��list<double>���Ͳ���
	6:list<double> list_double;
	//����һ��list<bool>���Ͳ���
	7:list<bool> list_bool;
}

struct BasicMapDto {
	//����һ��map<string,string>���Ͳ���
	1:map<string,string> map_string;
	//����һ��map<string,short>���Ͳ���
	2:map<string,i16> map_short;
	//����һ��map<string,int>���Ͳ���
	3:map<string,i32> map_int;
	//����һ��map<string,long>���Ͳ���
	4:map<string,i64> map_long;
	//����һ��map<string,byte>���Ͳ���
	5:map<string,byte> map_byte;
	//����һ��map<string,double>���Ͳ���
	6:map<string,double> map_double;
	//����һ��map<string,boolean>���Ͳ���
	7:map<string,bool> map_bool;
}

struct DtoTypeDto {
	//�����һ����ʵ���ʵ�����
	1:BasicTypeDto dto_basic;
	//����һ����listʵ���ʵ�����
	2:BasicListDto dto_basic_list;
	//����һ����mapʵ���ʵ�����
	3:BasicMapDto dto_basic_map;
}

struct ListDtoTypeDto {
	//����һ����������ʵ���list����
	1:list<BasicTypeDto> list_basic_dto;
	//����һ������list����ʵ���list����
	2:list<BasicListDto> list_basic_list;
	//����һ������map����ʵ���list����
	3:list<BasicMapDto> list_basic_map;
	//����һ��ʵ������ʵ���list����
	4:list<DtoTypeDto> list_dto_dto;
}

struct MapDtoTypeDto {
	//����һ����������ʵ���map����
	1:map<string,BasicTypeDto> map_basic_dto;
	//����һ������list����ʵ���map����
	2:map<string,BasicListDto> map_basic_list;
	//����һ������map����ʵ���map����
	3:map<string,BasicMapDto> map_basic_map;
	//����һ��ʵ������ʵ���map����
	4:map<string,DtoTypeDto> map_dto_dto;
}

service DtoTypeService {
	// ��������-��ʵ��
	//@dto ����һ�������͵�ʵ�����
	BasicTypeDto getBasicTypeDto(1:BasicTypeDto dto);
	// ��������-��listʵ��
	//@dto ����һ����list���͵�ʵ�����
	BasicListDto getBasicListDto(1:BasicListDto dto);
	// ��������-��mapʵ��
	//@dto ����һ����map���͵�ʵ�����
	BasicMapDto getBasicMapDto(1:BasicMapDto dto);
	// ��������-ʵ�����͵�ʵ�����
	//@dto ����һ��ʵ�����͵�ʵ�����
	DtoTypeDto getDtoTypeDto(1:DtoTypeDto dto)
	// ��������-listʵ�����͵�ʵ�����
	//@dto ����һ��listʵ�����͵�ʵ�����
	ListDtoTypeDto getListDtoTypeDto(1:ListDtoTypeDto dto);
	// ��������-mapʵ�����͵�ʵ�����
	//@dto ����һ��mapʵ�����͵�ʵ�����
	MapDtoTypeDto getMapDtoTypeDto(1:MapDtoTypeDto dto);
}