package com.zhaopin.plugin.generate.helper;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zhaopin.plugin.Constants;
import com.zhaopin.plugin.common.ItemPair;
import com.zhaopin.plugin.common.ThriftResult;
import com.zhaopin.plugin.common.ThriftStruct;
import com.zhaopin.plugin.common.ThriftStructField;
import com.zhaopin.plugin.template.ThriftCodeTemplate;
import com.zhaopin.plugin.util.StringFinder;
import com.zhaopin.plugin.util.ThriftTypeResolve;
import com.zhaopin.rpc.annotation.ThriftField;
import com.zhaopin.thrift.rpc.checker.IStructFields;

public class StructCodecGenerateHelper extends AbstractGenerateHelper {

	public StructCodecGenerateHelper(File srcDir) {
		super(srcDir);
	}

	public ThriftResult generate(ThriftStruct thriftStruct) {
		StringBuffer buf = new StringBuffer();
		ItemPair<String, String> pkgClsPair = getCodec(thriftStruct);
		buf.append(formatPackage(pkgClsPair.getKey()));
		String superCodec = getSuperCodec(thriftStruct);
		StringBuffer classDefineBuf = new StringBuffer();
		classDefineBuf.append(
				formatClassDefine(pkgClsPair.getValue(), null, new String[] { IStructFields.class.getSimpleName() }))
				.append(" {\r\n");
		classDefineBuf.append(formatField("protected", thriftStruct.getClassName(), "obj"));
		classDefineBuf.append(formatConstruct(thriftStruct, pkgClsPair.getValue()));
		classDefineBuf.append(formatEncoder(thriftStruct));
		classDefineBuf.append(formatDecoder(thriftStruct));
		classDefineBuf.append(formatChecker(thriftStruct));
		classDefineBuf.append("}\r\n");
		// 后面有对import的操作
		buf.append(appendImports(thriftStruct, superCodec));
		buf.append(classDefineBuf);
		return new ThriftResult(pkgClsPair.getKey() + "." + pkgClsPair.getValue(), buf.toString());
	}

	private String getSuperCodec(ThriftStruct thriftStruct) {
		if (thriftStruct.getSuperName() == null) {
			return null;
		}
		// 是否是同一个包下的dto
		String targetImport = null;
		for (String importClass : thriftStruct.getImports()) {
			if (importClass.endsWith("." + thriftStruct.getSuperName())) {
				targetImport = importClass;
				break;
			}
		}
		if (targetImport == null) {
			return null;
		}
		String codecClass = null;
		int index = StringFinder.findIndex(targetImport, ".", 2);
		if (index < 0) {
			codecClass = Constants.CODEC_PREFIX + "." + thriftStruct.getSuperName() + Constants.CODEC_CLS_SUFFIX;
		} else {
			codecClass = Constants.CODEC_PREFIX + "." + targetImport.substring(index + 1) + Constants.CODEC_CLS_SUFFIX;
		}
		return codecClass;
	}

	private String formatConstruct(ThriftStruct thriftStruct, String codecClass) {
		StringBuffer buf = new StringBuffer();
		String formatConstructBegin[] = new String[] { "#{0}public #{1}() {\r\n", "#{0}public #{1}(#{2} obj) {\r\n" };
		// 添加没有参数的构造函数
		buf.append(formatConstructBegin[0].replace("#{0}", "\t").replace("#{1}", codecClass));
		String formatConstructBody[] = new String[] { "#{0}this.obj = null;\r\n", "#{0}this.obj = obj;\r\n" };
		buf.append(formatConstructBody[0].replace("#{0}", "\t\t"));
		String formatConstructEnd = "#{0}}\r\n";
		buf.append(formatConstructEnd.replace("#{0}", "\t"));
		// 添加有参数构造函数
		buf.append(formatConstructBegin[1].replace("#{0}", "\t").replace("#{1}", codecClass).replace("#{2}",
				thriftStruct.getClassName()));
		if (thriftStruct.getSuperName() != null) {
			String formatConstructSuper = "#{0}super(obj);\r\n";
			buf.append(formatConstructSuper.replace("#{0}", "\t\t"));
		}
		buf.append(formatConstructBody[1].replace("#{0}", "\t\t"));
		buf.append(formatConstructEnd.replace("#{0}", "\t"));
		return buf.toString();
	}

	private String formatEncoder(ThriftStruct thriftStruct) {
		String formatEncoder = "#{0}public void encode(" + Constants._TProtocol + " oprot) {\r\n ";
		StringBuffer buf = new StringBuffer();
		buf.append(formatEncoder.replace("#{0}", "\t"));
		String writeStructBegin = "#{0}oprot.writeStructBegin(new " + Constants._TStruct + "(\"#{1}\"));\r\n";
		buf.append(writeStructBegin.replace("#{0}", "\t\t").replace("#{1}", thriftStruct.getClassName()));
		// 依次对类属性的每一个字段进行序列化
		for (final ThriftStructField field : thriftStruct.getStructFields()) {
			String type = field.getFieldType();
			ThriftCodeTemplate template = ThriftTypeResolve.getThriftCodeTemplate(type);
			buf.append(template.genEncode(field, "\t\t", thriftStruct));
		}
		String writeFieldStop = "#{0}oprot.writeFieldStop();\r\n";
		buf.append(writeFieldStop.replace("#{0}", "\t\t"));
		String writeStructEnd = "#{0}oprot.writeStructEnd();\r\n";
		buf.append(writeStructEnd.replace("#{0}", "\t\t"));
		buf.append("\t").append("}").append("\r\n");
		return buf.toString();
	}

