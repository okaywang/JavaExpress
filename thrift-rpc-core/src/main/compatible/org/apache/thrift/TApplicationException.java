package org.apache.thrift;

import com.zhaopin.thrift.rpc.common.TField;
import com.zhaopin.thrift.rpc.common.TStruct;
import com.zhaopin.thrift.rpc.protocol.TProtocol;
import com.zhaopin.thrift.rpc.protocol.TProtocolUtil;

public class TApplicationException extends TException {
	private static final TStruct TAPPLICATION_EXCEPTION_STRUCT = new TStruct("TApplicationException");
	private static final TField MESSAGE_FIELD = new TField("message", (byte) 11, (short) 1);
	private static final TField TYPE_FIELD = new TField("type", (byte) 8, (short) 2);
	private static final long serialVersionUID = 1L;
	public static final int UNKNOWN = 0;
	public static final int UNKNOWN_METHOD = 1;
	public static final int INVALID_MESSAGE_TYPE = 2;
	public static final int WRONG_METHOD_NAME = 3;
	public static final int BAD_SEQUENCE_ID = 4;
	public static final int MISSING_RESULT = 5;
	public static final int INTERNAL_ERROR = 6;
	public static final int PROTOCOL_ERROR = 7;
	public static final int INVALID_TRANSFORM = 8;
	public static final int INVALID_PROTOCOL = 9;
	public static final int UNSUPPORTED_CLIENT_TYPE = 10;
	protected int type_ = 0;

	public TApplicationException() {
	}

	public TApplicationException(int type) {
		this.type_ = type;
	}

	public TApplicationException(int type, String message) {
		super(message);
		this.type_ = type;
	}

	public TApplicationException(String message) {
		super(message);
	}

	public int getType() {
		return this.type_;
	}

	public static TApplicationException read(TProtocol iprot) throws TException {
		iprot.readStructBegin();

		String message = null;
		int type = 0;
		while (true) {
			TField field = iprot.readFieldBegin();
			if (field.type == 0) {
				break;
			}
			switch (field.id) {
			case 1:
				if (field.type == 11)
					message = iprot.readString();
				else {
					TProtocolUtil.skip(iprot, field.type);
				}
				break;
			case 2:
				if (field.type == 8)
					type = iprot.readI32();
				else {
					TProtocolUtil.skip(iprot, field.type);
				}
				break;
			default:
				TProtocolUtil.skip(iprot, field.type);
			}

			iprot.readFieldEnd();
		}
		iprot.readStructEnd();

		return new TApplicationException(type, message);
	}

	public void write(TProtocol oprot) {
		oprot.writeStructBegin(TAPPLICATION_EXCEPTION_STRUCT);
		if (getMessage() != null) {
			oprot.writeFieldBegin(MESSAGE_FIELD);
			oprot.writeString(getMessage());
			oprot.writeFieldEnd();
		}
		oprot.writeFieldBegin(TYPE_FIELD);
		oprot.writeI32(this.type_);
		oprot.writeFieldEnd();
		oprot.writeFieldStop();
		oprot.writeStructEnd();
	}
}
