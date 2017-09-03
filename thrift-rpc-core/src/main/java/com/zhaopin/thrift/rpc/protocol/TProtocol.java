package com.zhaopin.thrift.rpc.protocol;

import java.nio.ByteBuffer;
import java.util.Date;

import com.zhaopin.thrift.rpc.common.TField;
import com.zhaopin.thrift.rpc.common.TList;
import com.zhaopin.thrift.rpc.common.TMap;
import com.zhaopin.thrift.rpc.common.TMessage;
import com.zhaopin.thrift.rpc.common.TSet;
import com.zhaopin.thrift.rpc.common.TStruct;

public abstract class TProtocol {

	public static final TStruct ANONYMOUS_STRUCT = new TStruct();

	public static final int VERSION_MASK = 0xffff0000;

	public static final int VERSION_1 = 0x80010000;
	// nodejs°æ±¾
	public static final int NODEJS = 1;
	// thrift 4.x°æ±¾
	public static final int VERSION_4 = 0x80020000, V4 = 4;
	// thrift 5.x°æ±¾
	public static final int VERSION_5 = 0x80030000, V5 = 5;

	public abstract TMessage readMessageBegin();

	public abstract void writeMessageEnd();

	public abstract void readMessageEnd();

	public abstract void writeMessageBegin(TMessage message);

	public abstract TStruct readStructBegin();

	public abstract void writeStructBegin(TStruct struct);

	public abstract void writeFieldStop();

	public abstract void writeStructEnd();

	public abstract void readStructEnd();

	public abstract void writeBool(boolean b);

	public abstract void writeByte(byte b);

	public abstract void writeI16(short i16);

	public abstract void writeI32(int i32);

	public abstract void writeI64(long i64);

	public abstract void writeDouble(double dub);

	public abstract void writeString(String str);

	public abstract void writeDate(Date date);

	public abstract void writeFieldBegin(TField field);

	public abstract void writeFieldEnd();

	public abstract void writeListBegin(TList list);

	public abstract void writeListEnd();

	public abstract void writeSetBegin(TSet set);

	public abstract void writeSetEnd();

	public abstract void readSetEnd();

	public abstract void writeMapBegin(TMap map);

	public abstract void writeMapEnd();

	public abstract TField readFieldBegin();

	public abstract void readFieldEnd();

	public abstract boolean readBool();

	public abstract byte readByte();

	public abstract short readI16();

	public abstract int readI32();

	public abstract long readI64();

	public abstract double readDouble();

	public abstract String readString();

	public abstract Date readDate();

	public abstract TList readListBegin();

	public abstract void readListEnd();

	public abstract TSet readSetBegin();

	public abstract TMap readMapBegin();

	public abstract void readMapEnd();

	public abstract ByteBuffer readBinary();

	public abstract void writeBinary(ByteBuffer buf);
}
