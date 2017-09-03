package com.zhaopin.plugin.template;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.zhaopin.plugin.Constants;
import com.zhaopin.plugin.common.ItemPair;
import com.zhaopin.plugin.common.SupportTypes;
import com.zhaopin.plugin.common.ThriftObject;
import com.zhaopin.plugin.util.ThriftType2JavaType;
import com.zhaopin.plugin.util.ThriftTypeDetermine;
import com.zhaopin.plugin.util.ThriftTypeUtil;
import com.zhaopin.thrift.rpc.helper.BasicHelper;

import io.netty.util.internal.StringUtil;

public abstract class AbstractCollectionCodeTemplate extends AbstractThriftCodeTemplate {

	public String genericDecodeCode(String fieldType, String space, String retName, int level, ThriftObject thriftObj) {
		StringBuffer buf = new StringBuffer();
		if (ThriftTypeDetermine.isListType(fieldType)) {
			// 循环处理list的泛型
			Pattern pattern = Pattern.compile("^list *<(.*)>$");
			Matcher matcher = pattern.matcher(fieldType);
			matcher.find();
			String retType = ThriftType2JavaType.convert(fieldType);
			String innerType = matcher.group(1).trim();
			if (isBasicType(innerType)) { // 基本类型
				buf.append(readBasicList(innerType, retType, retName, space));
			} else {
				buf.append(space).append(retType + " " + retName + " = null;").append("\r\n");
				buf.append(space).append("{").append("\r\n");
				buf.append(formatReadListBegin(space, "tlist" + level));
				// 判断size是否大于0
				buf.append(formatSizeGtZero(space, "tlist" + level + ".getSize()"));
				buf.append(space + "\t").append(retName + " = new java.util.Array" + retType + "();").append("\r\n");
				if (ThriftTypeDetermine.isListType(innerType)) { // list类型
					// 进行for循环
					buf.append(space + "\t").append("for (int t" + level + "=0; t" + level + "<" + "tlist" + level
							+ ".getSize(); ++t" + level + "){").append("\r\n");
					buf.append(
							genericDecodeCode(innerType, space + "\t\t", "list" + (level + 1), level + 1, thriftObj));
					buf.append(space + "\t\t").append(retName + ".add(" + "list" + (level + 1) + ");").append("\r\n");
					buf.append(space + "\t").append("}").append("\r\n");
				} else if (ThriftTypeDetermine.isSetType(innerType)) { // set类型
					// 进行for循环
					buf.append(space + "\t").append("for (int t" + level + "=0; t" + level + "<" + "tlist" + level
							+ ".getSize(); ++t" + level + "){").append("\r\n");
					buf.append(genericDecodeCode(innerType, space + "\t\t", "set" + (level + 1), level + 1, thriftObj));
					buf.append(space + "\t\t").append(retName + ".add(" + "set" + (level + 1) + ");").append("\r\n");
					buf.append(space + "\t").append("}").append("\r\n");
				} else if (ThriftTypeDetermine.isMapType(innerType)) { // map类型
					buf.append(space + "\t").append("for (int t" + level + "=0; t" + level + "<" + "tlist" + level
							+ ".getSize(); ++t" + level + "){").append("\r\n");
					buf.append(genericDecodeCode(innerType, space + "\t\t", "map" + (level + 1), level + 1, thriftObj));
					buf.append(space + "\t\t").append(retName + ".add(" + "map" + (level + 1) + ");").append("\r\n");
					buf.append(space + "\t").append("}").append("\r\n");
				} else { // dto类型
					buf.append(space + "\t").append("for (int t" + level + "=0; t" + level + "<" + "tlist" + level
							+ ".getSize(); ++t" + level + "){").append("\r\n");
					buf.append(doDecodeDto(innerType, "dto" + level, thriftObj, space + "\t"));
					buf.append(space + "\t\t").append(retName + ".add(" + "dto" + level + ");").append("\r\n");
					buf.append(space + "\t").append("}").append("\r\n");
				}
				buf.append(formatIfEnd(space));
				buf.append(space).append("}").append("\r\n");
			}
		} else if (ThriftTypeDetermine.isSetType(fieldType)) {
			// 循环处理list的泛型
			Pattern pattern = Pattern.compile("^set *<(.*)>$");
			Matcher matcher = pattern.matcher(fieldType);
			matcher.find();
			String retType = ThriftType2JavaType.convert(fieldType);
			String innerType = matcher.group(1).trim();
			if (isBasicType(innerType)) { // 基本类型
				buf.append(readBasicSet(innerType, retType, retName, space));
			} else {
				buf.append(space).append(retType + " " + retName + " = null;").append("\r\n");
				buf.append(formatReadSetBegin(space, "tset" + level));
				buf.append(space).append("{").append("\r\n");
				// 判断size是否大于0
				buf.append(formatSizeGtZero(space, "tset" + level + ".getSize()"));
				buf.append(space + "\t").append(retName + " = new java.util.Hash" + retType + "();").append("\r\n");
				if (ThriftTypeDetermine.isListType(innerType)) { // list类型
					// 进行for循环
					buf.append(space + "\t").append("for (int t" + level + "=0; t" + level + "<" + "tset" + level
							+ ".getSize(); ++t" + level + "){").append("\r\n");
					buf.append(
							genericDecodeCode(innerType, space + "\t\t", "list" + (level + 1), level + 1, thriftObj));
					buf.append(space + "\t\t").append(retName + ".add(" + "list" + (level + 1) + ");").append("\r\n");
					buf.append(space + "\t").append("}").append("\r\n");
				} else if (ThriftTypeDetermine.isSetType(innerType)) { // set类型
					// 进行for循环
					buf.append(space + "\t").append("for (int t" + level + "=0; t" + level + "<" + "tset" + level
							+ ".getSize(); ++t" + level + "){").append("\r\n");
					buf.append(genericDecodeCode(innerType, space + "\t\t", "set" + (level + 1), level + 1, thriftObj));
					buf.append(space + "\t\t").append(retName + ".add(" + "set" + (level + 1) + ");").append("\r\n");
					buf.append(space + "\t").append("}").append("\r\n");
				} else if (ThriftTypeDetermine.isMapType(innerType)) { // map类型
					buf.append(space + "\t").append("for (int t" + level + "=0; t" + level + "<" + "tset" + level
							+ ".getSize(); ++t" + level + "){").append("\r\n");
					buf.append(genericDecodeCode(innerType, space + "\t\t", "map" + (level + 1), level + 1, thriftObj));
					buf.append(space + "\t\t").append(retName + ".add(" + "map" + (level + 1) + ");").append("\r\n");
					buf.append(space + "\t").append("}").append("\r\n");
				} else { // dto类型
					buf.append(space + "\t").append("for (int t" + level + "=0; t" + level + "<" + "tset" + level
							+ ".getSize(); ++t" + level + "){").append("\r\n");
					buf.append(doDecodeDto(innerType, "dto" + level, thriftObj, space + "\t"));
					buf.append(space + "\t\t").append(retName + ".add(" + "dto" + level + ");").append("\r\n");
					buf.append(space + "\t").append("}").append("\r\n");
				}
				buf.append(formatIfEnd(space));
				buf.append(space).append("}").append("\r\n");
			}
		} else if (ThriftTypeDetermine.isMapType(fieldType)) {
			// 解决map问题, 首先提取map里面的泛型
			Pattern pattern = Pattern.compile("^map *<(.*)>$");
			Matcher matcher = pattern.matcher(fieldType);
			matcher.find();
			String innerType = matcher.group(1).trim();
			// 对innerType进行解析，提取出里面的Key, Value
			ItemPair<String, String> kv = ThriftType2JavaType.resolveKV(innerType);
			buf.append(formatReadMapBegin(space, "tmap" + level));
			String retType = ThriftType2JavaType.convert(fieldType);
			buf.append(space).append(retType + " " + retName + " = null;").append("\r\n");
			buf.append(formatSizeGtZero(space, "tmap" + level + ".getSize()"));
			String Initilize = "#{0}#{1} = new java.util.Hash#{2}();\r\n";
			buf.append(Initilize.replace("#{0}", space + "\t").replace("#{2}", retType).replace("#{1}", retName));
			String Iterator = "#{0}for(int #{1} = 0; #{1} < #{2}; ++#{1}) {\r\n";
			buf.append(Iterator.replace("#{0}", space + "\t").replace("#{1}", "t" + level).replace("#{2}",
					"tmap" + level + ".getSize()"));
			if (isBasicType(kv.getKey())) { // key是基本类型
				buf.append(readBasicMap(kv.getKey(), "key" + level, space + "\t\t"));
			} else if (ThriftTypeDetermine.isListType(kv.getKey())) { // key的类型是list
				buf.append(genericDecodeCode(kv.getKey(), space + "\t\t", "key" + level, level + 1, thriftObj));
			} else if (ThriftTypeDetermine.isSetType(kv.getKey())) { // key的类型是set
				buf.append(genericDecodeCode(kv.getKey(), space + "\t\t", "key" + level, level + 1, thriftObj));
			} else if (ThriftTypeDetermine.isMapType(kv.getKey())) { // key的类型是map
				buf.append(genericDecodeCode(kv.getKey(), space + "\t\t", "key" + level, level + 1, thriftObj));
			} else { // dto类型
				buf.append(doDecodeDto(kv.getKey(), "key" + level, thriftObj, space + "\t"));
			}
			if (isBasicType(kv.getValue())) { // key是基本类型
				buf.append(readBasicMap(kv.getValue(), "value" + level, space + "\t\t"));
			} else if (ThriftTypeDetermine.isListType(kv.getValue())) { // key的类型是list
				buf.append(genericDecodeCode(kv.getValue(), space + "\t\t", "value" + level, level + 1, thriftObj));
			} else if (ThriftTypeDetermine.isSetType(kv.getValue())) { // key的类型是set
				buf.append(genericDecodeCode(kv.getValue(), space + "\t\t", "value" + level, level + 1, thriftObj));
			} else if (ThriftTypeDetermine.isMapType(kv.getValue())) { // key的类型是map
				buf.append(genericDecodeCode(kv.getValue(), space + "\t\t", "value" + level, level + 1, thriftObj));
			} else { // dto类型
				buf.append(doDecodeDto(kv.getValue(), "value" + level, thriftObj, space + "\t"));
			}
			// 加入到map中
			buf.append(space + "\t\t").append(retName + ".put(" + "key" + level + ", value" + level + ");")
					.append("\r\n");
			buf.append(formatForEnd(space + "\t"));
			buf.append(formatIfEnd(space));
			buf.append(formatReadMapEnd(space));
		}
		return buf.toString();
	}

