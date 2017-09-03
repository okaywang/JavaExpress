package com.zhaopin.plugin.generate.helper;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.zhaopin.plugin.common.ItemPair;
import com.zhaopin.plugin.common.ThriftResult;
import com.zhaopin.plugin.common.ThriftService;
import com.zhaopin.plugin.common.ThriftServiceFunc;
import com.zhaopin.plugin.common.ThriftServiceFuncParam;
import com.zhaopin.plugin.util.ThriftType2JavaType;

public class WraperGenerateHelper extends AbstractGenerateHelper {

	public WraperGenerateHelper(File srcDir) {
		super(srcDir);
	}

	public ThriftResult generate(ThriftService thriftService) {
		StringBuffer buf = new StringBuffer();
		ItemPair<String, String> pkgClsPair = getWraper(thriftService);
		buf.append(formatPackage(pkgClsPair.getKey()));
		Set<String> imports = new TreeSet<String>();
		StringBuffer clsDefBuf = new StringBuffer();
		clsDefBuf.append(formatClassDefine(pkgClsPair.getValue())).append(" {\r\n");
		clsDefBuf.append(appendConstruct(pkgClsPair.getValue(), thriftService.getClassName(), "\t"));
		String funcDefine = appendServiceFunc(thriftService, "\t", imports);
		if (funcDefine != null) {
			clsDefBuf.append(funcDefine);
		}
		clsDefBuf.append("}\r\n");
		buf.append(appendImports(thriftService, imports));
		buf.append(clsDefBuf);
		return new ThriftResult(pkgClsPair.getKey() + "." + pkgClsPair.getValue(), buf.toString());
	}

	public String appendImports(ThriftService thriftService, Set<String> implImports) {
		Set<String> imports = new HashSet<String>();
		imports.addAll(thriftService.getImports());
		imports.add(thriftService.getPkgName() + "." + thriftService.getClassName());
		imports.addAll(implImports);
		return formatImports(imports);
	}

	private String appendServiceFunc(ThriftService service, String space, Set<String> implImports) {
		StringBuffer buf = new StringBuffer();
		String implClass = service.getImplementClass();
		String filePath = getSrcPath() + File.separator + implClass.replace(".", File.separator) + ".java";
		File implFile = new File(filePath);
		try {
			List<?> lines = FileUtils.readLines(implFile, "utf-8");
			// 分析实现类的没一行
			Set<ThriftServiceFunc> funcs = new HashSet<ThriftServiceFunc>();
			funcs.addAll(service.getServiceFuncs());
			int t = 0;
			while (t < lines.size()) {
				String line = lines.get(t++).toString().trim();
				// 确认该行是否是实现类的方法开始定义, 如果该行以;结尾，说明是属性
				if (!line.startsWith("public") || line.endsWith(";") || !line.contains("(")) {
					continue;
				}
				StringBuffer funcDef = new StringBuffer();
				funcDef.append(line);
				if (!line.endsWith("{")) {
					while (t < lines.size()) {
						line = lines.get(t++).toString().trim();
						if (line.endsWith("{")) {
							funcDef.append(line.substring(0, line.length() - 1));
							break;
						} else {
							funcDef.append(line);
						}
					}
				}
				ThriftServiceFunc func = findMatchFunc(funcDef.toString(), funcs, service, implImports);
				if (func != null) {
					String funcDefine = funcDef.toString();
					if (funcDefine.contains("throws")) {
						funcDefine = funcDefine.substring(0, funcDefine.indexOf("throws"));
						funcDefine += "throws " + getThrowTable(func) + " {";
					}
					// 如果方法定义中存在
					buf.append("\t").append(funcDefine).append("\r\n");
					String invoker = "return this.processor." + func.getFuncName() + "(" + getParamListStr(func) + ");";
					buf.append("\t\t").append(invoker).append("\r\n");
					buf.append("\t}").append("\r\n");
				}
			}
		} catch (Exception exp) {
			throw new IllegalStateException(exp);
		}
		return buf.toString();
	}

	private String getThrowTable(ThriftServiceFunc func) {
		StringBuffer exceptionTables = new StringBuffer();
		List<String> exceptions = func.getExceptions();
		for (int t = 0, len = exceptions.size(); t < len; ++t) {
			if (t == 0) {
				exceptionTables.append("" + exceptions.get(t));
			} else {
				exceptionTables.append(" ," + exceptions.get(t));
			}
		}
		return exceptionTables.toString();
	}

	private String getParamListStr(ThriftServiceFunc func) {
		StringBuffer buf = new StringBuffer();
		for (ThriftServiceFuncParam param : func.getFuncParams()) {
			buf.append(param.getParamName()).append(",");
		}
		if (buf.length() > 0) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	private ThriftServiceFunc findMatchFunc(String implFunc, Set<ThriftServiceFunc> funcs, ThriftService service,
			Set<String> implImports) {
		Iterator<ThriftServiceFunc> it = funcs.iterator();
		while (it.hasNext()) {
			ThriftServiceFunc func = it.next();
			Pattern servicePat = Pattern.compile(func.getFuncName() + "[ \t]*\\(");
			Matcher matcher = servicePat.matcher(implFunc);
			if (!matcher.find()) {
				continue;
			}
			int end = matcher.end();
			String trimImplFunc = implFunc.substring(end);
			// 对参数类型进行判断
			boolean find = true;
			for (int t = 0, len = func.getFuncParams().size(); t < len; ++t) {
				ThriftServiceFuncParam param = func.getFuncParams().get(t);
				String javaType = ThriftType2JavaType.convert(param.getParamType());
				Pattern paramPat = null;
				Matcher paramMat = null;
				if (t == len - 1) {
					paramPat = Pattern.compile(javaType + "[ \t]+" + param.getParamName() + "\\)");
					paramMat = paramPat.matcher(trimImplFunc);
				} else {
					paramPat = Pattern.compile(javaType + "[ \t]+" + param.getParamName() + "[ \t]*,");
					paramMat = paramPat.matcher(trimImplFunc);
				}
				if (!paramMat.find()) {
					find = false;
					break;
				}
				int startIndex = paramMat.start();
				int endIndex = paramMat.end();
				// 注解部分提取
				String annotations = trimImplFunc.substring(0, startIndex);
				trimImplFunc = trimImplFunc.substring(endIndex);
				// 解析注解
				parseAnnotations(annotations, service, implImports);
			}
			if (find) {
				it.remove();
				return func;
			}
		}
		return null;
	}

	private void parseAnnotations(String annotations, ThriftService service, Set<String> implImports) {
		Pattern annoPat = null;
		Matcher annoMat = null;
		do {
			annoPat = Pattern.compile("@([a-zA-Z0-9_]+)[ \t]*");
			annoMat = annoPat.matcher(annotations);
			if (!annoMat.find()) {
				break;
			}
			String anno = annoMat.group(1);
			for (String thriftImport : service.getImplementImports()) {
				if (thriftImport.endsWith("." + anno)) {
					implImports.add(thriftImport);
					break;
				}
			}
			int end = annoMat.end();
			if (end + 1 >= annotations.length()) {
				break;
			}
			annotations = annotations.substring(end + 1);
		} while (true);
	}

	private String appendConstruct(String httpProcessor, String service, String space) {
		String formatProcessor = "#{0}private final #{2} processor;\r\n" + "#{0}public #{1}(#{2} processor) {\r\n"
				+ "#{0}\tthis.processor = processor;\r\n" + "#{0}}\r\n";
		return formatProcessor.replace("#{0}", space).replace("#{1}", httpProcessor).replace("#{2}", service);
	}
}
