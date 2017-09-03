package com.zhaopin.plugin.template;

import com.zhaopin.plugin.Constants;
import com.zhaopin.plugin.common.ThriftService;
import com.zhaopin.plugin.common.ThriftServiceFunc;
import com.zhaopin.plugin.common.ThriftServiceFuncParam;
import com.zhaopin.plugin.common.ThriftStruct;
import com.zhaopin.plugin.common.ThriftStructField;
import com.zhaopin.plugin.util.ThriftType2JavaType;

public class ThriftI32CodeTemplate extends AbstractThriftCodeTemplate {

	@Override
	public String genEncode(ThriftServiceFunc func, String space, ThriftService service) {
		StringBuffer buf = new StringBuffer();
		buf.append(formatWriteMessageBegin(space, func.getFuncName(), Reply));
		buf.append(formatWriteStructBegin(space, func.getFuncName() + "_args"));
		buf.append(formatWriteFieldBegin("success", Constants._TTYPE_I32, "0", space));
		buf.append(writeValue(space, "writeI32", "result"));
		buf.append(formatWriteFieldEnd(space));
		buf.append(formatWriteFiledStop(space));
		buf.append(formatWriteStructEnd(space));
		buf.append(formatWriteMessageEnd(space));
		return buf.toString();
	}

	@Override
	public String genDecode(ThriftServiceFunc func, String space, ThriftService service) {
		StringBuffer buf = new StringBuffer();
		buf.append(fromatReadStructBegin(space));
		buf.append(formatReadFieldBegin(space));
		String retType = ThriftType2JavaType.convert(func.getRetType());
		buf.append(space).append(retType + " result" + " = " + "iprot.readI32()" + ";").append("\r\n");
		buf.append(formatReadFieldEnd(space));
		buf.append(space).append("return result;\r\n");
		return buf.toString();
	}

	@Override
	public String genEncode(ThriftServiceFuncParam param, String space, ThriftService service) {
		int index = param.getParamIndex() - 1;
		StringBuffer buf = new StringBuffer();
		buf.append(formatWriteFieldBegin(param.getParamName(), Constants._TTYPE_I32, param.getParamIndex(), space));
		buf.append(writeValue(space, "writeI32", "(Integer) args[" + index + "]"));
		buf.append(formatWriteFieldEnd(space));
		return buf.toString();
	}

	@Override
	public String genDecode(ThriftServiceFuncParam param, String space, ThriftService service) {
		StringBuffer buf = new StringBuffer();
		buf.append(formatIfEqual(space, new String[] { "field.type", Constants._TTYPE_I32 }));
		String fieldName = param.getParamName().trim();
		buf.append(space + "\t").append("args." + fieldName + " = " + "iprot.readI32()" + ";").append("\r\n");
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
		buf.append(formatWriteFieldBegin(fieldName, Constants._TTYPE_I32, field.getFieldIndex(), space));
		buf.append(writeValue(space, "writeI32", "this.obj." + func + "()"));
		buf.append(formatWriteFieldEnd(space));
		return buf.toString();
	}

	@Override
	public String genDecode(ThriftStructField field, String space, ThriftStruct struct) {
		StringBuffer buf = new StringBuffer();
		buf.append(formatIfEqual(space, new String[] { "field.type", Constants._TTYPE_I32 }));
		String fieldName = field.getFieldName().trim();
		String func = getFuncName("set", fieldName);
		buf.append(space + "\t").append("obj." + func + "(" + "iprot.readI32()" + ");").append("\r\n");
		buf.append(space + "} else {").append("\r\n");
		buf.append(formatSkip(space + "\t"));
		buf.append(space + "}").append("\r\n");
		return buf.toString();
	}
}
