namespace java com.zhaopin.plugin.dto
include "struct.thrift"

service PersonService {
	// 测试map bool bool
	// @map 用户列表
	map<bool, bool> listBoolBool(1:map<bool, bool> map);
	// 测试map bool byte
	// @map 用户列表
	map<bool, byte> listBoolByte(1:map<bool, byte> map);
	// 测试map bool I16
	// @map 用户列表
	map<bool, i16> listBoolI16(1:map<bool, i16> map);
	// 测试map bool I32
	// @map 用户列表
	map<bool, i32> listBoolI32(1:map<bool, i32> map);
	// 测试map bool I64
	// @map 用户列表
	map<bool, i64> listBoolI64(1:map<bool, i64> map);
	// 测试map bool double
	// @map 用户列表
	map<bool, double> listBoolDouble(1:map<bool, double> map);
	// 测试map bool string
	// @map 用户列表
	map<bool, String> listBoolString(1:map<bool, String> map);
	
	// 测试map byte bool
	// @map 用户列表
	map<byte, bool> listByteBool(1:map<byte, bool> map);
	// 测试map byte byte
	// @map 用户列表
	map<byte, byte> listByteByte(1:map<byte, byte> map);
	// 测试map byte I16
	// @map 用户列表
	map<byte, i16> listByteI16(1:map<byte, i16> map);
	// 测试map byte I32
	// @map 用户列表
	map<byte, i32> listByteI32(1:map<byte, i32> map);
	// 测试map byte I64
	// @map 用户列表
	map<byte, i64> listByteI64(1:map<byte, i64> map);
	// 测试map byte double
	// @map 用户列表
	map<byte, double> listByteDouble(1:map<byte, double> map);
	// 测试map byte string
	// @map 用户列表
	map<byte, String> listByteString(1:map<byte, String> map);
	
	// 测试map i16 bool
	// @map 用户列表
	map<i16, bool> listI16Bool(1:map<i16, bool> map);
	// 测试map byte byte
	// @map 用户列表
	map<i16, byte> listI16Byte(1:map<i16, byte> map);
	// 测试map byte I16
	// @map 用户列表
	map<i16, i16> listI16I16(1:map<i16, i16> map);
	// 测试map byte I32
	// @map 用户列表
	map<i16, i32> listI16I32(1:map<i16, i32> map);
	// 测试map byte I64
	// @map 用户列表
	map<i16, i64> listI16I64(1:map<i16, i64> map);
	// 测试map byte double
	// @map 用户列表
	map<i16, double> listI16Double(1:map<i16, double> map);
	// 测试map byte string
	// @map 用户列表
	map<i16, String> listI16String(1:map<i16, String> map);
	
	// 测试map i32 bool
	// @map 用户列表
	map<i32, bool> listI32Bool(1:map<i32, bool> map);
	// 测试map byte byte
	// @map 用户列表
	map<i32, byte> listI32Byte(1:map<i32, byte> map);
	// 测试map byte I16
	// @map 用户列表
	map<i32, i16> listI32I16(1:map<i32, i16> map);
	// 测试map byte I32
	// @map 用户列表
	map<i32, i32> listI32I32(1:map<i32, i32> map);
	// 测试map byte I64
	// @map 用户列表
	map<i32, i64> listI32I64(1:map<i32, i64> map);
	// 测试map byte double
	// @map 用户列表
	map<i32, double> listI32Double(1:map<i32, double> map);
	// 测试map byte string
	// @map 用户列表
	map<i32, String> listI32String(1:map<i32, String> map);
	
	// 测试map i64 bool
	// @map 用户列表
	map<i64, bool> listI64Bool(1:map<i64, bool> map);
	// 测试map i64 byte
	// @map 用户列表
	map<i64, byte> listI64Byte(1:map<i64, byte> map);
	// 测试map i64 I16
	// @map 用户列表
	map<i64, i16> listI64I16(1:map<i64, i16> map);
	// 测试map i64 I32
	// @map 用户列表
	map<i64, i32> listI64I32(1:map<i64, i32> map);
	// 测试map i64 I64
	// @map 用户列表
	map<i64, i64> listI64I64(1:map<i64, i64> map);
	// 测试map i64 double
	// @map 用户列表
	map<i64, double> listI64Double(1:map<i64, double> map);
	// 测试map i64 string
	// @map 用户列表
	map<i64, String> listI64String(1:map<i64, String> map);
	
	// 测试map double bool
	// @map 用户列表
	map<double, bool> listDoubleBool(1:map<double, bool> map);
	// 测试map double byte
	// @map 用户列表
	map<double, byte> listDoubleByte(1:map<double, byte> map);
	// 测试map double I16
	// @map 用户列表
	map<double, i16> listDoubleI16(1:map<double, i16> map);
	// 测试map double I32
	// @map 用户列表
	map<double, i32> listDoubleI32(1:map<double, i32> map);
	// 测试map double I64
	// @map 用户列表
	map<double, i64> listDoubleI64(1:map<double, i64> map);
	// 测试map double double
	// @map 用户列表
	map<double, double> listDoubleDouble(1:map<double, double> map);
	// 测试map double string
	// @map 用户列表
	map<double, String> listDoubleString(1:map<double, String> map);
	
	// 测试map string bool
	// @map 用户列表
	map<string, bool> listStringBool(1:map<string, bool> map);
	// 测试map string byte
	// @map 用户列表
	map<string, byte> listStringByte(1:map<string, byte> map);
	// 测试map string I16
	// @map 用户列表
	map<string, i16> listStringI16(1:map<string, i16> map);
	// 测试map string I32
	// @map 用户列表
	map<string, i32> listStringI32(1:map<string, i32> map);
	// 测试map string I64
	// @map 用户列表
	map<string, i64> listStringI64(1:map<string, i64> map);
	// 测试map string double
	// @map 用户列表
	map<string, double> listStringDouble(1:map<string, double> map);
	// 测试map string string
	// @map 用户列表
	map<string, String> listStringString(1:map<string, String> map);
}
