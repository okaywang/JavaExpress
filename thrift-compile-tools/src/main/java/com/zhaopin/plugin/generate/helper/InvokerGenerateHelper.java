package com.zhaopin.plugin.generate.helper;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.zhaopin.plugin.Constants;
import com.zhaopin.plugin.common.ItemPair;
import com.zhaopin.plugin.common.ThriftResult;
import com.zhaopin.plugin.common.ThriftService;
import com.zhaopin.plugin.common.ThriftServiceFunc;
import com.zhaopin.plugin.common.ThriftServiceFuncParam;
import com.zhaopin.plugin.template.ThriftCodeTemplate;
import com.zhaopin.plugin.util.ThriftType2JavaType;
import com.zhaopin.plugin.util.ThriftTypeDetermine;
import com.zhaopin.plugin.util.ThriftTypeResolve;
import com.zhaopin.rpc.annotation.ThriftInterface;
import com.zhaopin.thrift.rpc.codec.ThriftCodec;

public class InvokerGenerateHelper extends AbstractGenerateHelper {

	public InvokerGenerateHelper(File srcDir) {
		super(srcDir);
	}

	public ThriftResult generate(ThriftService thriftService) {
		StringBuffer buf = new StringBuffer();
		ItemPair<String, String> pkgClsPair = getInvoker(thriftService);
		buf.append(formatPackage(pkgClsPair.getKey()));
		// 所有的import
		buf.append(appendImports(thriftService));
		// 类定义
		buf.append(formatClassDefine(pkgClsPair.getValue())).append(" {\r\n");
		// 处理每一个方法
		buf.append(appendServiceFunc(thriftService, "\t"));
		buf.append("}\r\n");
		return new ThriftResult(pkgClsPair.getKey() + "." + pkgClsPair.getValue(), buf.toString());
	}

	private String appendServiceFunc(ThriftService thriftService, String space) {
		StringBuffer buf = new StringBuffer();
		for (ThriftServiceFunc func : thriftService.getServiceFuncs()) {
			// 首先添加方法的调用
			String retType = ThriftType2JavaType.convert(func.getRetType());
			if (ThriftTypeDetermine.isNumberTypeMapping(func.getRetType().trim())) {
				retType = ThriftTypeDetermine.numberTypeMapping(func.getRetType().trim());
			}
			// 添加参数的序列化代码和返回结果的反序列化代码
			String[] interfaces = new String[] { Constants._ThriftCodec + "<" + retType + ">" };
			buf.append(space)
					.append(formatClassDefine("public static", func.getFuncName() + "_invoker", null, interfaces))
					.append(" {\r\n");
			// 添加encode方法
			String formatInvokerEncoder = "#{0}@Override\r\n#{0}public void encode(" + Constants._TProtocol
					+ " oprot, Object[] args) {\r\n";
			buf.append(formatInvokerEncoder.replace("#{0}", space + "\t").replace("#{1}", func.getFuncName()));
			buf.append(encode(func, space + "\t", thriftService));
			buf.append(space + "\t}\r\n");
			String formatDecoder = "#{0}@Override\r\n#{0}public #{1} decode(" + Constants._TProtocol + " iprot) {\r\n";
			buf.append(formatDecoder.replace("#{0}", space + "\t").replace("#{1}", retType));
			buf.append(decode(func, space + "\t", thriftService));
			buf.append(space + "\t}\r\n");
			buf.append(space + "}\r\n");
		}
		return buf.toString();
	}

	private String encode(ThriftServiceFunc func, String space, ThriftService thriftService) {
		StringBuffer encodeBuf = new StringBuffer();
		String writeStructBegin = "#{0}oprot.writeStructBegin(new " + Constants._TStruct + "(\"#{1}\"));\r\n";
		encodeBuf.append(writeStructBegin.replace("#{0}", space + "\t").replace("#{1}", func.getFuncName() + "_args"));
		// 对每一个字段进行反序列化
		for (final ThriftServiceFuncParam field : func.getFuncParams()) {
			String type = field.getParamType();
			ThriftCodeTemplate template = ThriftTypeResolve.getThriftCodeTemplate(type);
			encodeBuf.append(template.genEncode(field, space + "\t", thriftService));
		}
		encodeBuf.append(space + "\t" + "oprot.writeFieldStop();" + "\r\n");
		encodeBuf.append(space + "\t" + "oprot.writeStructEnd();" + "\r\n");
		return encodeBuf.toString();
	}

	private String decode(ThriftServiceFunc func, String space, ThriftService thriftService) {
		ThriftCodeTemplate template = ThriftTypeResolve.getThriftCodeTemplate(func.getRetType());
		return template.genDecode(func, space + "\t", thriftService);
	}

	public String appendImports(ThriftService thriftService) {
		Set<String> imports = new HashSet<String>();
		imports.addAll(thriftService.getImports());
		imports.add(Constants.TProtocol);
		imports.add(Constants.TStruct);
		imports.add(Constants.TFIELD);
		imports.add(Constants.TTYPE);
		imports.add(ThriftCodec.class.getName());
		imports.remove(ThriftInterface.class.getName());
		return formatImports(imports);
	}

}
