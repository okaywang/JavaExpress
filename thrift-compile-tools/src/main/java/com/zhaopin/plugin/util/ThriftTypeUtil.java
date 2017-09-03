package com.zhaopin.plugin.util;

import com.zhaopin.plugin.Constants;
import com.zhaopin.plugin.common.ThriftObject;
import com.zhaopin.plugin.common.ThriftService;
import com.zhaopin.plugin.common.ThriftServiceFunc;
import com.zhaopin.plugin.common.ThriftServiceFuncParam;
import com.zhaopin.plugin.common.ThriftStruct;
import com.zhaopin.plugin.common.ThriftStructField;

public final class ThriftTypeUtil {

	public static String getDtoCodecPath(String dtoClass) {
		if (!dtoClass.contains(".")) {
			return Constants.CODEC_CLS_PREFIX + dtoClass + Constants.CODEC_CLS_SUFFIX;
		}
		int index = dtoClass.lastIndexOf(".");
		String pkg = dtoClass.substring(0, index);
		String codecName = Constants.CODEC_CLS_PREFIX + dtoClass.substring(index + 1) + Constants.CODEC_CLS_SUFFIX;
		return pkg + "." + codecName;
	}

	public static String getFuncReturnClassPath(ThriftServiceFunc func, ThriftService thriftService) {
		for (String importClass : thriftService.getImports()) {
			if (importClass.equals(func.getRetType()) || importClass.endsWith("." + func.getRetType())) {
				return importClass;
			}
		}
		// 两种情况出现这个原因
		// 1. 包引用里面有import java.util.*;的情况
		// 2. 该成员和类在同一个包内
		return func.getRetType();
		// String pkg = thriftService.getPkgName();
		// if (pkg == null || pkg.trim().equals("")) {
		// return func.getRetType();
		// }
		// return pkg.trim() + "." + func.getRetType();
	}

	public static String getFuncParamClassPath(ThriftServiceFuncParam funcParam, ThriftService thriftService) {
		for (String importClass : thriftService.getImports()) {
			if (importClass.equals(funcParam.getParamType()) || importClass.endsWith("." + funcParam.getParamType())) {
				return importClass;
			}
		}
		// 两种情况出现这个原因
		// 1. 包引用里面有import java.util.*;的情况
		// 2. 该成员和类在同一个包内
		return funcParam.getParamType();
		// String pkg = thriftService.getPkgName();
		// if (pkg == null || pkg.trim().equals("")) {
		// return funcParam.getParamType();
		// }
		// return pkg.trim() + "." + funcParam.getParamType();
	}

	public static String getStructFieldClassPath(ThriftStructField field, ThriftStruct struct) {
		for (String importClass : struct.getImports()) {
			if (importClass.equals(field.getFieldType()) || importClass.endsWith("." + field.getFieldType())) {
				return importClass;
			}
		}
		// 两种情况出现这个原因
		// 1. 包引用里面有import java.util.*;的情况
		// 2. 该成员和类在同一个包内
		return field.getFieldType();

		// String pkg = struct.getPkgName();
		// if (pkg == null || pkg.trim().equals("")) {
		// return field.getFieldType();
		// }
		// return pkg.trim() + "." + field.getFieldType();
	}

	public static String getClassPath(String simpleName, ThriftObject struct) {
		for (String importClass : struct.getImports()) {
			if (importClass.equals(simpleName) || importClass.endsWith("." + simpleName)) {
				return importClass;
			}
		}
		// 两种情况出现这个原因
		// 1. 包引用里面有import java.util.*;的情况
		// 2. 该成员和类在同一个包内
		return simpleName;
		// String pkg = struct.getPkgName();
		// if (pkg == null || pkg.trim().equals("")) {
		// return struct.getClassName();
		// }
		// return pkg.trim() + "." + simpleName;
	}
}
