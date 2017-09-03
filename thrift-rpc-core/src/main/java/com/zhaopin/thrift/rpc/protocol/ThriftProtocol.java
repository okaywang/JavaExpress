package com.zhaopin.thrift.rpc.protocol;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

import com.zhaopin.thrift.rpc.common.TField;
import com.zhaopin.thrift.rpc.common.TList;
import com.zhaopin.thrift.rpc.common.TMap;
import com.zhaopin.thrift.rpc.common.TMessage;
import com.zhaopin.thrift.rpc.common.TSet;
import com.zhaopin.thrift.rpc.common.TStruct;
import com.zhaopin.thrift.rpc.common.TType;

import io.netty.buffer.ByteBuf;

public class ThriftProtocol extends TProtocol {

	private final ByteBuf buffer;

	public ThriftProtocol(ByteBuf buffer) {
		this.buffer = buffer;
	}

	public ByteBuf getBuffer() {
		return buffer;
	}

	@Override
	public void writeMessageBegin(TMessage message) {
		int version;
		if (message.getThriftVersion() == TProtocol.V4) {
			// 如果是v4版本
			version = VERSION_4 | message.getType();
		} else if (message.getThriftVersion() == TProtocol.V5) {
			// 如果是v5版本
			version = VERSION_5 | message.getType();
		} else {
			version = VERSION_1 | message.getType();
		}
		writeI32(version);
		writeString(message.getName());
		writeI32(message.getSeqid());
		if (message.getThriftVersion() == TProtocol.V4) {
			writeI64(message.getParentId());
			writeI64(message.getSpanId());
			writeByte(message.isSampled() ? (byte) 1 : (byte) 0);
		} else if (message.getThriftVersion() == TProtocol.V5) {
			writeI64(message.getParentId());
			writeI64(message.getSpanId());
			writeByte(message.isSampled() ? (byte) 1 : (byte) 0);
			encodeAttach(message.getAttachment());
		} else {
			writeString("");
		}
		writeString(message.getTraceId());
		// 写入灰度token
		String grayToken = message.getGrayToken();
		if (StringUtils.isEmpty(grayToken)) {
			grayToken = "";
		}
		writeString(grayToken);
	}

	private void encodeAttach(Map<String, String> attach) {
		writeMapBegin(new TMap(TType.STRING, TType.STRING, attach != null ? attach.size() : 0));
		for (Entry<String, String> entry : attach.entrySet()) {
			writeString(entry.getKey());
			writeString(entry.getValue());
		}
	}

	private Map<String, String> decodeAttach() {
		TMap map = readMapBegin();
		Map<String, String> attach = new HashMap<String, String>();
		for (int t = 0; t < map.getSize(); ++t) {
			String key = readString();
			String value = readString();
			attach.put(key, value);
		}
		return attach;
	}

	@Override
	public void writeBool(boolean b) {
		writeByte(b ? (byte) 1 : (byte) 0);
	}

	@Override
	public void writeByte(byte b) {
		byte[] bout = new byte[] { b };
		this.buffer.writeBytes(bout);
	}

	@Override
	public void writeI16(short i16) {
		byte[] i16out = new byte[2];
		i16out[0] = (byte) (0xff & (i16 >> 8));
		i16out[1] = (byte) (0xff & (i16));
		this.buffer.writeBytes(i16out);
	}

	@Override
	public void writeI32(int i32) {
		byte[] i32out = new byte[4];
		i32out[0] = (byte) (0xff & (i32 >> 24));
		i32out[1] = (byte) (0xff & (i32 >> 16));
		i32out[2] = (byte) (0xff & (i32 >> 8));
		i32out[3] = (byte) (0xff & (i32));
		this.buffer.writeBytes(i32out);
	}

	@Override
	public void writeI64(long i64) {
		byte[] i64out = new byte[8];
		i64out[0] = (byte) (0xff & (i64 >> 56));
		i64out[1] = (byte) (0xff & (i64 >> 48));
		i64out[2] = (byte) (0xff & (i64 >> 40));
		i64out[3] = (byte) (0xff & (i64 >> 32));
		i64out[4] = (byte) (0xff & (i64 >> 24));
		i64out[5] = (byte) (0xff & (i64 >> 16));
		i64out[6] = (byte) (0xff & (i64 >> 8));
		i64out[7] = (byte) (0xff & (i64));
		this.buffer.writeBytes(i64out);
	}

	@Override
	public void writeDouble(double dub) {
		writeI64(Double.doubleToLongBits(dub));
	}

	@Override
	public void writeString(String str) {
		if (str != null) {
			try {
				byte[] dat = str.getBytes("utf-8");
				writeI32(dat.length);
				this.buffer.writeBytes(dat, 0, dat.length);
			} catch (UnsupportedEncodingException cause) {
				throw new IllegalStateException("jvm do not support utf-8", cause);
			}
		} else {
			writeI32(-1);
		}
	}

	@Override
	public void writeDate(Date date) {
		long time = date == null ? -1L : date.getTime();
		writeI64(time);
	}

	@Override
	public void writeListBegin(TList list) {
		writeByte(list.getElemType());
		writeI32(list.getSize());
	}

	@Override
	public void writeListEnd() {
		// 空函数
	}

	@Override
	public void writeStructBegin(TStruct struct) {
		// 空函数
	}

	@Override
	public void writeStructEnd() {
		// 空函数
	}

	@Override
	public void writeSetBegin(TSet set) {
		writeByte(set.getElemType());
		writeI32(set.getSize());
	}

