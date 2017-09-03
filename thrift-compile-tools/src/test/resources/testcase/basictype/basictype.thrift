namespace java com.zhaopin.rpcdemo

service BasicTypeService {
	// 测试用例-short
	//@a 这是一个short类型参数
	i16 getShort(1:i16 a);
	// 测试用例-int
	//@a 这是一个int类型参数
	i32 getInt(1:i32 a);
	// 测试用例-long
	//@a 这是一个long类型参数
	i64 getLong(1:i64 a);
	// 测试用例-byte
	//@a 这是一个byte类型参数
	byte getByte(1:byte a);
	// 测试用例-double
	//@a 这是一个double类型参数
	double getDouble(1:double a);
	// 测试用例-string
	//@a 这是一个string类型参数
	string getString(1:string a);
}