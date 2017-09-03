package com.zhaopin.plugin.util;

import java.util.HashMap;
import java.util.Map;

import com.zhaopin.plugin.template.ThriftCodeTemplate;
import com.zhaopin.plugin.common.SupportTypes;
import com.zhaopin.plugin.template.ThriftBoolCodeTemplate;
import com.zhaopin.plugin.template.ThriftByteCodeTemplate;
import com.zhaopin.plugin.template.ThriftDateCodeTemplate;
import com.zhaopin.plugin.template.ThriftDoubleCodeTemplate;
import com.zhaopin.plugin.template.ThriftDtoTemplate;
import com.zhaopin.plugin.template.ThriftI16CodeTemplate;
import com.zhaopin.plugin.template.ThriftI32CodeTemplate;
import com.zhaopin.plugin.template.ThriftI64CodeTemplate;
import com.zhaopin.plugin.template.ThriftListTemplate;
import com.zhaopin.plugin.template.ThriftMapTemplate;
import com.zhaopin.plugin.template.ThriftSetTemplate;
import com.zhaopin.plugin.template.ThriftStringCodeTemplate;

public final class ThriftTypeResolve {

	public final static Map<String, ThriftCodeTemplate> ThriftCodeTemplates = getThriftCodeTemplates();

	public final static ThriftCodeTemplate getThriftCodeTemplate(String type) {
		return ThriftCodeTemplates.get(getType(type));
	}

	public final static String getType(String type) {
		String thriftType = null;
		// 对于基础类型
		if (SupportTypes.BOOL.equals(type)) {
			thriftType = SupportTypes.BOOL;
		} else if (SupportTypes.BYTE.equals(type)) {
			thriftType = SupportTypes.BYTE;
		} else if (SupportTypes.I16.equals(type)) {
			thriftType = SupportTypes.I16;
		} else if (SupportTypes.I32.equals(type)) {
			thriftType = SupportTypes.I32;
		} else if (SupportTypes.I64.equals(type)) {
			thriftType = SupportTypes.I64;
		} else if (SupportTypes.DOUBLE.equals(type)) {
			thriftType = SupportTypes.DOUBLE;
		} else if (SupportTypes.STRING.equals(type)) {
			thriftType = SupportTypes.STRING;
		} else if (SupportTypes.DATE.equals(type)) {
			thriftType = SupportTypes.DATE;
		} else if (ThriftTypeDetermine.isListType(type)) {
			thriftType = SupportTypes.LIST;
		} else if (ThriftTypeDetermine.isSetType(type)) {
			thriftType = SupportTypes.SET;
		} else if (ThriftTypeDetermine.isMapType(type)) {
			thriftType = SupportTypes.MAP;
		} else {
			thriftType = SupportTypes.DTO;
		}
		return thriftType;
	}

	public final static Map<String, ThriftCodeTemplate> getThriftCodeTemplates() {
		Map<String, ThriftCodeTemplate> thriftCodeTemplates = new HashMap<String, ThriftCodeTemplate>();
		thriftCodeTemplates.put(SupportTypes.BOOL, new ThriftBoolCodeTemplate());
		thriftCodeTemplates.put(SupportTypes.BYTE, new ThriftByteCodeTemplate());
		thriftCodeTemplates.put(SupportTypes.I16, new ThriftI16CodeTemplate());
		thriftCodeTemplates.put(SupportTypes.I32, new ThriftI32CodeTemplate());
		thriftCodeTemplates.put(SupportTypes.I64, new ThriftI64CodeTemplate());
		thriftCodeTemplates.put(SupportTypes.DOUBLE, new ThriftDoubleCodeTemplate());
		thriftCodeTemplates.put(SupportTypes.STRING, new ThriftStringCodeTemplate());
		thriftCodeTemplates.put(SupportTypes.DATE, new ThriftDateCodeTemplate());
		thriftCodeTemplates.put(SupportTypes.LIST, new ThriftListTemplate());
		thriftCodeTemplates.put(SupportTypes.SET, new ThriftSetTemplate());
		thriftCodeTemplates.put(SupportTypes.MAP, new ThriftMapTemplate());
		thriftCodeTemplates.put(SupportTypes.DTO, new ThriftDtoTemplate());
		return thriftCodeTemplates;
	}

	public static boolean isNumberOrBoolType(String fieldType) {
		// 是否是基本类型的list
		if (SupportTypes.BOOL.equals(fieldType)) {
			return true;
		} else if (SupportTypes.BYTE.equals(fieldType)) {
			return true;
		} else if (SupportTypes.I16.equals(fieldType)) {
			return true;
		} else if (SupportTypes.I32.equals(fieldType)) {
			return true;
		} else if (SupportTypes.I64.equals(fieldType)) {
			return true;
		} else if (SupportTypes.DOUBLE.equals(fieldType)) {
			return true;
		}
		return false;
	}
}