	@Override
	public void writeSetEnd() {
		// 空函数
	}

	@Override
	public void writeMapBegin(TMap map) {
		writeByte(map.getKeyType());
		writeByte(map.getValueType());
		writeI32(map.getSize());
	}

	@Override
	public void writeMapEnd() {
		// 空函数
	}

	@Override
	public void writeBinary(ByteBuffer buf) {
		int length = buf.limit() - buf.position();
		writeI32(length);
		this.buffer.writeBytes(buf.array(), buf.position() + buf.arrayOffset(), length);

	}

	@Override
	public void writeFieldBegin(TField field) {
		writeByte(field.type);
		writeI16(field.id);

	}

	@Override
	public void writeFieldStop() {
		writeByte(TType.STOP);
	}

	@Override
	public void writeFieldEnd() {
		// 空函数
	}

	@Override
	public void writeMessageEnd() {
		// 空函数
	}

	@Override
	public TMessage readMessageBegin() {
		int type = readI32();
		int version = type & VERSION_MASK;
		String name = readString();
		int seqid = readI32();
		// 添加level字段，用于表示调用的层级关系
		long parentId = 0, spanId = 0;
		// 确定调用方的版本, 如果是thrift 4.x版本
		boolean fromNodeJs = false;
		boolean sampled = false;
		Map<String, String> attach = new HashMap<String, String>();
		if (version == VERSION_4) {
			parentId = readI64();
			spanId = readI64();
			sampled = this.readByte() == 1 ? true : false;
		} else if (version == VERSION_5) {
			parentId = readI64();
			spanId = readI64();
			sampled = this.readByte() == 1 ? true : false;
			attach = this.decodeAttach();
		} else {
			// 旧版本
			String tag = readString();
			if (!StringUtils.isEmpty(tag) && tag.startsWith("node")) {
				fromNodeJs = true;
			}
		}
		String traceID = readString();
		// 这里是为了留下一个可扩展的字段
		String grayToken = readString();
		TMessage tmsg = new TMessage(name, (byte) (type & 0x000000ff), seqid, traceID, grayToken, parentId, spanId);
		tmsg.setSampled(sampled);
		if (version == VERSION_4) {
			tmsg.setThriftVersion(V4);
		} else if (version == VERSION_5) {
			tmsg.setThriftVersion(V5);
		}
		if (fromNodeJs) {
			tmsg.setThriftVersion(NODEJS);
		}
		tmsg.setAttachment(attach);
		return tmsg;
	}

	@Override
	public boolean readBool() {
		return readByte() == (byte) 1;
	}

	@Override
	public byte readByte() {
		return buffer.readByte();
	}

	@Override
	public short readI16() {
		byte[] buf = new byte[2];
		buffer.readBytes(buf);
		return (short) (((buf[0] & 0xff) << 8) | ((buf[1] & 0xff)));
	}

	@Override
	public int readI32() {
		byte[] buf = new byte[4];
		buffer.readBytes(buf);
		return ((buf[0] & 0xff) << 24) | ((buf[1] & 0xff) << 16) | ((buf[2] & 0xff) << 8) | ((buf[3] & 0xff));
	}

	@Override
	public long readI64() {
		byte[] buf = new byte[8];
		buffer.readBytes(buf);
		return ((long) (buf[0] & 0xff) << 56) | ((long) (buf[1] & 0xff) << 48) | ((long) (buf[2] & 0xff) << 40)
				| ((long) (buf[3] & 0xff) << 32) | ((long) (buf[4] & 0xff) << 24) | ((long) (buf[5] & 0xff) << 16)
				| ((long) (buf[6] & 0xff) << 8) | ((long) (buf[7] & 0xff));
	}

	@Override
	public double readDouble() {
		return Double.longBitsToDouble(readI64());
	}

	@Override
	public String readString() {
		int size = readI32();
		if (size < 0) {
			return null;
		}
		return readStringBody(size);
	}

	private String readStringBody(int size) {
		try {
			byte[] buf = new byte[size];
			this.buffer.readBytes(buf);
			return new String(buf, "UTF-8");
		} catch (UnsupportedEncodingException uex) {
			throw new IllegalStateException("jvm do not supprt utf-8 charset!");
		}
	}

	@Override
	public TField readFieldBegin() {
		byte type = readByte();
		short id = type == TType.STOP ? 0 : readI16();
		return new TField("", type, id);
	}

	@Override
	public void readMessageEnd() {
		// 空函数
	}

	@Override
	public TStruct readStructBegin() {
		return ANONYMOUS_STRUCT;
	}

	@Override
	public void readStructEnd() {
		// 空函数
	}

	@Override
	public void readSetEnd() {
		// 空函数
	}

	@Override
	public void readFieldEnd() {
		// 空函数
	}

	@Override
	public TList readListBegin() {
		return new TList(readByte(), readI32());
	}

	@Override
	public Date readDate() {
		long time = readI64();
		if (time < 0) {
			return null;
		}
		return new Date(time);
	}

	@Override
	public void readListEnd() {
		// 空函数
	}

	@Override
	public TSet readSetBegin() {
		return new TSet(readByte(), readI32());
	}

	@Override
	public TMap readMapBegin() {
		return new TMap(readByte(), readByte(), readI32());
	}

	@Override
	public void readMapEnd() {
		// 空函数
	}

	@Override
	public ByteBuffer readBinary() {
		int size = readI32();
		if (size < 0) {
			return null;
		}
		byte[] buf = new byte[size];
		buffer.readBytes(buf);
		return ByteBuffer.wrap(buf);
	}

}
