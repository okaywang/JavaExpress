package com.zhaopin.thrift.tool.service;

import com.zhaopin.thrift.rpc.protocol.TProtocol;
import com.zhaopin.thrift.rpc.common.TMessageType;
import com.zhaopin.thrift.rpc.common.TMessage;
import com.zhaopin.thrift.tool.service.IThriftStatisticsReport;
import com.zhaopin.thrift.rpc.processor.AbstractThriftProcessor;
import com.zhaopin.thrift.rpc.common.TField;
import com.zhaopin.thrift.rpc.common.TStruct;
import com.zhaopin.thrift.rpc.common.TType;
import com.zhaopin.thrift.rpc.protocol.TProtocolUtil;

public class _IThriftStatisticsReportProcessor_ extends AbstractThriftProcessor implements IThriftStatisticsReport {
	private final IThriftStatisticsReport processor;
	public _IThriftStatisticsReportProcessor_(IThriftStatisticsReport processor) {
		super(IThriftStatisticsReport.class, processor);
		this.processor = processor;
	}
	@Override
	public void process(TProtocol iprot, TProtocol oprot) throws Throwable {
		TMessage msg = iprot.readMessageBegin();
		if ("report".equals(msg.getName())) {
			report_args args = new report_Processor().read(iprot);
			boolean reply = report(args.type, args.value);
			logAfterInvoke(reply, msg);
			new report_Processor().write(oprot, reply, msg);
		}
	}
	@Override
	public boolean report(int type, String value) {
		Object[] args = new Object[] {type, value};
		validate(this.processor, "report", args);
		return this.processor.report(type, value);
	}
	private class report_args {
		public int type;
		public String value;
	}
	private class report_Processor {
		public report_args read(TProtocol iprot) {
			report_args args = new report_args();
			iprot.readStructBegin();
			while (true) {
				TField field = iprot.readFieldBegin();
				if (field.type == TType.STOP) {
					break;
				}
				switch (field.id) {
				case 1:
					if (field.type == TType.I32) {
						args.type = iprot.readI32();
					} else {
						TProtocolUtil.skip(iprot, field.type);
					}
					break;
				case 2:
					if (field.type == TType.STRING) {
						args.value = iprot.readString();
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
			oprot.writeStructBegin(new TStruct("report_args"));
			oprot.writeFieldBegin(new TField("success", TType.BOOL, (short) 0));
			oprot.writeBool(result);
			oprot.writeFieldEnd();
			oprot.writeFieldStop();
			oprot.writeStructEnd();
			oprot.writeMessageEnd();
		}
	}
}
