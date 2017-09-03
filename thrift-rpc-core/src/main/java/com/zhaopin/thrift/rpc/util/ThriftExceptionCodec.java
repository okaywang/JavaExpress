package com.zhaopin.thrift.rpc.util;

import org.apache.thrift.TException;

import com.zhaopin.thrift.rpc.common.TField;
import com.zhaopin.thrift.rpc.common.TType;
import com.zhaopin.thrift.rpc.protocol.TProtocol;
import com.zhaopin.thrift.rpc.protocol.TProtocolUtil;

public class ThriftExceptionCodec {

	public static final int UNKNOWN = 0;

	public static TException read(TProtocol iprot) {
		TField field;
		iprot.readStructBegin();
		String message = null;
		int type = UNKNOWN;
		while (true) {
			field = iprot.readFieldBegin();
			if (field.type == TType.STOP) {
				break;
			}
			switch (field.id) {
			case 1:
				if (field.type == TType.STRING) {
					message = iprot.readString();
				} else {
					TProtocolUtil.skip(iprot, field.type);
				}
				break;
			case 2:
				if (field.type == TType.I32) {
					type = iprot.readI32();
				} else {
					TProtocolUtil.skip(iprot, field.type);
				}
				break;
			default:
				TProtocolUtil.skip(iprot, field.type);
				break;
			}
			iprot.readFieldEnd();
		}
		iprot.readStructEnd();
		return new TException("type:" + type + ", msg:" + message);
	}

}
