package com.zhaopin.plugin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhaopin.plugin.Constants;
import com.zhaopin.plugin.common.SupportTypes;

public class ThriftTypeDetermine {

	public static String resolveType(String genericType) {
		genericType = genericType.trim();
		if (SupportTypes.BOOL.equals(genericType)) {
			return Constants._TTYPE_BOOL;
		} else if (SupportTypes.BYTE.equals(genericType)) {
			return Constants._TTYPE_BYTE;
		} else if (SupportTypes.I16.equals(genericType)) {
			return Constants._TTYPE_I16;
		} else if (SupportTypes.I32.equals(genericType)) {
			return Constants._TTYPE_I32;
		} else if (SupportTypes.I64.equals(genericType)) {
			return Constants._TTYPE_I64;
		} else if (SupportTypes.DOUBLE.equals(genericType)) {
			return Constants._TTYPE_DOUBLE;
		} else if (SupportTypes.STRING.equals(genericType)) {
			return Constants._TTYPE_STRING;
		} else if (isListType(genericType)) {
			return Constants._TTYPE_LIST;
		} else if (isSetType(genericType)) {
			return Constants._TTYPE_SET;
		} else if (isMapType(genericType)) {
			return Constants._TTYPE_MAP;
		} else {
			return Constants._TTYPE_STRUCT;
		}

	}

	public static boolean isListType(String fieldType) {
		Pattern pattern = Pattern.compile("^list *<(.*)>$");
		Matcher matcher = pattern.matcher(fieldType);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	public static boolean isSetType(String fieldType) {
		Pattern pattern = Pattern.compile("^set *<(.*)>$");
		Matcher matcher = pattern.matcher(fieldType);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	public static boolean isMapType(String fieldType) {
		Pattern pattern = Pattern.compile("^map *<(.*)>$");
		Matcher matcher = pattern.matcher(fieldType);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	public static boolean isCollectionType(String fieldType) {
		return isListType(fieldType) || isSetType(fieldType) || isMapType(fieldType);
	}

	/**
	 * 判断数据类型是否是基本类型
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isBasicType(String type) {
		if (isNumberTypeMapping(type)) {
			return true;
		} else if ("string".equals(type.trim())) {
			return true;
		}
		return false;
	}

	public static boolean isNumberTypeMapping(String type) {
		String types[] = new String[] { "bool", "byte", "i16", "i32", "i64", "double" };
		for (String item : types) {
			if (item.equals(type)) {
				return true;
			}
		}
		return false;

	}

	public static String numberTypeMapping(String type) {
		String types[] = new String[] { "bool", "byte", "i16", "i32", "i64", "double" };
		String result[] = new String[] { "Boolean", "Byte", "Short", "Integer", "Long", "Double" };
		for (int t = 0; t < types.length; ++t) {
			if (types[t].equals(type)) {
				return result[t];
			}
		}
		throw new IllegalArgumentException("" + type + " is invalid!");
	}

}
