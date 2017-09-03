package com.zhaopin.plugin.template;

import com.zhaopin.plugin.Constants;
import com.zhaopin.plugin.common.ThriftService;
import com.zhaopin.plugin.common.ThriftServiceFunc;
import com.zhaopin.plugin.common.ThriftServiceFuncParam;
import com.zhaopin.plugin.common.ThriftStruct;
import com.zhaopin.plugin.common.ThriftStructField;
import com.zhaopin.plugin.util.ThriftTypeUtil;

public class ThriftDtoTemplate extends AbstractThriftCodeTemplate {

	@Override
	public String genEncode(ThriftServiceFunc func, String space, ThriftService service) {
		StringBuffer buf = new StringBuffer();
		buf.append(formatWriteMessageBegin(space, func.getFuncName(), Reply));
		buf.append(formatWriteStructBegin(space, func.getFuncName() + "_args"));
		buf.append(formatIfNotEqual(space, new String[] { "result", "null" }));
		buf.append(formatWriteFieldBegin("success", Constants._TTYPE_STRUCT, "0", space + "\t"));
		String thriftImport = ThriftTypeUtil.getFuncReturnClassPath(func, service);
		buf.append(formatDtoEncode(space + "\t", ThriftTypeUtil.getDtoCodecPath(thriftImport)));
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
		String thriftImport = ThriftTypeUtil.getFuncReturnClassPath(func, service);
		buf.append(formatDtoDecode(space, func.getRetType(), ThriftTypeUtil.getDtoCodecPath(thriftImport)));
		buf.append(formatReadFieldEnd(space));
		buf.append(space + "return result;" + "\r\n");
		return buf.toString();
	}

	@Override
	public String genEncode(ThriftServiceFuncParam param, String space, ThriftService service) {
		int paramIndex = param.getParamIndex() - 1;
		StringBuffer buf = new StringBuffer();
		String thriftImport = ThriftTypeUtil.getFuncParamClassPath(param, service);
		String codecCls = ThriftTypeUtil.getDtoCodecPath(thriftImport);
		buf.append(formatIfNotEqual(space, new String[] { "args[" + paramIndex + "]", "null" }));
		buf.append(formatWriteFieldBegin(param.getParamName(), Constants._TTYPE_STRUCT, param.getParamIndex(),
				space + "\t"));
		buf.append(formatDtoEncode(space + "\t", codecCls, param.getParamType(), "args[" + paramIndex + "]"));
		buf.append(formatWriteFieldEnd(space + "\t"));
		buf.append(formatIfEnd(space));
		return buf.toString();
	}

	@Override
	public String genDecode(ThriftServiceFuncParam param, String space, ThriftService service) {
		String thriftImport = ThriftTypeUtil.getFuncParamClassPath(param, service);
		String codecCls = ThriftTypeUtil.getDtoCodecPath(thriftImport);
		StringBuffer buf = new StringBuffer();
		buf.append(formatIfEqual(space, new String[] { "field.type", Constants._TTYPE_STRUCT }));
		buf.append(formatDtoDecode(space + "\t", param.getParamType(), codecCls, "dto"));
		String formatSetter = "#{0}args." + param.getParamName() + " = dto;\r\n";
		buf.append(formatSetter.replace("#{0}", space + "\t"));
		buf.append(formatElse(space));
		buf.append(formatSkip(space + "\t"));
		buf.append(formatIfEnd(space));
		return buf.toString();
	}

	@Override
	public String genEncode(ThriftStructField field, String space, ThriftStruct struct) {
		StringBuffer buf = new StringBuffer();
		String thriftImport = ThriftTypeUtil.getStructFieldClassPath(field, struct);
		String codecClass = ThriftTypeUtil.getDtoCodecPath(thriftImport);
		String param = "this.obj." + getFuncName("get", field.getFieldName()) + "()";
		String[] args = new String[] { "this.obj." + getFuncName("get", field.getFieldName()) + "()", "null" };
		buf.append(formatIfNotEqual(space, args));
		buf.append(formatWriteFieldBegin(field.getFieldName(), Constants._TTYPE_STRUCT, field.getFieldIndex(),
				space + "\t"));
		buf.append(formatDtoEncode(space, codecClass, param));
		buf.append(formatWriteFieldEnd(space + "\t"));
		buf.append(formatIfEnd(space));
		return buf.toString();
	}

	@Override
	public String genDecode(ThriftStructField field, String space, ThriftStruct struct) {
		StringBuffer buf = new StringBuffer();
		String thriftImport = ThriftTypeUtil.getStructFieldClassPath(field, struct);
		String codecClass = ThriftTypeUtil.getDtoCodecPath(thriftImport);
		buf.append(formatIfEqual(space, new String[] { "field.type", Constants._TTYPE_STRUCT }));
		buf.append(formatDtoDecode(space + "\t", field.getFieldType(), codecClass, "dto"));
		String formatSetter = "#{0}this.obj." + getFuncName("set", field.getFieldName()) + "(dto);\r\n";
		buf.append(formatSetter.replace("#{0}", space + "\t"));
		buf.append(formatElse(space));
		buf.append(formatSkip(space + "\t"));
		buf.append(formatIfEnd(space));
		return buf.toString();
	}
}
