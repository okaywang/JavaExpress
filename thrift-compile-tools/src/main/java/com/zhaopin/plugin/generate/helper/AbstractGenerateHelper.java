package com.zhaopin.plugin.generate.helper;

import java.io.File;
import java.util.Set;

import com.zhaopin.plugin.Constants;
import com.zhaopin.plugin.common.ItemPair;
import com.zhaopin.plugin.common.ThriftObject;
import com.zhaopin.plugin.common.ThriftService;
import com.zhaopin.plugin.common.ThriftStruct;
import com.zhaopin.plugin.util.StringFinder;

public abstract class AbstractGenerateHelper {
	// 源代码根路径
	private final File srcDir;

	public AbstractGenerateHelper(File srcDir) {
		this.srcDir = srcDir;
	}

	protected String getClassName(String pkg, String simpleName) {
		if (pkg == null || pkg.trim().equals("")) {
			return simpleName;
		}
		return pkg + "." + simpleName;
	}

	protected String getClassName(ThriftObject thriftService) {
		return getClassName(thriftService.getPkgName(), thriftService.getClassName());
	}

	protected ItemPair<String, String> getWraper(ThriftService thriftService) {
		String pkgName = Constants.WRAPER_PREFIX + "." + retrivePkgPrefix(thriftService.getPkgName());
		String wraperClass = thriftService.getClassName() + Constants.WRAPER_SUFFIX;
		return new ItemPair<String, String>(pkgName, wraperClass);
	}

	protected ItemPair<String, String> getProcessor(ThriftService thriftService) {
		String wraperClass = Constants.ROCESSOR_CLS_PREFIX + thriftService.getClassName()
				+ Constants.ROCESSOR_CLS_SUFFIX;
		return new ItemPair<String, String>(thriftService.getPkgName(), wraperClass);
	}

	protected ItemPair<String, String> getCodec(ThriftStruct thriftStruct) {
		String wraperClass = Constants.CODEC_CLS_PREFIX + thriftStruct.getClassName() + Constants.CODEC_CLS_SUFFIX;
		return new ItemPair<String, String>(thriftStruct.getPkgName(), wraperClass);
	}

	protected ItemPair<String, String> getInvoker(ThriftService thriftService) {
		String wraperClass = Constants.INVOKER_CLS_PREFIX + thriftService.getClassName() + Constants.INVOKER_CLS_SUFFIX;
		return new ItemPair<String, String>(thriftService.getPkgName(), wraperClass);
	}

	protected String formatImports(Set<String> imports) {
		StringBuffer buf = new StringBuffer();
		for (String str : imports) {
			buf.append("import " + str.trim()).append(";\r\n");
		}
		buf.append("\r\n");
		return buf.toString();
	}

	protected String formatField(String prop, String type, String name) {
		String formatField = "\t#{0} #{1} #{2};\r\n";
		return formatField.replace("#{0}", prop).replace("#{1}", type).replace("#{2}", name);
	}

	protected String formatClassDefine(String className) {
		return formatClassDefine(className, null, null);
	}

	protected String formatClassDefine(String className, String superName, String[] interfaces) {
		return formatClassDefine("public", className, superName, interfaces);
	}

	protected String formatClassDefine(String classProp, String className, String superName, String[] interfaces) {
		StringBuffer classDefine = new StringBuffer();
		classDefine.append(classProp + " class " + className);
		if (superName != null) {
			classDefine.append(" extends " + superName);
		}
		if (interfaces != null) {
			for (int t = 0; t < interfaces.length; ++t) {
				if (t == 0) {
					classDefine.append(" implements " + interfaces[t]);
				} else {
					classDefine.append(", " + interfaces[t]);
				}
			}
		}
		return classDefine.toString();
	}

	protected String formatPackage(String pkgName) {
		String formatPkg = "package #{1};\r\n\r\n";
		return formatPkg.replace("#{1}", pkgName);
	}

	/**
	 * 去除掉com.zhaopin二级结构，便于添加头部
	 *
	 * @param pkgPath
	 * @return
	 */
	protected String retrivePkgPrefix(String pkgPath) {
		int index = StringFinder.findIndex(pkgPath, ".", 2);
		if (index < 0) {
			throw new IllegalArgumentException("包路径" + pkgPath + "不合法!");
		}
		return pkgPath.substring(index + 1);
	}

	public File getSrcDir() {
		return srcDir;
	}

	public String getSrcPath() {
		try {
			return getSrcDir().getCanonicalPath();
		} catch (Exception exp) {
			throw new IllegalStateException(exp);
		}
	}
}
