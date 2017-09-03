package com.zhaopin.plugin.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhaopin.plugin.common.ItemPair;

public class ThriftType2JavaType {
	// ���͵�ӳ��
	public static Map<String, String> MAPPING = init();
	// ����ӳ��
	public static Map<String, String> GENERIC_MAPPING = genericInit();

	private static Map<String, String> init() {
		Map<String, String> MAPPING = new HashMap<String, String>();
		// ��������ӳ��
		MAPPING.put("bool", "boolean");
		MAPPING.put("byte", "byte");
		MAPPING.put("i16", "short");
		MAPPING.put("i32", "int");
		MAPPING.put("i64", "long");
		MAPPING.put("double", "double");
		MAPPING.put("string", "String");
		MAPPING.put("date", "Date");
		return MAPPING;
	}
	
	public static String toWraper(String type) {
		String wraperType = GENERIC_MAPPING.get(type.trim());
		if (wraperType != null) {
			return wraperType;
		} else {
			return type.trim();
		}
	}

	private static Map<String, String> genericInit() {
		Map<String, String> MAPPING = new HashMap<String, String>();
		// ��������ӳ��
		MAPPING.put("boolean", "Boolean");
		MAPPING.put("byte", "Byte");
		MAPPING.put("short", "Short");
		MAPPING.put("int", "Integer");
		MAPPING.put("long", "Long");
		MAPPING.put("double", "Double");
		return MAPPING;
	}

	public static String convert(String fieldType, List<String> generics) {
		String result = MAPPING.get(fieldType);
		if (result != null) {
			return result;
		}
		if (ThriftTypeDetermine.isListType(fieldType)) {
			if (generics != null && !generics.contains(List.class.getName())) {
				generics.add(List.class.getName());
			}
			return resolveListType(fieldType, generics);
		} else if (ThriftTypeDetermine.isSetType(fieldType)) {
			if (generics != null && !generics.contains(Set.class.getName())) {
				generics.add(Set.class.getName());
			}
			return resolveSetType(fieldType, generics);
		} else if (ThriftTypeDetermine.isMapType(fieldType)) {
			if (generics != null && !generics.contains(Map.class.getName())) {
				generics.add(Map.class.getName());
			}
			return resolveMapType(fieldType, generics);
		} else {
			if (generics != null) {
				generics.add(fieldType.trim());
			}
			return fieldType;
		}
	}

	public static String convert(String fieldType) {
		return convert(fieldType, null);
	}

	// ���list
	private static String resolveListType(String fieldType, List<String> generics) {
		Pattern pattern = Pattern.compile("^list *<(.*)>$");
		Matcher matcher = pattern.matcher(fieldType);
		if (matcher.find()) {
			String genericType = matcher.group(1).trim();
			genericType = normal(convert(genericType, generics));
			return "List<" + genericType + ">";
		} else {
			throw new IllegalStateException("type " + fieldType + " is invalid!");
		}
	}

	// ���set
	private static String resolveSetType(String fieldType, List<String> generics) {
		Pattern pattern = Pattern.compile("^set *<(.*)>$");
		Matcher matcher = pattern.matcher(fieldType);
		if (matcher.find()) {
			String genericType = matcher.group(1).trim();
			genericType = normal(convert(genericType, generics));
			return "Set<" + genericType + ">";
		} else {
			throw new IllegalStateException("type " + fieldType + " is invalid!");
		}
	}

	// ���map
	private static String resolveMapType(String fieldType, List<String> generics) {
		Pattern pattern = Pattern.compile("^map *<(.*)>$");
		Matcher matcher = pattern.matcher(fieldType);
		if (matcher.find()) {
			String innerType = matcher.group(1).trim();
			ItemPair<String, String> kv = resolveKV(innerType);
			String key = normal(convert(kv.getKey(), generics));
			String value = normal(convert(kv.getValue(), generics));
			return "Map<" + key + ", " + value + ">";
		} else {
			throw new IllegalStateException("type " + fieldType + " is invalid!");
		}
	}

