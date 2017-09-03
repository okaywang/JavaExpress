package com.zhaopin.thrift.rpc.util;

import java.nio.ByteBuffer;

import com.zhaopin.common.exception.BaseException;
import com.zhaopin.common.exception.DaoException;
import com.zhaopin.common.exception.ServiceException;
import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.thrift.rpc.common.TStruct;
import com.zhaopin.thrift.rpc.protocol.TProtocol;

public class RuntimeExceptionCodec {

	public static Logger LOGGER = LoggerFactory.getLogger(RuntimeExceptionCodec.class);

	private static final TStruct TException = new TStruct("TApplicationException");

	public static void write(TProtocol oprot, Throwable cause) {
		oprot.writeStructBegin(TException);
		byte[] msg = SerializeUtil.encode(cause);
		// һ�������������,���ж��쳣����(���ֵ�������޷���hession���л�����)
		if (msg == null || msg.length == 0) {
			LOGGER.warn("encode exception failed", cause);
			// ���¹����쳣
			Throwable error = null;
			if (cause instanceof ServiceException) {
				error = new ServiceException(cause.getMessage(), ((ServiceException) cause).getCode());
			} else if (cause instanceof BaseException) {
				String code = ((BaseException) cause).getCode();
				error = new BaseException(cause.getMessage(), null, code, null);
			} else if (cause instanceof DaoException) {
				error = new DaoException(cause.getCause(), ((DaoException) cause).getCode());
			} else {
				// ��������������쳣������ִ�е�����
				error = new IllegalStateException(cause.getMessage(), cause.getCause());
			}
			msg = SerializeUtil.encode(error);
		}
		ByteBuffer byteBuf = ByteBuffer.wrap(msg);
		oprot.writeBinary(byteBuf);
		oprot.writeStructEnd();
	}

	public static Throwable read(TProtocol iprot) throws Exception {
		iprot.readStructBegin();
		ByteBuffer buffer = iprot.readBinary();
		byte[] binCode = new byte[buffer.remaining()];
		buffer.get(binCode);
		Object throwable = SerializeUtil.decode(binCode);
		iprot.readStructEnd();
		return (Throwable) throwable;
	}
}
