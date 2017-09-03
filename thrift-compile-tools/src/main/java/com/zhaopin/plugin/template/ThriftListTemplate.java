package com.zhaopin.plugin.template;

import com.zhaopin.plugin.Constants;
import com.zhaopin.plugin.common.ThriftService;
import com.zhaopin.plugin.common.ThriftServiceFunc;
import com.zhaopin.plugin.common.ThriftServiceFuncParam;
import com.zhaopin.plugin.common.ThriftStruct;
import com.zhaopin.plugin.common.ThriftStructField;
import com.zhaopin.plugin.util.ThriftType2JavaType;

public class ThriftListTemplate extends AbstractCollectionCodeTemplate {

	@Override
	public String genEncode(ThriftServiceFunc func, String space, ThriftService service) {
		StringBuffer buf = new StringBuffer();
		buf.append(formatWriteMessageBegin(space, func.getFuncName(), Reply));
		buf.append(formatWriteStructBegin(space, func.getFuncName() + "_args"));
		buf.append(formatIfNotEqual(space, new String[] { "result", "null" }));
		buf.append(formatWriteFieldBegin("success", Constants._TTYPE_LIST, "0", space + "\t"));
		buf.append(genericEncoderCode(func.getRetType(), space + "\t", "result", 0, service));
		buf.append(formatWriteFieldEnd(space + "\t"));
		buf.append(formatIfEnd(space));
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
		buf.append(formatIfEqual(space, new String[] { "field.type", Constants._TTYPE_STOP }));
		buf.append(space + "\t" + "return null;\r\n");
		buf.append(formatIfEnd(space));
		buf.append(genericDecodeCode(func.getRetType(), space, "result", 0, service));
		buf.append(formatReadFieldEnd(space));
		buf.append(space).append("return result;\r\n");
		return buf.toString();
	}

	@Override
	public String genEncode(ThriftServiceFuncParam param, String space, ThriftService service) {
		StringBuffer buf = new StringBuffer();
		int index = param.getParamIndex() - 1;
		buf.append(formatIfNotEqual(space, new String[] { "args[" + index + "]", "null" }));
		String paramType = ThriftType2JavaType.convert(param.getParamType());
		String formatCast = "#{0}@SuppressWarnings(\"unchecked\")\r\n#{0}#{1} argList = (#{1}) #{2};\r\n";
		buf.append(formatCast.replace("#{0}", space + "\t").replace("#{1}", paramType).replace("#{2}",
				"args[" + index + "]"));
		String paramName = param.getParamName();
		buf.append(formatWriteFieldBegin(paramName, Constants._TTYPE_LIST, param.getParamIndex(), space + "\t"));
		buf.append(genericEncoderCode(param.getParamType(), space + "\t", "argList", 0, service));
		buf.append(formatWriteFieldEnd(space + "\t"));
		buf.append(formatIfEnd(space));
		return buf.toString();
	}

	@Override
	public String genDecode(ThriftServiceFuncParam param, String space, ThriftService service) {
		StringBuffer buf = new StringBuffer();
		buf.append(formatIfEqual(space, new String[] { "field.type", Constants._TTYPE_LIST }));
		buf.append(genericDecodeCode(param.getParamType(), space + "\t", "list0", 0, service));
		buf.append(space + "\t" + "args." + param.getParamName() + " = list0;").append("\r\n");
		buf.append(formatElse(space));
		buf.append(formatSkip(space + "\t"));
		buf.append(formatIfEnd(space));
		return buf.toString();
	}

	@Override
	public String genEncode(ThriftStructField field, String space, ThriftStruct struct) {
		String func = getFuncName("get", field.getFieldName());
		StringBuffer buf = new StringBuffer();
		buf.append(formatIfNotEqual(space, new String[] { "this.obj." + func + "()", "null" }));
		buf.append(formatWriteFieldBegin(field.getFieldName(), Constants._TTYPE_LIST, field.getFieldIndex(), space + "\t"));
		buf.append(genericEncoderCode(field.getFieldType(), space + "\t", "this.obj." + func + "()", 0, struct));
		buf.append(formatWriteFieldEnd(space + "\t"));
		buf.append(formatIfEnd(space));
		return buf.toString();
	}

	@Override
	public String genDecode(ThriftStructField field, String space, ThriftStruct struct) {
		StringBuffer buf = new StringBuffer();
		buf.append(formatIfEqual(space, new String[] { "field.type", Constants._TTYPE_LIST }));
		buf.append(genericDecodeCode(field.getFieldType(), space + "\t", "list0", 0, struct));
		String func = getFuncName("set", field.getFieldName());
		buf.append(space + "\t" + "obj." + func + "(list0);").append("\r\n");
		buf.append(formatElse(space));
		buf.append(formatSkip(space + "\t"));
		buf.append(space + "}").append("\r\n");
		return buf.toString();
	}
}
