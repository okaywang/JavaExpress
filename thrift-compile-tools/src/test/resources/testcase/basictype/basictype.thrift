namespace java com.zhaopin.rpcdemo

service BasicTypeService {
	// ��������-short
	//@a ����һ��short���Ͳ���
	i16 getShort(1:i16 a);
	// ��������-int
	//@a ����һ��int���Ͳ���
	i32 getInt(1:i32 a);
	// ��������-long
	//@a ����һ��long���Ͳ���
	i64 getLong(1:i64 a);
	// ��������-byte
	//@a ����һ��byte���Ͳ���
	byte getByte(1:byte a);
	// ��������-double
	//@a ����һ��double���Ͳ���
	double getDouble(1:double a);
	// ��������-string
	//@a ����һ��string���Ͳ���
	string getString(1:string a);
}