namespace java com.zhaopin.plugin.dto
include "struct.thrift"

service PersonService {
	// ����map bool bool
	// @map �û��б�
	map<bool, bool> listBoolBool(1:map<bool, bool> map);
	// ����map bool byte
	// @map �û��б�
	map<bool, byte> listBoolByte(1:map<bool, byte> map);
	// ����map bool I16
	// @map �û��б�
	map<bool, i16> listBoolI16(1:map<bool, i16> map);
	// ����map bool I32
	// @map �û��б�
	map<bool, i32> listBoolI32(1:map<bool, i32> map);
	// ����map bool I64
	// @map �û��б�
	map<bool, i64> listBoolI64(1:map<bool, i64> map);
	// ����map bool double
	// @map �û��б�
	map<bool, double> listBoolDouble(1:map<bool, double> map);
	// ����map bool string
	// @map �û��б�
	map<bool, String> listBoolString(1:map<bool, String> map);
	
	// ����map byte bool
	// @map �û��б�
	map<byte, bool> listByteBool(1:map<byte, bool> map);
	// ����map byte byte
	// @map �û��б�
	map<byte, byte> listByteByte(1:map<byte, byte> map);
	// ����map byte I16
	// @map �û��б�
	map<byte, i16> listByteI16(1:map<byte, i16> map);
	// ����map byte I32
	// @map �û��б�
	map<byte, i32> listByteI32(1:map<byte, i32> map);
	// ����map byte I64
	// @map �û��б�
	map<byte, i64> listByteI64(1:map<byte, i64> map);
	// ����map byte double
	// @map �û��б�
	map<byte, double> listByteDouble(1:map<byte, double> map);
	// ����map byte string
	// @map �û��б�
	map<byte, String> listByteString(1:map<byte, String> map);
	
	// ����map i16 bool
	// @map �û��б�
	map<i16, bool> listI16Bool(1:map<i16, bool> map);
	// ����map byte byte
	// @map �û��б�
	map<i16, byte> listI16Byte(1:map<i16, byte> map);
	// ����map byte I16
	// @map �û��б�
	map<i16, i16> listI16I16(1:map<i16, i16> map);
	// ����map byte I32
	// @map �û��б�
	map<i16, i32> listI16I32(1:map<i16, i32> map);
	// ����map byte I64
	// @map �û��б�
	map<i16, i64> listI16I64(1:map<i16, i64> map);
	// ����map byte double
	// @map �û��б�
	map<i16, double> listI16Double(1:map<i16, double> map);
	// ����map byte string
	// @map �û��б�
	map<i16, String> listI16String(1:map<i16, String> map);
	
	// ����map i32 bool
	// @map �û��б�
	map<i32, bool> listI32Bool(1:map<i32, bool> map);
	// ����map byte byte
	// @map �û��б�
	map<i32, byte> listI32Byte(1:map<i32, byte> map);
	// ����map byte I16
	// @map �û��б�
	map<i32, i16> listI32I16(1:map<i32, i16> map);
	// ����map byte I32
	// @map �û��б�
	map<i32, i32> listI32I32(1:map<i32, i32> map);
	// ����map byte I64
	// @map �û��б�
	map<i32, i64> listI32I64(1:map<i32, i64> map);
	// ����map byte double
	// @map �û��б�
	map<i32, double> listI32Double(1:map<i32, double> map);
	// ����map byte string
	// @map �û��б�
	map<i32, String> listI32String(1:map<i32, String> map);
	
	// ����map i64 bool
	// @map �û��б�
	map<i64, bool> listI64Bool(1:map<i64, bool> map);
	// ����map i64 byte
	// @map �û��б�
	map<i64, byte> listI64Byte(1:map<i64, byte> map);
	// ����map i64 I16
	// @map �û��б�
	map<i64, i16> listI64I16(1:map<i64, i16> map);
	// ����map i64 I32
	// @map �û��б�
	map<i64, i32> listI64I32(1:map<i64, i32> map);
	// ����map i64 I64
	// @map �û��б�
	map<i64, i64> listI64I64(1:map<i64, i64> map);
	// ����map i64 double
	// @map �û��б�
	map<i64, double> listI64Double(1:map<i64, double> map);
	// ����map i64 string
	// @map �û��б�
	map<i64, String> listI64String(1:map<i64, String> map);
	
	// ����map double bool
	// @map �û��б�
	map<double, bool> listDoubleBool(1:map<double, bool> map);
	// ����map double byte
	// @map �û��б�
	map<double, byte> listDoubleByte(1:map<double, byte> map);
	// ����map double I16
	// @map �û��б�
	map<double, i16> listDoubleI16(1:map<double, i16> map);
	// ����map double I32
	// @map �û��б�
	map<double, i32> listDoubleI32(1:map<double, i32> map);
	// ����map double I64
	// @map �û��б�
	map<double, i64> listDoubleI64(1:map<double, i64> map);
	// ����map double double
	// @map �û��б�
	map<double, double> listDoubleDouble(1:map<double, double> map);
	// ����map double string
	// @map �û��б�
	map<double, String> listDoubleString(1:map<double, String> map);
	
	// ����map string bool
	// @map �û��б�
	map<string, bool> listStringBool(1:map<string, bool> map);
	// ����map string byte
	// @map �û��б�
	map<string, byte> listStringByte(1:map<string, byte> map);
	// ����map string I16
	// @map �û��б�
	map<string, i16> listStringI16(1:map<string, i16> map);
	// ����map string I32
	// @map �û��б�
	map<string, i32> listStringI32(1:map<string, i32> map);
	// ����map string I64
	// @map �û��б�
	map<string, i64> listStringI64(1:map<string, i64> map);
	// ����map string double
	// @map �û��б�
	map<string, double> listStringDouble(1:map<string, double> map);
	// ����map string string
	// @map �û��б�
	map<string, String> listStringString(1:map<string, String> map);
}
