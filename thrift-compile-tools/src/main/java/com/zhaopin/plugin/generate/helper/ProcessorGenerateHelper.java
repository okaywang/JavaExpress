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
import com.zhaopin.plugin.util.ThriftType2JavaType;
import com.zhaopin.plugin.util.ThriftTypeResolve;
import com.zhaopin.rpc.annotation.ThriftInterface;
import com.zhaopin.plugin.template.ThriftCodeTemplate;
import com.zhaopin.thrift.rpc.processor.AbstractThriftProcessor;

public class ProcessorGenerateHelper extends AbstractGenerateHelper {

	private static String ABSTRACT_PROCESSOR = AbstractThriftProcessor.class.getSimpleName();

	public ProcessorGenerateHelper(File srcDir) {
		super(srcDir);
	}

	public ThriftResult generate(ThriftService thriftService) {
		StringBuffer buf = new StringBuffer();
		ItemPair<String, String> pkgClsPair = getProcessor(thriftService);
		buf.append(formatPackage(pkgClsPair.getKey()));
		buf.append(appendImports(thriftService));
		buf.append(formatClassDefine(pkgClsPair.getValue(), ABSTRACT_PROCESSOR,
				new String[] { thriftService.getClassName() })).append(" {\r\n");
		buf.append(formatField("private final", thriftService.getClassName(), "processor"));
		buf.append(formatConstruct(pkgClsPair.getValue(), thriftService.getClassName()));
		buf.append(formatProcess(thriftService));
		for (ThriftServiceFunc func : thriftService.getServiceFuncs()) {
			buf.append(formatInvokerFunc(func));
			buf.append(formatArgsClass(func));
			buf.append(formatProcessorClass(func, thriftService));
		}
		buf.append("}\r\n");
		return new ThriftResult(pkgClsPair.getKey() + "." + pkgClsPair.getValue(), buf.toString());
	}

	/**
	 * 添加process接口的方法
	 * 
	 * @return
	 */
	private String formatProcess(ThriftService thriftService) {
		StringBuffer buf = new StringBuffer();
		String formatProcess = "\t@Override\r\n" + "\tpublic void process(" + Constants._TProtocol + " iprot, "
				+ Constants._TProtocol + " oprot) throws Throwable {\r\n";
		buf.append(formatProcess);
		buf.append("\t\t" + Constants._TMessage + " msg = iprot.readMessageBegin();\r\n");
		final String replyFunc = "\t\t\tnew #{0}_Processor().write(oprot, reply, msg);\r\n";
		boolean first = true;
		for (ThriftServiceFunc func : thriftService.getServiceFuncs()) {
			if (first) {
				first = false;
				String formatfuncComp = "\t\tif (\"#{0}\".equals(msg.getName())) {\r\n";
				buf.append(formatfuncComp.replace("#{0}", func.getFuncName()));
			} else {
				String formatfuncComp = " else if (\"#{0}\".equals(msg.getName())) {\r\n";
				buf.append(formatfuncComp.replace("#{0}", func.getFuncName()));
			}
			String formatDecode = "\t\t\t#{0}_args args = new #{0}_Processor().read(iprot);\r\n";
			buf.append(formatDecode.replace("#{0}", func.getFuncName()));
			String replyType = ThriftType2JavaType.convert(func.getRetType());
			String invokeFunc = "\t\t\t#{0} reply = #{1}(#{2});\r\n";
			buf.append(invokeFunc.replace("#{0}", replyType).replace("#{1}", func.getFuncName()).replace("#{2}",
					getParamVars(func)));
			buf.append("\t\t\tlogAfterInvoke(reply, msg);\r\n");
			//
			buf.append(replyFunc.replace("#{0}", func.getFuncName()));
			buf.append("\t\t}");
		}
		buf.append("\r\n");
		buf.append("\t}\r\n");
		return buf.toString();
	}

