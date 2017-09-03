package com.zhaopin.plugin.template;

import com.zhaopin.plugin.Constants;
import com.zhaopin.plugin.common.SupportTypes;
import com.zhaopin.plugin.util.ThriftTypeResolve;
import com.zhaopin.thrift.rpc.common.TMessageType;

public abstract class AbstractThriftCodeTemplate implements ThriftCodeTemplate {
	
	public final String Reply = TMessageType.class.getName() + ".REPLY";

	protected String formatReadMapEnd(String space) {
		return space + "iprot.readMapEnd();\r\n";
	}

	protected String formatReadSetBegin(String space, String tlistName) {
		String formatReadSetBegin = Constants.TSet + " #{0} = iprot.readSetBegin();\r\n";
		return space + formatReadSetBegin.replace("#{0}", tlistName);
	}

	protected String formatReadMapBegin(String space, String tmapName) {
		String ReadMapBegin = Constants.TMap + " #{0} = iprot.readMapBegin();\r\n";
		return space + ReadMapBegin.replace("#{0}", tmapName);
	}

	protected String formatSizeGtZero(String space, String name) {
		String formatSizeGtZero = "#{0}if(#{1} >= 0) {\r\n";
		return formatSizeGtZero.replace("#{0}", space).replace("#{1}", name);
	}

	protected String formatReadListBegin(String space, String tlistName) {
		String ReadListBegin = Constants.TLIST + " #{0} = iprot.readListBegin();\r\n";
		return space + ReadListBegin.replace("#{0}", tlistName);
	}

	protected String formatReadFieldBegin(String space) {
		String formatReadFieldBegin = "#{0}iprot.readFieldBegin();\r\n";
		return formatReadFieldBegin.replace("#{0}", space);
	}

	protected String formatReadFieldBegin(String fieldName, String space) {
		String formatReadFieldBegin = "#{0}" + Constants._TFIELD + " #{1} = iprot.readFieldBegin();\r\n";
		return formatReadFieldBegin.replace("#{0}", space).replace("#{1}", fieldName);
	}

	protected String formatWriteListBegin(String space, String ttype, String size) {
		String WRITE_LIST_BEGIN = "#{0}oprot.writeListBegin(new " + Constants.TLIST + "(#{1}, #{2}));\r\n";
		return WRITE_LIST_BEGIN.replace("#{0}", space).replace("#{1}", ttype).replace("#{2}", size);
	}

	protected String formatIterator(String space, String itemType, String itemName, String param) {
		String FOR = "#{0}for (final #{1} #{2} : #{3}) {\r\n";
		return FOR.replace("#{0}", space).replace("#{1}", itemType).replace("#{2}", itemName).replace("#{3}", param);
	}

	protected String formatForEnd(String space) {
		String FOR_END = "#{0}}\r\n";
		return FOR_END.replace("#{0}", space);
	}

	protected String formatWriteMapEnd(String space) {
		String WRITE_MAP_END = "#{0}oprot.writeMapEnd();\r\n";
		return WRITE_MAP_END.replace("#{0}", space);
	}

	protected String formatWriteMapBegin(String space, String keyType, String valType, String size) {
		String WRITE_MAP_BEGIN = "#{0}oprot.writeMapBegin(new " + Constants.TMap + "(#{1}, #{2}, #{3}));\r\n";
		return WRITE_MAP_BEGIN.replace("#{0}", space).replace("#{1}", keyType).replace("#{2}", valType).replace("#{3}",
				size);
	}

	protected String formatMapIterator(String itType, String itName, String mapIt, String space) {
		String MAP_ITERATOR = "#{0}for(#{1} #{2} : #{3}) {\r\n";
		return MAP_ITERATOR.replace("#{0}", space).replace("#{1}", itType).replace("#{2}", itName).replace("#{3}",
				mapIt);
	}

	protected String formatWriteSetEnd(String space) {
		String WRITE_SET_END = "#{0}oprot.writeSetEnd();\r\n";
		return WRITE_SET_END.replace("#{0}", space);
	}

	protected String formatWriteListEnd(String space) {
		String WRITE_LIST_END = "#{0}oprot.writeListEnd();\r\n";
		return WRITE_LIST_END.replace("#{0}", space);
	}

	protected String formatWriteSetBegin(String space, String ttype, String size) {
		String WRITE_SET_BEGIN = "#{0}oprot.writeSetBegin(new " + Constants.TSet + "(#{1}, #{2}));\r\n";
		return WRITE_SET_BEGIN.replace("#{0}", space).replace("#{1}", ttype).replace("#{2}", size);
	}

	protected boolean isBasicType(String fieldType) {
		if (isNumberOrBoolType(fieldType)) {
			return true;
		}
		if (SupportTypes.STRING.equals(fieldType)) {
			return true;
		} else if (SupportTypes.DATE.equals(fieldType)) {
			return true;
		}
		return false;
	}

	protected boolean isNumberOrBoolType(String fieldType) {
		return ThriftTypeResolve.isNumberOrBoolType(fieldType);
	}

	protected String formatDtoDecode(String space, String type, String codecClass) {
		return formatDtoDecode(space, type, codecClass, "result");
	}

