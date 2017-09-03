package com.zhaopin.plugin.template;

import com.zhaopin.plugin.Constants;
import com.zhaopin.plugin.common.ThriftService;
import com.zhaopin.plugin.common.ThriftServiceFunc;
import com.zhaopin.plugin.common.ThriftServiceFuncParam;
import com.zhaopin.plugin.common.ThriftStruct;
import com.zhaopin.plugin.common.ThriftStructField;
import com.zhaopin.plugin.util.ThriftType2JavaType;

public class ThriftStringCodeTemplate extends AbstractThriftCodeTemplate {

	@Override
	public String genEncode(ThriftServiceFunc func, String space, ThriftService service) {
		StringBuffer buf = new StringBuffer();
		buf.append(formatWriteMessageBegin(space, func.getFuncName(), Reply));
		buf.append(formatWriteStructBegin(space, func.getFuncName() + "_args"));
		// if (result != null) {} {
		buf.append(formatIfNotEqual(space, new String[] { "result", "null" }));
		buf.append(formatWriteFieldBegin("success", Constants._TTYPE_STRING, "0", space + "\t"));
		buf.append(writeValue(space + "\t", "writeString", "result"));
		buf.append(formatWriteFieldEnd(space + "\t"));
		// }
		buf.append(this.formatIfEnd(space));
		buf.append(formatWriteFiledStop(space));
		buf.append(formatWriteStructEnd(space));
		buf.append(formatWriteMessageEnd(space));
		return buf.toString();
	}

	@Override
	public String genDecode(ThriftServiceFunc func, String space, ThriftService service) {
		StringBuffer buf = new StringBuffer();
		buf.append(fromatReadStructBegin(space));
		buf.append(formatReadFieldBegin("field", space));
		buf.append(formatIfEqual(space, new String[] { "field.type", "TType.STOP" }));
		buf.append(formatReadFieldEnd(space + "\t"));
		buf.append(space + "\t").append("return null;\r\n");
		buf.append(formatIfEnd(space));
		String retType = ThriftType2JavaType.convert(func.getRetType());
		buf.append(space).append(retType + " result" + " = " + "iprot.readString()" + ";").append("\r\n");
		buf.append(formatReadFieldEnd(space));
		buf.append(space).append("return result;\r\n");
		return buf.toString();
	}

	@Override
	public String genEncode(ThriftServiceFuncParam param, String space, ThriftService service) {
		int index = param.getParamIndex() - 1;
		StringBuffer buf = new StringBuffer();
		buf.append(this.formatIfNotEqual(space, new String[] { "args[" + index + "]", "null" }));
		buf.append(formatWriteFieldBegin(param.getParamName(), Constants._TTYPE_STRING, param.getParamIndex(),
				space + "\t"));
		buf.append(writeValue(space + "\t", "writeString", "(String) args[" + index + "]"));
		buf.append(formatWriteFieldEnd(space + "\t"));
		buf.append(this.formatIfEnd(space));
		return buf.toString();
	}

	@Override
	public String genDecode(ThriftServiceFuncParam param, String space, ThriftService service) {
		StringBuffer buf = new StringBuffer();
		buf.append(formatIfEqual(space, new String[] { "field.type", Constants._TTYPE_STRING }));
		String fieldName = param.getParamName().trim();
		buf.append(space + "\t").append("args." + fieldName + " = " + "iprot.readString()" + ";").append("\r\n");
		buf.append(space + "} else {").append("\r\n");
		buf.append(formatSkip(space + "\t"));
		buf.append(space + "}\r\n");
		return buf.toString();
	}

	@Override
	public String genEncode(ThriftStructField field, String space, ThriftStruct struct) {
		String fieldName = field.getFieldName().trim();
		String func = getFuncName("get", fieldName);
		StringBuffer buf = new StringBuffer();
		buf.append(this.formatIfNotEqual(space, new String[] { "this.obj." + func + "()", "null" }));
		buf.append(formatWriteFieldBegin(fieldName, Constants._TTYPE_STRING, field.getFieldIndex(), space + "\t"));
		buf.append(writeValue(space + "\t", "writeString", "this.obj." + func + "()"));
		buf.append(formatWriteFieldEnd(space + "\t"));
		buf.append(this.formatIfEnd(space));
		return buf.toString();
	}

	@Override
	public String genDecode(ThriftStructField field, String space, ThriftStruct struct) {
		StringBuffer buf = new StringBuffer();
		buf.append(formatIfEqual(space, new String[] { "field.type", Constants._TTYPE_STRING }));
		String fieldName = field.getFieldName().trim();
		String func = getFuncName("set", fieldName);
		buf.append(space + "\t").append("obj." + func + "(" + "iprot.readString()" + ");").append("\r\n");
		buf.append(space + "} else {").append("\r\n");
		buf.append(formatSkip(space + "\t"));
		buf.append(space + "}").append("\r\n");
		return buf.toString();
	}
}
