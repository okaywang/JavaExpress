package com.zhaopin.thrift.rpc.protocol;

import java.nio.ByteBuffer;
import java.util.Date;

import com.zhaopin.thrift.rpc.common.TField;
import com.zhaopin.thrift.rpc.common.TList;
import com.zhaopin.thrift.rpc.common.TMap;
import com.zhaopin.thrift.rpc.common.TMessage;
import com.zhaopin.thrift.rpc.common.TSet;
import com.zhaopin.thrift.rpc.common.TStruct;

public class ThriftProtocolWraper extends TProtocol {
	// 实际的protocol类
	private TProtocol protocol;
	// 消息头部
	private TMessage msg;

	public ThriftProtocolWraper(TProtocol protocol, TMessage msg) {
		this.protocol = protocol;
		// 只保留方法
		int index = msg.getName().indexOf(":");
		if (index < 0) {
			throw new IllegalArgumentException("\"" + msg.getName() + "\" is invalid!");
		}
		this.msg = new TMessage(msg.getName().substring(index + 1), msg.getType(), msg.getSeqid(), msg.getTraceId(),
				msg.getGrayToken(), msg.getParentId());
	}

	@Override
	public TMessage readMessageBegin() {
		return msg;
	}

	@Override
	public void writeMessageEnd() {
		protocol.writeMessageEnd();

	}

	@Override
	public void readMessageEnd() {
		protocol.readMessageEnd();
	}

	@Override
	public void writeMessageBegin(TMessage message) {
		protocol.writeMessageBegin(message);
	}

	@Override
	public TStruct readStructBegin() {
		return protocol.readStructBegin();
	}

	@Override
	public void writeStructBegin(TStruct struct) {
		protocol.writeStructBegin(struct);
	}

	@Override
	public void writeFieldStop() {
		protocol.writeFieldStop();
	}

	@Override
	public void writeStructEnd() {
		protocol.writeStructEnd();
	}

	@Override
	public void readStructEnd() {
		protocol.readStructEnd();
	}

	@Override
	public void writeBool(boolean b) {
		protocol.writeBool(b);
	}

	@Override
	public void writeByte(byte b) {
		protocol.writeByte(b);
	}

	@Override
	public void writeI16(short i16) {
		protocol.writeI16(i16);
	}

	@Override
	public void writeI32(int i32) {
		protocol.writeI32(i32);
	}

	@Override
	public void writeI64(long i64) {
		protocol.writeI64(i64);
	}

	@Override
	public void writeDouble(double dub) {
		protocol.writeDouble(dub);
	}

	@Override
	public void writeString(String str) {
		protocol.writeString(str);
	}

	@Override
	public void writeFieldBegin(TField field) {
		protocol.writeFieldBegin(field);
	}

	@Override
	public void writeFieldEnd() {
		protocol.writeFieldEnd();
	}

	@Override
	public void writeListBegin(TList list) {
		protocol.writeListBegin(list);
	}

	@Override
	public void writeListEnd() {
		protocol.writeListEnd();
	}

	@Override
	public void writeSetBegin(TSet set) {
		protocol.writeSetBegin(set);
	}

	@Override
	public void writeSetEnd() {
		protocol.writeSetEnd();
	}

	@Override
	public void readSetEnd() {
		protocol.readSetEnd();
	}

	@Override
	public void writeMapBegin(TMap map) {
		protocol.writeMapBegin(map);
	}

	@Override
	public void writeMapEnd() {
		protocol.writeMapEnd();
	}

	@Override
	public TField readFieldBegin() {
		return protocol.readFieldBegin();
	}

	@Override
	public void readFieldEnd() {
		protocol.readFieldEnd();
	}

	@Override
	public boolean readBool() {
		return protocol.readBool();
	}

	@Override
	public byte readByte() {
		return protocol.readByte();
	}

	@Override
	public short readI16() {
		return protocol.readI16();
	}

	@Override
	public int readI32() {
		return protocol.readI32();
	}

	@Override
	public long readI64() {
		return protocol.readI64();
	}

	@Override
	public double readDouble() {
		return protocol.readDouble();
	}

	@Override
	public String readString() {
		return protocol.readString();
	}

	@Override
	public TList readListBegin() {
		return protocol.readListBegin();
	}

	@Override
	public void readListEnd() {
		protocol.readListEnd();
	}

	@Override
	public TSet readSetBegin() {
		return protocol.readSetBegin();
	}

	@Override
	public TMap readMapBegin() {
		return protocol.readMapBegin();
	}

	@Override
	public void readMapEnd() {
		protocol.readMapEnd();
	}

	@Override
	public ByteBuffer readBinary() {
		return protocol.readBinary();
	}

	@Override
	public void writeBinary(ByteBuffer buf) {
		protocol.writeBinary(buf);
	}

	@Override
	public void writeDate(Date date) {
		protocol.writeDate(date);
	}

	@Override
	public Date readDate() {
		return protocol.readDate();
	}
}