	private String formatFuncDecoder(ThriftServiceFunc func, String space, ThriftService thriftService) {
		StringBuffer buf = new StringBuffer();
		// 反序列化该类的字段
		String formatArgsInit = "#{0}#{1}_args args = new #{1}_args();\r\n";
		buf.append(formatArgsInit.replace("#{0}", space).replace("#{1}", func.getFuncName()));
		buf.append(space + "iprot.readStructBegin();\r\n");
		buf.append(space + "while (true) {" + "\r\n");
		buf.append(space + "\t" + Constants._TFIELD + " field = iprot.readFieldBegin();" + "\r\n");
		buf.append(space + "\t" + "if (field.type == " + Constants._TTYPE_STOP + ") {" + "\r\n");
		buf.append(space + "\t\t" + "break;" + "\r\n");
		buf.append(space + "\t" + "}" + "\r\n");
		buf.append(space + "\t" + "switch (field.id) {").append("\r\n");
		// 对每一个字段进行反序列化
		for (final ThriftServiceFuncParam field : func.getFuncParams()) {
			ThriftCodeTemplate template = ThriftTypeResolve.getThriftCodeTemplate(field.getParamType());
			buf.append(space + "\t").append("case " + field.getParamIndex() + ":").append("\r\n");
			buf.append(template.genDecode(field, space + "\t\t", thriftService));
			buf.append(space + "\t\t").append("break;\r\n");
		}
		buf.append(space + "\t").append("default:\r\n");
		buf.append(space + "\t\t").append(Constants._TProtocolUtil + ".skip(iprot, field.type);\r\n");
		buf.append(space + "\t" + "}").append("\r\n");
		String formatReadFieldEnd = "#{0}iprot.readFieldEnd();\r\n";
		buf.append(formatReadFieldEnd.replace("#{0}", space + "\t"));
		buf.append(space + "}" + "\r\n");
		buf.append(space + "return args;\r\n");
		return buf.toString();
	}

	protected String formatFuncEncoder(ThriftServiceFunc thriftFunc, String space, ThriftService thriftService) {
		ThriftCodeTemplate template = ThriftTypeResolve.getThriftCodeTemplate(thriftFunc.getRetType());
		return template.genEncode(thriftFunc, space, thriftService);
	}

	private String formatInvokerFunc(ThriftServiceFunc func) {
		StringBuffer buf = new StringBuffer();
		buf.append("\t@Override\r\n");
		String retType = ThriftType2JavaType.convert(func.getRetType());
		buf.append("\tpublic ").append(retType).append(" " + func.getFuncName() + "(").append(getArgVars(func))
				.append(")" + getExceptions(func) + " {\r\n");
		buf.append("\t\tObject[] args = new Object[] {").append(getArgVarsWithoutType(func)).append("};\r\n");
		String formatValidater = "\t\tvalidate(this.processor, \"#{0}\", args);\r\n";
		buf.append(formatValidater.replace("#{0}", func.getFuncName()));
		String formatInvoke = "\t\treturn this.processor.#{1}(#{2});\r\n";
		buf.append(formatInvoke.replace("#{1}", func.getFuncName()).replace("#{2}", getArgVarsWithoutType(func)));
		buf.append("\t}\r\n");
		return buf.toString();
	}

	private String formatArgsClass(ThriftServiceFunc func) {
		StringBuffer buf = new StringBuffer();
		String formatArgs = "\tprivate class #{0}_args {\r\n";
		buf.append(formatArgs.replace("#{0}", func.getFuncName()));
		String formatArg = "\t\tpublic #{0} #{1};\r\n";
		for (ThriftServiceFuncParam param : func.getFuncParams()) {
			String transParam = ThriftType2JavaType.convert(param.getParamType());
			buf.append(formatArg.replace("#{0}", transParam).replace("#{1}", param.getParamName()));
		}
		buf.append("\t}\r\n");
		return buf.toString();
	}

