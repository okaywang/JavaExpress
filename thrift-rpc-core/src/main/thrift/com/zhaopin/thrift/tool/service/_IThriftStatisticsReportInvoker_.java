package com.zhaopin.thrift.tool.service;

import com.zhaopin.thrift.rpc.protocol.TProtocol;
import com.zhaopin.thrift.rpc.codec.ThriftCodec;
import com.zhaopin.thrift.rpc.common.TStruct;
import com.zhaopin.thrift.rpc.common.TField;
import com.zhaopin.thrift.rpc.common.TType;

public class _IThriftStatisticsReportInvoker_ {
	public static class report_invoker implements ThriftCodec<Boolean> {
		@Override
		public void encode(TProtocol oprot, Object[] args) {
			oprot.writeStructBegin(new TStruct("report_args"));
			oprot.writeFieldBegin(new TField("type", TType.I32, (short) 1));
			oprot.writeI32((Integer) args[0]);
			oprot.writeFieldEnd();
			if (args[1] != null) {
				oprot.writeFieldBegin(new TField("value", TType.STRING, (short) 2));
				oprot.writeString((String) args[1]);
				oprot.writeFieldEnd();
			}
			oprot.writeFieldStop();
			oprot.writeStructEnd();
		}
		@Override
		public Boolean decode(TProtocol iprot) {
			iprot.readStructBegin();
			iprot.readFieldBegin();
			boolean result = iprot.readBool();
			iprot.readFieldEnd();
			return result;
		}
	}
}
