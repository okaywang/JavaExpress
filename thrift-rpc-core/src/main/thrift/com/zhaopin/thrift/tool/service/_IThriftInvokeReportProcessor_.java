package com.zhaopin.thrift.tool.service;

import com.zhaopin.thrift.rpc.processor.AbstractThriftProcessor;
import com.zhaopin.thrift.rpc.common.TStruct;
import com.zhaopin.thrift.rpc.common.TType;
import javax.validation.constraints.NotNull;
import com.zhaopin.thrift.rpc.protocol.TProtocolUtil;
import com.zhaopin.thrift.tool.service.IThriftInvokeReport;
import com.zhaopin.thrift.rpc.protocol.TProtocol;
import com.zhaopin.thrift.rpc.common.TMessageType;
import java.util.List;
import org.springframework.stereotype.Component;
import com.zhaopin.thrift.rpc.common.TMessage;
import org.hibernate.validator.constraints.NotEmpty;
import com.zhaopin.thrift.rpc.common.TField;

public class _IThriftInvokeReportProcessor_ extends AbstractThriftProcessor implements IThriftInvokeReport {
	private final IThriftInvokeReport processor;
	public _IThriftInvokeReportProcessor_(IThriftInvokeReport processor) {
		super(IThriftInvokeReport.class, processor);
		this.processor = processor;
	}
	@Override
	public void process(TProtocol iprot, TProtocol oprot) throws Throwable {
		TMessage msg = iprot.readMessageBegin();
		if ("registerInvoker".equals(msg.getName())) {
			registerInvoker_args args = new registerInvoker_Processor().read(iprot);
			boolean reply = registerInvoker(args.invoker, args.refs);
			logAfterInvoke(reply, msg);
			new registerInvoker_Processor().write(oprot, reply, msg);
		}
	}
	@Override
	public boolean registerInvoker(String invoker, List<String> refs) {
		Object[] args = new Object[] {invoker, refs};
		validate(this.processor, "registerInvoker", args);
		return this.processor.registerInvoker(invoker, refs);
	}
	private class registerInvoker_args {
		public String invoker;
		public List<String> refs;
	}
	private class registerInvoker_Processor {
		public registerInvoker_args read(TProtocol iprot) {
			registerInvoker_args args = new registerInvoker_args();
			iprot.readStructBegin();
			while (true) {
				TField field = iprot.readFieldBegin();
				if (field.type == TType.STOP) {
					break;
				}
				switch (field.id) {
				case 1:
					if (field.type == TType.STRING) {
						args.invoker = iprot.readString();
					} else {
						TProtocolUtil.skip(iprot, field.type);
					}
					break;
				case 2:
					if (field.type == TType.LIST) {
						List<String> list0 = com.zhaopin.thrift.rpc.helper.ListHelper.readString(iprot);
						args.refs = list0;
					} else {
						TProtocolUtil.skip(iprot, field.type);
					}
					break;
				default:
					TProtocolUtil.skip(iprot, field.type);
				}
				iprot.readFieldEnd();
			}
			return args;
		}
		public void write(TProtocol oprot, boolean result, TMessage msg) {
			oprot.writeMessageBegin(new TMessage(msg, com.zhaopin.thrift.rpc.common.TMessageType.REPLY));
			oprot.writeStructBegin(new TStruct("registerInvoker_args"));
			oprot.writeFieldBegin(new TField("success", TType.BOOL, (short) 0));
			oprot.writeBool(result);
			oprot.writeFieldEnd();
			oprot.writeFieldStop();
			oprot.writeStructEnd();
			oprot.writeMessageEnd();
		}
	}
}