	public static String normal(String fieldType) {
		String value = GENERIC_MAPPING.get(fieldType);
		if (value != null) {
			return value;
		}
		return fieldType;
	}

	public static class ThriftTypeHolder {

		public String result;

		public List<String> types = new ArrayList<String>();

	}

	public static ItemPair<String, String> resolveKV(String innerType) {
		Stack<Integer> stack = new Stack<Integer>();
		List<PosPair> posPairList = new ArrayList<PosPair>();
		for (int t = 0, len = innerType.length(); t < len; ++t) {
			char ch = innerType.charAt(t);
			// ������ջ����
			if (ch == '<') {
				stack.push(t);
			} else if (ch == '>') {
				if (stack.empty()) {
					throw new IllegalStateException("generic " + innerType + "type is invalid!");
				}
				// ��¼λ����Ϣ
				posPairList.add(new PosPair(stack.pop(), t));
			}
		}
		// ��ȡ���ʱ,��ջ��Ԫ��ֻ��һ��
		if (!stack.isEmpty()) {
			throw new IllegalStateException("generic " + innerType + "type is invalid!");
		}
		String key = null, value = null;
		if (posPairList.isEmpty()) {
			// ���治�ٰ�������, ֱ���ö��Ÿ�������
			String[] types = innerType.split(",");
			if (types.length != 2) {
				throw new IllegalStateException("generic " + innerType + "type is invalid!");
			}
			key = types[0];
			value = types[1];
		} else {
			// Ѱ�ҷ�Χ��������Ԫ��(���������)
			PosPair first = new PosPair(Integer.MAX_VALUE, Integer.MIN_VALUE);
			PosPair second = new PosPair(Integer.MAX_VALUE, Integer.MIN_VALUE);
			// Ѱ�ҵ�һ��������ϵ
			for (PosPair posPair : posPairList) {
				if (posPair.start < first.start && posPair.end > first.end) {
					first = posPair;
				}
			}
			// ����first���ǵ�Ԫ���Ƴ�
			Iterator<PosPair> it = posPairList.iterator();
			while (it.hasNext()) {
				PosPair posPair = it.next();
				if (posPair.start >= first.start && posPair.end <= first.end) {
					it.remove();
				}
			}
			// Ѱ�ҵڶ���������ϵ
			if (posPairList.isEmpty()) {
				// �����ʱ�Ѿ��б�Ϊ��
				second = null;
			} else {
				for (PosPair posPair : posPairList) {
					if (posPair.start < second.start && posPair.end > second.end) {
						second = posPair;
					}
				}
			}
			if (second == null) {
				int seprator = 0;
				if (first.end == innerType.length() - 1) {
					// ��ǰ���ҵ�һ������
					for (int t = first.start; t >= 0; --t) {
						if (innerType.charAt(t) == ',') {
							seprator = t;
							break;
						}
					}
				} else {
					seprator = innerType.indexOf(",", first.end + 1);
					if (seprator < 0) {
						throw new IllegalStateException("generic " + innerType + "type is invalid!");
					}
				}
				key = innerType.substring(0, seprator).trim();
				value = innerType.substring(seprator + 1).trim();
			} else {
				// ��first��second����
				if (first.start > second.start) {
					PosPair tmp = first;
					first = second;
					second = tmp;
				}
				key = innerType.substring(0, first.end + 1).trim();
				int index = innerType.indexOf(",", first.end + 1);
				if (index < 0 || index >= second.start) {
					throw new IllegalStateException("generic " + innerType + "type is invalid!");
				}
				value = innerType.substring(index + 1).trim();
			}
		}
		return new ItemPair<String, String>(key.trim(), value.trim());
	}

	private static class PosPair {
		// ��ʼλ��
		public final int start;
		// ��ֹλ��
		public final int end;

		public PosPair(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public String toString() {
			return "PosPair [start=" + start + ", end=" + end + "]";
		}
	}
}
