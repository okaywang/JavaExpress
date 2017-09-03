package com.zhaopin.thrift.rpc.util;

import java.io.File;
import java.nio.ByteBuffer;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.zhaopin.thrift.rpc.common.TMessage;
import com.zhaopin.thrift.rpc.protocol.ThriftProtocol;

import io.netty.buffer.ByteBuf;

public class RuntimeExceptionCodecTester {

	@Test
	public void test() throws Exception {
		byte[] msg = FileUtils.readFileToByteArray(new File("D:/test.err"));
		ByteBuf _buffer = io.netty.buffer.Unpooled.buffer(1024);
		_buffer.writeBytes(msg);
		ThriftProtocol iprot = new ThriftProtocol(_buffer);
		TMessage tmsg = iprot.readMessageBegin();
		System.out.println(tmsg);
		iprot.readStructBegin();
		System.out.println(_buffer.readableBytes());
		int size = iprot.readI32();
		System.out.println(size);
		ByteBuffer buffer = iprot.readBinary();
		byte[] binCode = new byte[buffer.remaining()];
		buffer.get(binCode);
		Object throwable = SerializeUtil.decode(binCode);
		iprot.readStructEnd();

		System.out.println(throwable);
	}

}