	private String formatProcessorClass(ThriftServiceFunc func, ThriftService thriftService) {
		StringBuffer buf = new StringBuffer();
		// 生成参数的read方法
		String formatArgsSchemaCls = "\tprivate class #{0}_Processor {\r\n";
		buf.append(formatArgsSchemaCls.replace("#{0}", func.getFuncName()));
		String formatReadFunc = "\t\tpublic #{1}_args read(" + Constants._TProtocol + " iprot) {\r\n";
		buf.append(formatReadFunc.replace("#{1}", func.getFuncName()));
		buf.append(formatFuncDecoder(func, "\t\t\t", thriftService));
		buf.append("\t\t}\r\n");
		// 生成参数write方法
		String formatWriteFunc = "\t\tpublic void write(" + Constants._TProtocol + " oprot, #{1} result, "
				+ Constants._TMessage + " msg) {\r\n";
		String retType = ThriftType2JavaType.convert(func.getRetType());
		buf.append(formatWriteFunc.replace("#{0}", "\t\t").replace("#{1}", retType));
		buf.append(formatFuncEncoder(func, "\t\t\t", thriftService));
		buf.append("\t\t}\r\n");
		buf.append("\t}\r\n");
		return buf.toString();
	}

	private String formatConstruct(String processorName, String wraperSimpleName) {
		String formatInit = "\tpublic #{0}(#{1} processor) {\r\n" + "\t\tsuper(#{2}, processor);\r\n"
				+ "\t\tthis.processor = processor;\r\n" + "\t}\r\n";
		return formatInit.replace("#{0}", processorName).replace("#{1}", wraperSimpleName).replace("#{2}",
				wraperSimpleName + ".class");
	}

	private String getParamVars(ThriftServiceFunc func) {
		StringBuffer funcParamNames = new StringBuffer();
		boolean first = true;
		for (ThriftServiceFuncParam param : func.getFuncParams()) {
			if (first) {
				first = false;
				funcParamNames.append("args." + param.getParamName());
			} else {
				funcParamNames.append(", args." + param.getParamName());
			}
		}
		return funcParamNames.toString();
	}

	public String appendImports(ThriftService thriftService) {
		Set<String> imports = new HashSet<String>();
		imports.addAll(thriftService.getImports());
		imports.add(Constants.TProtocol);
		imports.add(Constants.TMessage);
		imports.add(Constants.TMessageType);
		imports.add(Constants.TFIELD);
		imports.add(Constants.TTYPE);
		imports.add(Constants.TStruct);
		imports.add(Constants.TProtocolUtil);
		imports.add(getClassName(thriftService));
		imports.add(AbstractThriftProcessor.class.getName());
		imports.remove(ThriftInterface.class.getName());
		return formatImports(imports);
	}

	private String getArgVars(ThriftServiceFunc func) {
		StringBuffer buf = new StringBuffer();
		boolean first = true;
		for (ThriftServiceFuncParam param : func.getFuncParams()) {
			String paramType = ThriftType2JavaType.convert(param.getParamType());
			if (first) {
				first = false;
				buf.append(paramType).append(" ").append(param.getParamName());
			} else {
				buf.append(", " + paramType).append(" ").append(param.getParamName());
			}
		}
		return buf.toString();
	}

	private String getArgVarsWithoutType(ThriftServiceFunc func) {
		StringBuffer buf = new StringBuffer();
		boolean first = true;
		for (ThriftServiceFuncParam param : func.getFuncParams()) {
			if (first) {
				first = false;
				buf.append(param.getParamName());
			} else {
				buf.append(", ").append(param.getParamName());
			}
		}
		return buf.toString();
	}

	protected String getExceptions(ThriftServiceFunc func) {
		StringBuffer buf = new StringBuffer();
		boolean first = true;
		for (String exception : func.getExceptions()) {
			if (first) {
				first = false;
				buf.append(" throws " + exception);
			} else {
				buf.append(", " + exception);
			}
		}
		return buf.toString();
	}

}
