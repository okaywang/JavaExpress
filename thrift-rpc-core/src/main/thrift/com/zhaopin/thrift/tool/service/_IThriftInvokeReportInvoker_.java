package com.zhaopin.thrift.tool.service;

import com.zhaopin.thrift.rpc.protocol.TProtocol;
import java.util.List;
import org.springframework.stereotype.Component;
import org.hibernate.validator.constraints.NotEmpty;
import com.zhaopin.thrift.rpc.codec.ThriftCodec;
import com.zhaopin.thrift.rpc.common.TStruct;
import com.zhaopin.thrift.rpc.common.TField;
import com.zhaopin.thrift.rpc.common.TType;
import javax.validation.constraints.NotNull;

public class _IThriftInvokeReportInvoker_ {
	public static class registerInvoker_invoker implements ThriftCodec<Boolean> {
		@Override
		public void encode(TProtocol oprot, Object[] args) {
			oprot.writeStructBegin(new TStruct("registerInvoker_args"));
			if (args[0] != null) {
				oprot.writeFieldBegin(new TField("invoker", TType.STRING, (short) 1));
				oprot.writeString((String) args[0]);
				oprot.writeFieldEnd();
			}
			if (args[1] != null) {
				@SuppressWarnings("unchecked")
				List<String> argList = (List<String>) args[1];
				oprot.writeFieldBegin(new TField("refs", TType.LIST, (short) 2));
				com.zhaopin.thrift.rpc.helper.ListHelper.writeString(oprot, argList);
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
