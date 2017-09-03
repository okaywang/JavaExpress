package com.zhaopin.thrift.rpc.checker;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.zhaopin.rpc.annotation.ThriftField;
import com.zhaopin.rpc.annotation.ThriftService;
import com.zhaopin.rpc.annotation.ThriftStruct;
import com.zhaopin.thrift.rpc.common.ThriftStructField;
import com.zhaopin.thrift.rpc.util.ThriftCodecUtil;

/**
 * ���struct���ɵ�codec���Ƿ���ȷ
 *
 */
public class ThriftStructChecker {

	private static String REASON_NO_DEF = "û��#{0}�Ķ���,����thrift_compile������б���!";

	private static String REASON_NOT_SER = "#{0}û��ʵ��Serializable�ӿ�,��ʵ��Serializable�ӿ�!";

	private static String REASON_MISMATCH_PROP = "#{0}���е�#{1}�������ֲ�ƥ��,����thrift_compile������б���!";

	private static String REASON_MISMATCH_TYPE = "#{0}���е�#{1}�������Ͳ�ƥ��,����thrift_compile������б���!";

	private static String REASON_NO_PROP = "#{0}���е�û��#{1}����,����thrift_compile������б���!";

	public boolean check(String basePkg) {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(ThriftStruct.class));
		provider.addIncludeFilter(new AnnotationTypeFilter(ThriftService.class));
		Set<BeanDefinition> candidates = provider.findCandidateComponents(basePkg);
		for (BeanDefinition candidate : candidates) {
			String beanName = candidate.getBeanClassName(), codecName = null;
			try {
				Class<?> thriftClass = Thread.currentThread().getContextClassLoader().loadClass(beanName);
				if (thriftClass.getAnnotation(ThriftStruct.class) != null) {
					checkStruct(beanName, thriftClass);
				}
			} catch (IllegalStateException exp) {
				throw exp;
			} catch (Exception exp) {
				throw new IllegalStateException(REASON_NO_DEF.replace("#{0}", codecName));
			}
		}
		return true;
	}

	private void checkStruct(String beanName, Class<?> thriftClass) throws Exception {
		String codecName = null;
		try {
			// ��ȡbean��codec��
			codecName = ThriftCodecUtil.getStructCodecName(beanName);
			Class<?> codecClass = Thread.currentThread().getContextClassLoader().loadClass(codecName);
			if (!checkIfImplSerializable(thriftClass)) {
				throw new IllegalStateException(REASON_NOT_SER.replace("#{0}", thriftClass.getName()));
			}
			IStructFields structField = (IStructFields) codecClass.newInstance();
			Map<Integer, ThriftStructField> fields = structField.getFields();
			checkFields(thriftClass, fields);
		} catch (ClassNotFoundException exp) {
			throw new IllegalStateException(REASON_NO_DEF.replace("#{0}", codecName));
		}
	}

	public boolean checkIfImplSerializable(Class<?> structClass) {
		if (!Serializable.class.isAssignableFrom(structClass)) {
			return false;
		}
		return true;
	}

	public boolean checkFields(Class<?> struct, Map<Integer, ThriftStructField> structCodecFields) {
		Map<Integer, ThriftStructField> fields = resolve(struct);
		if (fields.size() != structCodecFields.size()) {
			throw new IllegalStateException("������gradle����thrift_compile���б���!");
		}
		String className = struct.getName();
		for (Entry<Integer, ThriftStructField> entry : fields.entrySet()) {
			int fieldId = entry.getKey();
			ThriftStructField structField = entry.getValue();
			ThriftStructField expect = structCodecFields.get(fieldId);
			if (expect == null) {
				throw new IllegalStateException(
						REASON_NO_PROP.replace("#{0}", className).replace("#{1}", entry.getValue().getFieldName()));
			}
			if (!structField.getFieldName().equals(expect.getFieldName())) {
				throw new IllegalStateException(REASON_MISMATCH_PROP.replace("#{0}", className).replace("#{1}",
						entry.getValue().getFieldName()));
			}
			if (!structField.getFieldType().equals(expect.getFieldType())) {
				throw new IllegalStateException(
						REASON_MISMATCH_TYPE.replace("#{0}", className).replace("#{1}", structField.getFieldName()));
			}
		}
		return true;
	}

	/**
	 * ����ע��ThriftStruct�����Ƿ���ȷ�Ľ���
	 * 
	 * @param struct
	 *            ��ע��ThriftStruct����
	 * @return
	 */
	public Map<Integer, ThriftStructField> resolve(Class<?> struct) {
		Map<Integer, ThriftStructField> structFields = new HashMap<Integer, ThriftStructField>();
		Field[] fields = struct.getDeclaredFields();
		// �����ж�field�Ƿ����ThriftFieldע��
		for (Field field : fields) {
			ThriftField thriftField = field.getAnnotation(ThriftField.class);
			if (thriftField == null) {
				continue;
			}
			String fieldType = getFieldType(field);
			int fieldIndex = thriftField.value();
			String fieldName = field.getName();
			ThriftStructField ThriftStructField = new ThriftStructField(fieldIndex, fieldType, fieldName);
			ThriftStructField rawField = structFields.put(fieldIndex, ThriftStructField);
			if (rawField != null) {
				throw new IllegalStateException(struct.getName() + "�����ظ������" + fieldIndex + "!");
			}
		}
		return structFields;
	}

	/**
	 * ��ȡ����ֶ���thrift��ʾ���ַ���
	 * 
	 * @param field
	 *            ����ֶ�
	 * @param thriftField
	 *            ����ֶε�ע��
	 * @return
	 */
	private String getFieldType(Field field) {
		Type type = field.getGenericType();
		if (type instanceof ParameterizedType) {
			Type rawType = ((ParameterizedType) type).getRawType();
			Type[] actualType = ((ParameterizedType) type).getActualTypeArguments();
			if (((Class<?>) rawType).isAssignableFrom(List.class)) {
				String innerType = doGetFieldType(field, actualType[0]);
				return "list<" + innerType + ">";
			} else if (((Class<?>) rawType).isAssignableFrom(Set.class)) {
				String innerType = doGetFieldType(field, actualType[0]);
				return "set<" + innerType + ">";
			} else if (((Class<?>) rawType).isAssignableFrom(Map.class)) {
				String leftType = doGetFieldType(field, actualType[0]);
				String rightType = doGetFieldType(field, actualType[1]);
				return "map<" + leftType + "," + rightType + ">";
			} else {
				throw new IllegalArgumentException("type \"" + type + "\" is invalid!");
			}
		} else {
			return doGetFieldType(field, type);
		}
	}

	private String doGetFieldType(final Field field, Type type) {
		if (type instanceof Class<?>) {
			Class<?> classType = (Class<?>) type;
			if (classType.isAssignableFrom(Boolean.class) || classType.isAssignableFrom(boolean.class)) {
				return "bool";
			} else if (classType.isAssignableFrom(Byte.class) || classType.isAssignableFrom(byte.class)) {
				return "byte";
			} else if (classType.isAssignableFrom(Short.class) || classType.isAssignableFrom(short.class)) {
				return "i16";
			} else if (classType.isAssignableFrom(Integer.class) || classType.isAssignableFrom(int.class)) {
				return "i32";
			} else if (classType.isAssignableFrom(Long.class) || classType.isAssignableFrom(long.class)) {
				return "i64";
			} else if (classType.isAssignableFrom(Double.class) || classType.isAssignableFrom(double.class)) {
				return "double";
			} else if (classType.isAssignableFrom(java.util.Date.class)
					|| classType.isAssignableFrom(java.sql.Date.class)) {
				return "date";
			} else if (classType.isAssignableFrom(String.class)) {
				return "string";
			} else {
				return classType.getSimpleName();
			}
		} else {
			Type rawType = ((ParameterizedType) type).getRawType();
			Type[] actualType = ((ParameterizedType) type).getActualTypeArguments();
			if (((Class<?>) rawType).isAssignableFrom(List.class)) {
				String innerType = doGetFieldType(field, actualType[0]);
				return "list<" + innerType + ">";
			} else if (((Class<?>) rawType).isAssignableFrom(Set.class)) {
				String innerType = doGetFieldType(field, actualType[0]);
				return "set<" + innerType + ">";
			} else if (((Class<?>) rawType).isAssignableFrom(Map.class)) {
				String leftType = doGetFieldType(field, actualType[0]);
				String rightType = doGetFieldType(field, actualType[1]);
				return "map<" + leftType + "," + rightType + ">";
			} else {
				throw new IllegalArgumentException("field \"" + field + "\" is invalid!");
			}
		}
	}
}