	protected String formatDtoDecode(String space, String type, String codecClass, String var) {
		String formatDtoDecode = "#{0}#{1} #{2} = new #{3}().decode(iprot);\r\n";
		return formatDtoDecode.replace("#{0}", space).replace("#{1}", type).replace("#{2}", var).replace("#{3}",
				codecClass);
	}

	protected String formatDtoEncode(String space, String codecClass) {
		return formatDtoEncode(space, codecClass, "result");
	}

	protected String formatDtoEncode(String space, String codecClass, String param) {
		String formatDtoEncode = "#{0}new #{1}(#{2}).encode(oprot);\r\n";
		return formatDtoEncode.replace("#{0}", space + "\t").replace("#{1}", codecClass).replace("#{2}", param);
	}

	protected String formatDtoEncode(String space, String codecClass, String type, String param) {
		String formatDtoEncode = "#{0}new #{1}((#{2}) #{3}).encode(oprot);\r\n";
		return formatDtoEncode.replace("#{0}", space).replace("#{1}", codecClass).replace("#{2}", type).replace("#{3}",
				param);
	}

	protected String formatWriteStructBegin(String space, String structName) {
		String formatWriteStructBegin = "#{0}oprot.writeStructBegin(new " + Constants._TStruct + "(\"#{1}\"));\r\n";
		return formatWriteStructBegin.replace("#{0}", space).replace("#{1}", structName);
	}

	protected String formatWriteMessageBegin(String space, String funcName, String type) {
		String formatWriteMessageBegin = "#{0}oprot.writeMessageBegin(new " + Constants._TMessage + "(msg, #{1}));\r\n";
		return formatWriteMessageBegin.replace("#{0}", space).replace("#{1}", type);
	}

	protected String formatIfEqual(String space, String[] args) {
		assert args != null && args.length == 2;
		String formatIfEqual = "#{0}if (#{1} == #{2}) {\r\n";
		return formatIfEqual.replace("#{0}", space).replace("#{1}", args[0]).replace("#{2}", args[1]);
	}

	protected String formatIfNotEqual(String space, String[] args) {
		assert args != null && args.length == 2;
		String formatIfNotEqual = "#{0}if (#{1} != #{2}) {\r\n";
		return formatIfNotEqual.replace("#{0}", space).replace("#{1}", args[0]).replace("#{2}", args[1]);
	}

	protected String formatElse(String space) {
		return "#{0}} else {\r\n".replace("#{0}", space);
	}

	protected String formatIfEnd(String space) {
		return "#{0}}\r\n".replace("#{0}", space);
	}

	protected String formatSkip(String space) {
		String formatSkip = "#{0}" + Constants._TProtocolUtil + ".skip(iprot, field.type);\r\n";
		return formatSkip.replace("#{0}", space);
	}

	protected String getFuncName(String prefix, String fieldName) {
		if (fieldName.startsWith("_")) {
			return prefix + fieldName;
		}
		if (fieldName.length() <= 1) {
			return prefix + Character.toUpperCase(fieldName.charAt(0));
		} else {
			char firstChar = fieldName.charAt(0);
			char secondChar = fieldName.charAt(1);
			if (Character.isUpperCase(firstChar) || Character.isUpperCase(secondChar)) {
				return prefix + fieldName;
			} else {
				return prefix + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
			}
		}
	}

	protected String formatWriteFieldBegin(String fieldName, String fieldType, String fieldIndex, String space) {
		String WRITE_FIELD_BEGIN = "#{0}oprot.writeFieldBegin(new " + Constants._TFIELD
				+ "(\"#{1}\", #{2}, (short) #{3}));\r\n";
		return WRITE_FIELD_BEGIN.replace("#{0}", space).replace("#{1}", fieldName).replace("#{2}", fieldType)
				.replace("#{3}", "" + fieldIndex);
	}

	protected String formatWriteFieldBegin(String fieldName, String fieldType, int fieldIndex, String space) {
		return formatWriteFieldBegin(fieldName, fieldType, String.valueOf(fieldIndex), space);
	}

	protected String formatWriteFieldEnd(String space) {
		String WRITE_FIELD_END = "#{0}oprot.writeFieldEnd();\r\n";
		return WRITE_FIELD_END.replace("#{0}", space);
	}

	protected String formatWriteFiledStop(String space) {
		return space + "oprot.writeFieldStop();\r\n";
	}

	protected String formatWriteStructEnd(String space) {
		return space + "oprot.writeStructEnd();\r\n";
	}

	protected String formatWriteMessageEnd(String space) {
		return space + "oprot.writeMessageEnd();\r\n";
	}

	protected String writeValue(String space, String func, String value) {
		String writeValue = "#{0}oprot.#{1}(#{2});\r\n";
		return writeValue.replace("#{0}", space).replace("#{1}", func).replace("#{2}", value);
	}

	protected String writeByte(String space, String value) {
		StringBuffer buf = new StringBuffer();
		buf.append(space).append("oprot.writeByte(" + value + ");").append("\r\n");
		return buf.toString();
	}

	protected String fromatReadStructBegin(String space) {
		return space + "iprot.readStructBegin();\r\n";
	}

	protected String formatReadFieldEnd(String space) {
		String formatReadFieldBegin = "#{0}iprot.readFieldEnd();\r\n";
		return formatReadFieldBegin.replace("#{0}", space);
	}
}