	public String genericEncoderCode(String fieldType, String space, String param, int level, ThriftObject thriftObj) {
		StringBuffer buf = new StringBuffer();
		if (ThriftTypeDetermine.isListType(fieldType)) {
			// 循环处理list的泛型
			Pattern pattern = Pattern.compile("^list *<(.*)>$");
			Matcher matcher = pattern.matcher(fieldType);
			matcher.find();
			String innerType = matcher.group(1).trim();
			String itemType = ThriftTypeDetermine.resolveType(innerType);
			buf.append(formatWriteListBegin(space, itemType, param + ".size()"));
			if (isBasicType(innerType)) {
				return writeBasicList(space, innerType, param);
			} else if (ThriftTypeDetermine.isListType(innerType)) {
				buf.append(doGenerateList(innerType, fieldType, param, level, thriftObj, space));
			} else if (ThriftTypeDetermine.isSetType(innerType)) {
				buf.append(doGenerateSet(innerType, fieldType, param, level, thriftObj, space));
			} else if (ThriftTypeDetermine.isMapType(innerType)) {
				buf.append(doGenerateMap(innerType, fieldType, param, level, thriftObj, space));
			} else {
				buf.append(doGenerateMultiDto(innerType, param, level, thriftObj, space));
			}
			buf.append(formatWriteListEnd(space));
		} else if (ThriftTypeDetermine.isSetType(fieldType)) {
			// 循环处理list的泛型
			Pattern pattern = Pattern.compile("^set *<(.*)>$");
			Matcher matcher = pattern.matcher(fieldType);
			matcher.find();
			String innerType = matcher.group(1).trim();
			String itemType = ThriftTypeDetermine.resolveType(innerType);
			buf.append(formatWriteSetBegin(space, itemType, param + ".size()"));
			if (isBasicType(innerType)) {
				return writeBasicSet(space, innerType, param);
			} else if (ThriftTypeDetermine.isListType(innerType)) {
				buf.append(doGenerateList(innerType, fieldType, param, level, thriftObj, space));
			} else if (ThriftTypeDetermine.isSetType(innerType)) {
				buf.append(doGenerateSet(innerType, fieldType, param, level, thriftObj, space));
			} else if (ThriftTypeDetermine.isMapType(innerType)) {
				buf.append(doGenerateMap(innerType, fieldType, param, level, thriftObj, space));
			} else {
				buf.append(doGenerateMultiDto(innerType, param, level, thriftObj, space));
			}
			buf.append(formatWriteSetEnd(space));
		} else if (ThriftTypeDetermine.isMapType(fieldType)) {
			// 解决map问题, 首先提取map里面的泛型
			Pattern pattern = Pattern.compile("^map *<(.*)>$");
			Matcher matcher = pattern.matcher(fieldType);
			matcher.find();
			String innerType = matcher.group(1).trim();
			// 对innerType进行解析，提取出里面的Key, Value
			ItemPair<String, String> kv = ThriftType2JavaType.resolveKV(innerType);
			// 确定key的类型
			String keyType = ThriftTypeDetermine.resolveType(kv.getKey());
			String valueType = ThriftTypeDetermine.resolveType(kv.getValue());
			String keyJavaType = ThriftType2JavaType.toWraper(ThriftType2JavaType.convert(kv.getKey()));
			String valJavaType = ThriftType2JavaType.toWraper(ThriftType2JavaType.convert(kv.getValue()));
			buf.append(formatWriteMapBegin(space, keyType, valueType, param + ".size()"));
			// 对每一项进行处理
			String itType = "Map.Entry<" + keyJavaType + ", " + valJavaType + ">";
			buf.append(formatMapIterator(itType, "item" + level, param + ".entrySet()", space));
			if (isBasicType(kv.getKey())) {
				buf.append(writeBasic(space + "\t", kv.getKey(), "item" + level + ".getKey()"));
			} else {
				buf.append(formatIfNotEqual(space + "\t", new String[] { "item" + level + ".getKey()", "null" }));
				if (ThriftTypeDetermine.isListType(kv.getKey())) {
					buf.append(genericEncoderCode(kv.getKey(), space + "\t\t", "item" + level + ".getKey()", level + 1,
							thriftObj));
				} else if (ThriftTypeDetermine.isSetType(kv.getKey())) {
					buf.append(genericEncoderCode(kv.getKey(), space + "\t\t", "item" + level + ".getKey()", level + 1,
							thriftObj));
				} else if (ThriftTypeDetermine.isMapType(kv.getKey())) {
					buf.append(genericEncoderCode(kv.getKey(), space + "\t\t", "item" + level + ".getKey()", level + 1,
							thriftObj));
				} else {
					buf.append(doGenerateSingleDto(kv.getKey(), "item" + level + ".getKey()", thriftObj, space + "\t"));
				}
				buf.append(formatElse(space + "\t"));
				// 对key进行null的情况下判断输出
				buf.append(resoleMapItemNull(kv.getKey(), space + "\t\t"));
				buf.append(formatIfEnd(space + "\t"));
			}
			// 处理value类型
			if (isBasicType(kv.getValue())) {
				buf.append(writeBasic(space + "\t", kv.getValue(), "item" + level + ".getValue()"));
			} else {
				buf.append(formatIfNotEqual(space + "\t", new String[] { "item" + level + ".getValue()", "null" }));
				if (ThriftTypeDetermine.isListType(kv.getValue())) {
					buf.append(genericEncoderCode(kv.getValue(), space + "\t\t", "item" + level + ".getValue()",
							level + 1, thriftObj));
				} else if (ThriftTypeDetermine.isSetType(kv.getValue())) {
					buf.append(genericEncoderCode(kv.getValue(), space + "\t\t", "item" + level + ".getValue()",
							level + 1, thriftObj));
				} else if (ThriftTypeDetermine.isMapType(kv.getValue())) {
					buf.append(genericEncoderCode(kv.getValue(), space + "\t\t", "item" + level + ".getValue()",
							level + 1, thriftObj));
				} else {
					buf.append(doGenerateSingleDto(kv.getValue(), "item" + level + ".getValue()", thriftObj,
							space + "\t"));
				}
				buf.append(formatElse(space + "\t"));
				// 对value进行null的情况下判断输出
				buf.append(resoleMapItemNull(kv.getValue(), space + "\t\t"));
				buf.append(formatIfEnd(space + "\t"));
			}
			buf.append(formatForEnd(space));
			buf.append(formatWriteMapEnd(space));
		}
		return buf.toString();
	}