	protected String formatDecoder(ThriftStruct struct) {
		String decodeFormat = "#{0}public #{1} decode(" + Constants._TProtocol + " iprot) {\r\n";
		StringBuffer buf = new StringBuffer();
		buf.append(decodeFormat.replace("#{0}", "\t").replace("#{1}", struct.getClassName()));
		String Initilize = "#{1} obj = new #{1}();\r\n";
		buf.append("\t\t").append(Initilize.replace("#{1}", struct.getClassName()));
		buf.append("\t\t").append("iprot.readStructBegin();").append("\r\n");
		// 反序列化该类的字段
		buf.append("\t\t" + "while (true) {" + "\r\n");
		buf.append("\t\t\t" + Constants._TFIELD + " field = iprot.readFieldBegin();" + "\r\n");
		buf.append("\t\t\t" + "if (field.type == " + Constants._TTYPE_STOP + ") {" + "\r\n");
		buf.append("\t\t\t\t" + "iprot.readStructBegin();" + "\r\n");
		buf.append("\t\t\t\t" + "break;" + "\r\n");
		buf.append("\t\t\t" + "}" + "\r\n");
		String formatIfVoid = "#{0}if(field.type == " + Constants._TTYPE_VOID + ") {\r\n";
		buf.append(formatIfVoid.replace("#{0}", "\t\t\t"));
		buf.append("\t\t\t\treturn null;\r\n");
		buf.append("\t\t\t}\r\n");
		buf.append("\t\t\t" + "switch (field.id) {").append("\r\n");
		// 对每一个字段进行反序列化
		for (final ThriftStructField field : struct.getStructFields()) {
			String type = field.getFieldType();
			ThriftCodeTemplate template = ThriftTypeResolve.getThriftCodeTemplate(type);
			buf.append("\t\t\t").append("case " + field.getFieldIndex() + ":").append("\r\n");
			buf.append(template.genDecode(field, "\t\t\t\t", struct));
			buf.append("\t\t\t\t").append("break;\r\n");
		}
		// 添加default分支
		buf.append("\t\t\t").append("default:").append("\r\n");
		buf.append("\t\t\t\t").append("TProtocolUtil.skip(iprot, field.type);\r\n");
		buf.append("\t\t\t" + "}").append("\r\n");
		String formatReadFieldEnd = "iprot.readFieldEnd();\r\n";
		buf.append("\t\t\t" + formatReadFieldEnd);
		buf.append("\t\t" + "}" + "\r\n");
		buf.append("\t\t").append("iprot.readStructEnd();").append("\r\n");
		buf.append("\t\t" + "return obj;" + "\r\n");
		buf.append("\t" + "}" + "\r\n");
		return buf.toString();
	}

	protected String formatChecker(ThriftStruct struct) {
		StringBuffer buf = new StringBuffer();
		String formatChecker = "#{0}@Override\r\n#{0}public Map<Integer, ThriftStructField> getFields() {\r\n";
		buf.append(formatChecker.replace("#{0}", "\t"));
		String formatMapInit = "#{0}Map<Integer, ThriftStructField> fields = new HashMap<Integer, ThriftStructField>();\r\n";
		buf.append(formatMapInit.replace("#{0}", "\t\t"));
		String formatItem = "#{0}fields.put(#{1}, new ThriftStructField(#{1}, \"#{2}\", \"#{3}\"));\r\n";
		for (ThriftStructField thriftField : struct.getStructFields()) {
			buf.append(formatItem.replace("#{0}", "\t\t").replace("#{1}", "" + thriftField.getFieldIndex())
					.replace("#{2}", thriftField.getFieldType()).replace("#{3}", thriftField.getFieldName()));
		}
		buf.append("\t\treturn fields;\r\n");
		buf.append("\t}\r\n");
		return buf.toString();
	}

	private String appendImports(ThriftStruct thriftStruct, String superCodec) {
		Set<String> imports = new HashSet<String>();
		imports.addAll(thriftStruct.getImports());
		if (thriftStruct.getSuperName() != null && superCodec != null) {
			imports.add(superCodec);
		}
		imports.add(getClassName(thriftStruct));
		imports.remove(Serializable.class.getName());
		imports.add(IStructFields.class.getName());
		imports.add(Constants.TProtocol);
		imports.add(Constants.TFIELD);
		imports.add(Constants.TStruct);
		imports.add(Constants.TTYPE);
		imports.add(Constants.TProtocolUtil);
		imports.add(Map.class.getName());
		imports.add(HashMap.class.getName());
		imports.remove(com.zhaopin.rpc.annotation.ThriftStruct.class.getName());
		imports.remove(ThriftField.class.getName());
		imports.add(com.zhaopin.thrift.rpc.common.ThriftStructField.class.getName());
		return formatImports(imports);
	}
}
