package com.zhaopin.plugin.template;

import com.zhaopin.plugin.Constants;
import com.zhaopin.plugin.common.ThriftService;
import com.zhaopin.plugin.common.ThriftServiceFunc;
import com.zhaopin.plugin.common.ThriftServiceFuncParam;
import com.zhaopin.plugin.common.ThriftStruct;
import com.zhaopin.plugin.common.ThriftStructField;
import com.zhaopin.plugin.util.ThriftType2JavaType;

public class ThriftBoolCodeTemplate extends AbstractThriftCodeTemplate {

	@Override
	public String genEncode(ThriftServiceFunc func, String space, ThriftService service) {
		StringBuffer buf = new StringBuffer();
		buf.append(formatWriteMessageBegin(space, func.getFuncName(), Reply));
		buf.append(formatWriteStructBegin(space, func.getFuncName() + "_args"));
		buf.append(formatWriteFieldBegin("success", Constants._TTYPE_BOOL, 0, space));
		buf.append(writeValue(space, "writeBool", "result"));
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
		buf.append(space).append(retType + " result" + " = " + "iprot.readBool()" + ";").append("\r\n");
		buf.append(formatReadFieldEnd(space));
		buf.append(space + "return result;\r\n");
		return buf.toString();
	}

	@Override
	public String genEncode(ThriftServiceFuncParam param, String space, ThriftService service) {
		int index = param.getParamIndex() - 1;
		StringBuffer buf = new StringBuffer();
		buf.append(formatWriteFieldBegin(param.getParamName(), Constants._TTYPE_BOOL, param.getParamIndex(), space));
		buf.append(writeValue(space, "writeBool", "(Boolean) args[" + index + "]"));
		buf.append(formatWriteFieldEnd(space));
		return buf.toString();
	}

	@Override
	public String genDecode(ThriftServiceFuncParam param, String space, ThriftService service) {
		StringBuffer buf = new StringBuffer();
		buf.append(formatIfEqual(space, new String[] { "field.type", Constants._TTYPE_BOOL }));
		String formatReadBool = "#{0}args.#{1} = iprot.readBool();\r\n";
		String fieldName = param.getParamName().trim();
		buf.append(formatReadBool.replace("#{0}", space + "\t").replace("#{1}", fieldName));
		buf.append(space + "} else {\r\n");
		buf.append(formatSkip(space + "\t"));
		buf.append(space + "}\r\n");
		return buf.toString();
	}

	@Override
	public String genEncode(ThriftStructField field, String space, ThriftStruct struct) {
		String fieldName = field.getFieldName().trim();
		String func = getFuncName("is", fieldName);
		StringBuffer buf = new StringBuffer();
		buf.append(formatWriteFieldBegin(fieldName, Constants._TTYPE_BOOL, field.getFieldIndex(), space));
		buf.append(writeValue(space, "writeBool", "this.obj." + func + "()"));
		buf.append(formatWriteFieldEnd(space));
		return buf.toString();
	}

	@Override
	public String genDecode(ThriftStructField field, String space, ThriftStruct struct) {
		StringBuffer buf = new StringBuffer();
		buf.append(formatIfEqual(space, new String[] { "field.type", Constants._TTYPE_BOOL }));
		String fieldName = field.getFieldName().trim();
		String func = getFuncName("set", fieldName);
		buf.append(space + "\t").append("obj." + func + "(iprot.readBool());\r\n");
		buf.append(space + "} else {\r\n");
		buf.append(formatSkip(space + "\t"));
		buf.append(space + "}\r\n");
		return buf.toString();
	}
}