	private String writeBasicList(String space, String fieldType, String param) {
		StringBuffer buf = new StringBuffer();
		// 简单类型直接调用帮助类进行输出
		if (SupportTypes.BOOL.equals(fieldType)) {
			buf.append(space).append(Constants.LIST_HELPER + ".writeBool(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.BYTE.equals(fieldType)) {
			buf.append(space).append(Constants.LIST_HELPER + ".writeByte(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.I16.equals(fieldType)) {
			buf.append(space).append(Constants.LIST_HELPER + ".writeI16(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.I32.equals(fieldType)) {
			buf.append(space).append(Constants.LIST_HELPER + ".writeI32(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.I64.equals(fieldType)) {
			buf.append(space).append(Constants.LIST_HELPER + ".writeI64(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.DOUBLE.equals(fieldType)) {
			buf.append(space).append(Constants.LIST_HELPER + ".writeDouble(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.STRING.equals(fieldType)) {
			buf.append(space).append(Constants.LIST_HELPER + ".writeString(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.DATE.equals(fieldType)) {
			buf.append(space).append(Constants.LIST_HELPER + ".writeDate(oprot, " + param + ");").append("\r\n");
		} else {
			throw new IllegalStateException("type \"" + fieldType + "\"非法!");
		}
		return buf.toString();
	}

	private String readBasicList(String innerType, String retType, String retName, String space) {
		StringBuffer buf = new StringBuffer();
		// 简单类型直接调用帮助类进行输出
		if (SupportTypes.BOOL.equals(innerType)) {
			buf.append(space).append(retType + " " + retName + " = ")
					.append(Constants.LIST_HELPER + ".readBool(iprot);").append("\r\n");
		} else if (SupportTypes.BYTE.equals(innerType)) {
			buf.append(space).append(retType + " " + retName + " = ")
					.append(Constants.LIST_HELPER + ".readByte(iprot);").append("\r\n");
		} else if (SupportTypes.I16.equals(innerType)) {
			buf.append(space).append(retType + " " + retName + " = ").append(Constants.LIST_HELPER + ".readI16(iprot);")
					.append("\r\n");
		} else if (SupportTypes.I32.equals(innerType)) {
			buf.append(space).append(retType + " " + retName + " = ").append(Constants.LIST_HELPER + ".readI32(iprot);")
					.append("\r\n");
		} else if (SupportTypes.I64.equals(innerType)) {
			buf.append(space).append(retType + " " + retName + " = ").append(Constants.LIST_HELPER + ".readI64(iprot);")
					.append("\r\n");
		} else if (SupportTypes.DOUBLE.equals(innerType)) {
			buf.append(space).append(retType + " " + retName + " = ")
					.append(Constants.LIST_HELPER + ".readDouble(iprot);").append("\r\n");
		} else if (SupportTypes.STRING.equals(innerType)) {
			buf.append(space).append(retType + " " + retName + " = ")
					.append(Constants.LIST_HELPER + ".readString(iprot);").append("\r\n");
		} else if (SupportTypes.DATE.equals(innerType)) {
			buf.append(space).append(retType + " " + retName + " = ")
					.append(Constants.LIST_HELPER + ".readDate(iprot);").append("\r\n");
		}
		return buf.toString();
	}

	private String doGenerateList(String innerType, String fieldType, String param, int level, ThriftObject thriftObj,
			String space) {
		StringBuffer buf = new StringBuffer();
		String itemType = ThriftType2JavaType.convert(innerType);
		buf.append(formatIterator(space, itemType, "item" + level, param));
		buf.append(formatIfNotEqual(space + "\t", new String[] { "item" + level, "null" }));
		buf.append(genericEncoderCode(innerType, space + "\t\t", "item" + level, level + 1, thriftObj));
		buf.append(formatElse(space + "\t"));
		// 这里输出长度为-1的list，表示null
		buf.append(formatWriteListBegin(space + "\t\t", Constants.TTYPE_LIST, "-1"));
		buf.append(formatWriteListEnd(space + "\t\t"));
		buf.append(formatIfEnd(space + "\t"));
		buf.append(formatForEnd(space));
		return buf.toString();
	}

	private String doGenerateSet(String innerType, String fieldType, String param, int level, ThriftObject thriftObj,
			String space) {
		StringBuffer buf = new StringBuffer();
		String itemType = ThriftType2JavaType.convert(innerType);
		buf.append(formatIterator(space, itemType, "item" + level, param));
		buf.append(formatIfNotEqual(space + "\t", new String[] { "item" + level, "null" }));
		buf.append(genericEncoderCode(innerType, space + "\t\t", "item" + level, level + 1, thriftObj));
		buf.append(formatElse(space + "\t"));
		// 这里输出长度为-1的list，表示null
		buf.append(formatWriteSetBegin(space + "\t\t", Constants.TTYPE_SET, "-1"));
		buf.append(formatWriteSetEnd(space + "\t\t"));
		buf.append(formatIfEnd(space + "\t"));
		buf.append(formatForEnd(space));
		return buf.toString();
	}

	private String doGenerateMap(String innerType, String fieldType, String param, int level, ThriftObject thriftObj,
			String space) {
		StringBuffer buf = new StringBuffer();
		String itemType = ThriftType2JavaType.convert(innerType);
		Pattern pattern = Pattern.compile("^map *<(.*)>$");
		Matcher matcher = pattern.matcher(innerType);
		matcher.find();
		// 对innerType进行解析，提取出里面的Key, Value
		ItemPair<String, String> kv = ThriftType2JavaType.resolveKV(matcher.group(1).trim());
		String keyType = ThriftTypeDetermine.resolveType(kv.getKey());
		String valueType = ThriftTypeDetermine.resolveType(kv.getValue());
		buf.append(formatIterator(space, itemType, "item" + level, param));
		buf.append(formatIfNotEqual(space + "\t", new String[] { "item" + level, "null" }));
		buf.append(genericEncoderCode(innerType, space + "\t\t", "item" + level, level + 1, thriftObj));
		buf.append(formatElse(space + "\t"));
		// 这里输出长度为-1的list，表示null
		buf.append(formatWriteMapBegin(space + "\t\t", keyType, valueType, "-1"));
		buf.append(formatWriteSetEnd(space + "\t\t"));
		buf.append(formatIfEnd(space + "\t"));
		buf.append(formatForEnd(space));
		return buf.toString();
	}

	private String doGenerateMultiDto(String innerType, String param, int level, ThriftObject thriftObj, String space) {
		StringBuffer buf = new StringBuffer();
		String thriftImport = ThriftTypeUtil.getClassPath(innerType, thriftObj);
		// 避免同一个包内类型
		if (!StringUtils.isEmpty(thriftImport) && thriftImport.contains(".")) {
			thriftObj.addImport(thriftImport);
		}
		String codecCls = ThriftTypeUtil.getDtoCodecPath(thriftImport);
		String itemName = "item" + level;
		buf.append(formatIterator(space, innerType, itemName, param));
		buf.append(formatIfNotEqual(space + "\t", new String[] { itemName, "null" }));
		buf.append(space + "\t\t").append("new " + codecCls + "(" + itemName + ").encode(oprot);").append("\r\n");
		buf.append(formatElse(space + "\t"));
		buf.append(space + "\t\t").append(Constants.BASIC_HELPER + ".writeNullObject(oprot);\r\n");
		buf.append(formatIfEnd(space + "\t"));
		buf.append(formatForEnd(space));
		return buf.toString();
	}

	private String writeBasicSet(String space, String fieldType, String param) {
		StringBuffer buf = new StringBuffer();
		// 简单类型直接调用帮助类进行输出
		if (SupportTypes.BOOL.equals(fieldType)) {
			buf.append(space).append(Constants.SET_HELPER + ".writeBool(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.BYTE.equals(fieldType)) {
			buf.append(space).append(Constants.SET_HELPER + ".writeByte(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.I16.equals(fieldType)) {
			buf.append(space).append(Constants.SET_HELPER + ".writeI16(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.I32.equals(fieldType)) {
			buf.append(space).append(Constants.SET_HELPER + ".writeI32(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.I64.equals(fieldType)) {
			buf.append(space).append(Constants.SET_HELPER + ".writeI64(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.DOUBLE.equals(fieldType)) {
			buf.append(space).append(Constants.SET_HELPER + ".writeDouble(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.STRING.equals(fieldType)) {
			buf.append(space).append(Constants.SET_HELPER + ".writeString(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.DATE.equals(fieldType)) {
			buf.append(space).append(Constants.SET_HELPER + ".writeDate(oprot, " + param + ");").append("\r\n");
		} else {
			throw new IllegalStateException("类型" + fieldType + "非法!");
		}
		return buf.toString();
	}

	protected String writeBasic(String space, String fieldType, String param) {
		StringBuffer buf = new StringBuffer();
		// 简单类型直接调用帮助类进行输出
		if (SupportTypes.BOOL.equals(fieldType)) {
			buf.append(space).append(Constants.BASIC_HELPER + ".writeBool(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.BYTE.equals(fieldType)) {
			buf.append(space).append(Constants.BASIC_HELPER + ".writeByte(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.I16.equals(fieldType)) {
			buf.append(space).append(Constants.BASIC_HELPER + ".writeI16(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.I32.equals(fieldType)) {
			buf.append(space).append(Constants.BASIC_HELPER + ".writeI32(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.I64.equals(fieldType)) {
			buf.append(space).append(Constants.BASIC_HELPER + ".writeI64(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.DOUBLE.equals(fieldType)) {
			buf.append(space).append(Constants.BASIC_HELPER + ".writeDouble(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.STRING.equals(fieldType)) {
			buf.append(space).append(Constants.BASIC_HELPER + ".writeString(oprot, " + param + ");").append("\r\n");
		} else if (SupportTypes.DATE.equals(fieldType)) {
			buf.append(space).append(Constants.BASIC_HELPER + ".writeDate(oprot, " + param + ");").append("\r\n");
		} else {
			throw new IllegalStateException("类型" + fieldType + "非法!");
		}
		return buf.toString();
	}

	private String resoleMapItemNull(String type, String space) {
		StringBuffer buf = new StringBuffer();
		if (isBasicType(type)) {
			if (SupportTypes.BOOL.equals(type.trim())) {

			}
			return buf.toString();
		}
		if (ThriftTypeDetermine.isListType(type)) {
			Pattern pattern = Pattern.compile("^list *<(.*)>$");
			Matcher matcher = pattern.matcher(type);
			matcher.find();
			String innerType = matcher.group(1).trim();
			buf.append(formatWriteListBegin(space, ThriftTypeDetermine.resolveType(innerType), "-1"));
			buf.append(formatWriteListEnd(space));
			return buf.toString();
		} else if (ThriftTypeDetermine.isSetType(type)) {
			Pattern pattern = Pattern.compile("^set *<(.*)>$");
			Matcher matcher = pattern.matcher(type);
			matcher.find();
			String innerType = matcher.group(1).trim();
			buf.append(formatWriteListBegin(space, ThriftTypeDetermine.resolveType(innerType), "-1"));
			buf.append(formatWriteListEnd(space));
			return buf.toString();
		} else if (ThriftTypeDetermine.isMapType(type)) {
			Pattern pattern = Pattern.compile("^map *<(.*)>$");
			Matcher matcher = pattern.matcher(type);
			matcher.find();
			String innerType = matcher.group(1).trim();
			ItemPair<String, String> kv = ThriftType2JavaType.resolveKV(innerType);
			String keyType = ThriftTypeDetermine.resolveType(kv.getKey());
			String valueType = ThriftTypeDetermine.resolveType(kv.getValue());
			buf.append(formatWriteMapBegin(space, keyType, valueType, "-1"));
			buf.append(formatWriteMapEnd(space));
		} else {
			// 写入NULL的Dto
			String formatEmptyDto = "" + BasicHelper.class.getName() + ".writeNullObject(oprot);\r\n";
			buf.append(space).append(formatEmptyDto);
		}
		return buf.toString();
	}

	private String readBasicSet(String innerType, String retType, String retName, String space) {
		StringBuffer buf = new StringBuffer();
		// 简单类型直接调用帮助类进行输出
		if (SupportTypes.BOOL.equals(innerType)) {
			buf.append(space).append(retType + " " + retName + " = ").append(Constants.SET_HELPER + ".readBool(iprot);")
					.append("\r\n");
		} else if (SupportTypes.BYTE.equals(innerType)) {
			buf.append(space).append(retType + " " + retName + " = ").append(Constants.SET_HELPER + ".readByte(iprot);")
					.append("\r\n");
		} else if (SupportTypes.I16.equals(innerType)) {
			buf.append(space).append(retType + " " + retName + " = ").append(Constants.SET_HELPER + ".readI16(iprot);")
					.append("\r\n");
		} else if (SupportTypes.I32.equals(innerType)) {
			buf.append(space).append(retType + " " + retName + " = ").append(Constants.SET_HELPER + ".readI32(iprot);")
					.append("\r\n");
		} else if (SupportTypes.I64.equals(innerType)) {
			buf.append(space).append(retType + " " + retName + " = ").append(Constants.SET_HELPER + ".readI64(iprot);")
					.append("\r\n");
		} else if (SupportTypes.DOUBLE.equals(innerType)) {
			buf.append(space).append(retType + " " + retName + " = ")
					.append(Constants.SET_HELPER + ".readDouble(iprot);").append("\r\n");
		} else if (SupportTypes.STRING.equals(innerType)) {
			buf.append(space).append(retType + " " + retName + " = ")
					.append(Constants.SET_HELPER + ".readString(iprot);").append("\r\n");
		} else if (SupportTypes.DATE.equals(innerType)) {
			buf.append(space).append(retType + " " + retName + " = ").append(Constants.SET_HELPER + ".readDate(iprot);")
					.append("\r\n");
		}
		return buf.toString();
	}

	public String readBasicMap(String innerType, String retName, String space) {
		String ReadBasicMap = "#{0}#{1} #{2} = " + Constants.BASIC_HELPER + ".#{3}(iprot);\r\n";
		// 简单类型直接调用帮助类进行输出
		if (SupportTypes.BOOL.equals(innerType)) {
			return ReadBasicMap.replace("#{0}", space).replace("#{1}", "boolean").replace("#{2}", retName)
					.replace("#{3}", "readBool");
		} else if (SupportTypes.BYTE.equals(innerType)) {
			return ReadBasicMap.replace("#{0}", space).replace("#{1}", "byte").replace("#{2}", retName).replace("#{3}",
					"readByte");
		} else if (SupportTypes.I16.equals(innerType)) {
			return ReadBasicMap.replace("#{0}", space).replace("#{1}", "short").replace("#{2}", retName).replace("#{3}",
					"readI16");
		} else if (SupportTypes.I32.equals(innerType)) {
			return ReadBasicMap.replace("#{0}", space).replace("#{1}", "int").replace("#{2}", retName).replace("#{3}",
					"readI32");
		} else if (SupportTypes.I64.equals(innerType)) {
			return ReadBasicMap.replace("#{0}", space).replace("#{1}", "long").replace("#{2}", retName).replace("#{3}",
					"readI64");
		} else if (SupportTypes.DOUBLE.equals(innerType)) {
			return ReadBasicMap.replace("#{0}", space).replace("#{1}", "double").replace("#{2}", retName)
					.replace("#{3}", "readDouble");
		} else if (SupportTypes.STRING.equals(innerType)) {
			return ReadBasicMap.replace("#{0}", space).replace("#{1}", "String").replace("#{2}", retName)
					.replace("#{3}", "readString");
		} else if (SupportTypes.DATE.equals(innerType)) {
			return ReadBasicMap.replace("#{0}", space).replace("#{1}", "Date").replace("#{2}", retName).replace("#{3}",
					"readDate");
		} else {
			// 抛出异常
			throw new IllegalStateException("generic type:" + innerType + " is invalid!");
		}
	}

	private String doGenerateSingleDto(String innerType, String param, ThriftObject thriftObj, String space) {
		StringBuffer buf = new StringBuffer();
		String thriftImport = ThriftTypeUtil.getClassPath(innerType, thriftObj);
		if (!StringUtils.isEmpty(thriftImport) && thriftImport.contains(".")) {
			thriftObj.addImport(thriftImport);
		}
		String codecClass = ThriftTypeUtil.getDtoCodecPath(thriftImport);
		buf.append(formatDtoEncode(space + "\t", codecClass, param));
		return buf.toString();
	}

	private String doDecodeDto(String innerType, String retName, ThriftObject thriftObj, String space) {
		String thriftImport = ThriftTypeUtil.getClassPath(innerType, thriftObj);
		if (!StringUtils.isEmpty(thriftImport) && thriftImport.contains(".")) {
			thriftObj.addImport(thriftImport);
		}
		String codecClass = ThriftTypeUtil.getDtoCodecPath(thriftImport);
		return formatDtoDecode(space + "\t", innerType, codecClass, retName);
	}
}
