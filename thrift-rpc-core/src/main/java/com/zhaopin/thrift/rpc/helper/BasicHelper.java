package com.zhaopin.thrift.rpc.helper;

import java.util.Date;

import com.zhaopin.thrift.rpc.common.TField;
import com.zhaopin.thrift.rpc.common.TType;
import com.zhaopin.thrift.rpc.protocol.TProtocol;
import com.zhaopin.thrift.rpc.common.TStruct;

public class BasicHelper {

	public static void writeBool(TProtocol oprot, boolean b) {
		oprot.writeBool(b);
	}

	public static boolean readBool(TProtocol iprot) {
		return iprot.readBool();
	}

	public static void writeByte(TProtocol oprot, byte b) {
		oprot.writeByte(b);
	}

	public static byte readByte(TProtocol iprot) {
		return iprot.readByte();
	}

	public static void writeI16(TProtocol oprot, short i16) {
		oprot.writeI16(i16);
	}

	public static short readI16(TProtocol iprot) {
		return iprot.readI16();
	}

	public static void writeI32(TProtocol oprot, int i32) {
		oprot.writeI32(i32);
	}

	public static int readI32(TProtocol iprot) {
		return iprot.readI32();
	}

	public static void writeI64(TProtocol oprot, long i64) {
		oprot.writeI64(i64);
	}

	public static long readI64(TProtocol iprot) {
		return iprot.readI64();
	}

	public static void writeDouble(TProtocol oprot, double dub) {
		oprot.writeDouble(dub);
	}

	public static double readDouble(TProtocol iprot) {
		return iprot.readDouble();
	}

	public static void writeString(TProtocol oprot, String str) {
		oprot.writeString(str);
	}

	public static String readString(TProtocol iprot) {
		return iprot.readString();
	}

	public static void writeNullObject(TProtocol oprot) {
		oprot.writeStructBegin(new TStruct());
		oprot.writeFieldBegin(new TField("", TType.VOID, (short) -1));
		oprot.writeFieldEnd();
		oprot.writeFieldStop();
		oprot.writeStructEnd();
	}
	
	public static Date readDate(TProtocol iprot) {
		return iprot.readDate();
	}
	
	public static void writeDate(TProtocol oprot, Date date) {
		oprot.writeDate(date);
	}
}
