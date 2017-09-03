package com.zhaopin.plugin.doc;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.zhaopin.rpc.annotation.ThriftField;
import com.zhaopin.rpc.annotation.ThriftInterface;
import com.zhaopin.rpc.annotation.ThriftStruct;
import com.zhaopin.thrift.tool.finder.ThriftComponentProvider;

/**
 * ����IDL����
 *
 */
public class IdlGenerate {

	/**
	 * arg[0] ɨ��İ�·�� arg[1] ����Ŀ��·��
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args != null && args.length > 0 && !StringUtils.isEmpty(args[0])) {
			new IdlGenerate(args[1]).generate(args[0]);
		} else {
			new IdlGenerate(args[1]).generate("com.zhaopin");
		}
	}

	private final String projectRoot;

	public IdlGenerate(String projectRoot) {
		this.projectRoot = projectRoot;
	}

	/**
	 * ɨ��������Ŀ�Ľṹ����thrift��struct��service��������idl
	 * 
	 * @param basePkg
	 */
	public void generate(String basePkg) {
		ThriftComponentProvider provider = new ThriftComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(ThriftStruct.class));
		provider.addIncludeFilter(new AnnotationTypeFilter(ThriftInterface.class));
		Set<BeanDefinition> candidates = provider.findCandidateComponents(basePkg);
		// �����еı�ѡ�е�bean���д���
		// ��Ҫ�ж���������Ŀ�ж���Ļ���jar�и����õ�
		// �����jar�����õģ���Ҫ����Դ�
		Map<String, Set<Class<?>>> sortBeans = new HashMap<String, Set<Class<?>>>();
		for (BeanDefinition candidate : candidates) {
			String beanName = candidate.getBeanClassName();
			Class<?> cls = null;
			try {
				cls = Thread.currentThread().getContextClassLoader().loadClass(beanName);
			} catch (Exception exp) {
				// ���ﲻ������쳣
				exp.printStackTrace();
			}
			if (cls.getAnnotation(ThriftInterface.class) != null) {
				Set<Class<?>> beans = sortBeans.get(cls.getPackage().getName());
				if (beans == null) {
					beans = new HashSet<Class<?>>();
					sortBeans.put(cls.getPackage().getName(), beans);
				}
				beans.add(cls);
			} else if (cls.getAnnotation(ThriftStruct.class) != null) {
				Set<Class<?>> beans = sortBeans.get(cls.getPackage().getName());
				if (beans == null) {
					beans = new HashSet<Class<?>>();
					sortBeans.put(cls.getPackage().getName(), beans);
				}
				beans.add(cls);
			}
		}
		File dir = new File(this.projectRoot + "/build/thrift/IDL");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		Collection<File> files = FileUtils.listFiles(dir, new String[] { "thrift" }, false);
		for (File file : files) {
			FileUtils.deleteQuietly(file);
		}
		for (Entry<String, Set<Class<?>>> entry : sortBeans.entrySet()) {
			parse(entry.getKey(), entry.getValue(), provider);
		}
		files = FileUtils.listFiles(dir, new String[] { "thrift" }, false);
		// ����main
		StringBuffer define = new StringBuffer();
		for (File file : files) {
			define.append("include \"" + file.getName() + "\"\r\n");
		}
		define.append("// thrift.exe -r -gen java main.thrift\r\n");
		define.append("// thrift.exe -r -gen js:node main.thrift");
		File main = new File(dir, "main.thrift");
		try {
			FileUtils.writeStringToFile(main, define.toString(), "utf-8");
		} catch (Exception exp) {
			throw new IllegalStateException("thrift idlд���ļ��쳣", exp);
		}
		File dependFile = new File(dir, "depend.json");
		try {
			FileUtils.writeStringToFile(dependFile, JSON.toJSONString(provider.getDependsJars()), "utf-8");
		} catch (Exception exp) {
			throw new IllegalStateException("thrift idlд�������ļ��쳣", exp);
		}
	}

	private void parse(String pkg, Set<Class<?>> beans, ThriftComponentProvider provider) {
		StringBuffer define = new StringBuffer();
		StringBuffer localBuffer = new StringBuffer();
		StringBuffer refBuffer = new StringBuffer();
		define.append("namespace java " + pkg + "\r\n");
		define.append("namespace js " + pkg + "\r\n\r\n");
		Set<String> imports = new TreeSet<String>();
		for (Class<?> cls : beans) {
			String idlCont = "";
			if (cls.getAnnotation(ThriftInterface.class) != null) {
				idlCont = parseService(cls, imports);
			} else if (cls.getAnnotation(ThriftStruct.class) != null) {
				idlCont = parseStruct(cls, imports);
			}
			if (provider.isLocalClass(cls.getName())) {
				localBuffer.append(idlCont);
			} else {
				refBuffer.append(idlCont);
			}
		}
		for (String include : imports) {
			define.append("include \"" + include + ".thrift\"\r\n");
		}
		define.append(localBuffer);
		if (refBuffer.length() > 0) {
			// ��jar�еĺͱ��صķֿ�
			define.append("//---------------------------------------//");
			define.append(refBuffer);
		}
		File dir = new File(this.projectRoot + "/build/thrift/IDL");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, pkg.replace(".", "_") + ".thrift");
		try {
			FileUtils.writeStringToFile(file, define.toString(), "utf-8");
		} catch (Exception exp) {
			throw new IllegalStateException("thrift idlд���ļ��쳣" + pkg, exp);
		}
	}

	private String parseService(Class<?> service, Set<String> imports) {
		StringBuffer define = new StringBuffer();
		define.append("\r\n// " + service.getName() + "\r\n");
		define.append("service " + service.getSimpleName() + " { \r\n");
		Method[] methods = service.getDeclaredMethods();
		if (methods.length > 0) {
			for (int k = 0; k < methods.length; ++k) {
				Method method = methods[k];
				Type retType = method.getGenericReturnType();
				String strRet = executeType(service, retType, imports, method, null);
				define.append("\t" + strRet).append(" " + method.getName() + "(");
				Type[] params = method.getGenericParameterTypes();
				for (int t = 1; t <= params.length; ++t) {
					String strParam = executeType(service, params[t - 1], imports, method, null);
					if (t == 1) {
						define.append("" + t + ":" + strParam + " param" + t);
					} else {
						define.append(", " + t + ":" + strParam + " param" + t);
					}
				}
				define.append(")");
				if (k != methods.length - 1) {
					define.append(",\r\n");
				}
			}
		} else {
			define.append("\r\n");
		}
		define.append("\r\n}\r\n");
		return define.toString();
	}

	private String parseStruct(Class<?> struct, Set<String> imports) {
		if (struct.getSuperclass() != Object.class) {
			throw new IllegalStateException("��" + struct + "��ע��@ThriftStructע��,thrift��֧�ּ̳�!");
		}
		// ����field�а���ThriftFieldע��
		Field[] fields = struct.getDeclaredFields();
		StringBuffer define = new StringBuffer();
		define.append("\r\n// " + struct.getName() + "\r\n");
		define.append("struct " + struct.getSimpleName() + " { \r\n");
		Set<Integer> indices = new TreeSet<Integer>();
		List<Field> selectFields = new ArrayList<Field>();
		for (Field field : fields) {
			if (field.getAnnotation(ThriftField.class) != null) {
				int index = field.getAnnotation(ThriftField.class).value();
				if (indices.contains(index)) {
					throw new IllegalStateException("��" + struct + "����@ThriftField(" + index + ")�ظ����ֶ�!");
				}
				indices.add(index);
				selectFields.add(field);
			}
		}
		if (selectFields.size() > 0) {
			for (Field field : selectFields) {
				int index = field.getAnnotation(ThriftField.class).value();
				String result = executeType(struct, field.getGenericType(), imports, null, field);
				define.append("\t").append("" + index + ": " + result + " " + field.getName() + ",\r\n");
			}
			define.delete(define.length() - 3, define.length());
		}
		define.append("\r\n}\r\n");
		return define.toString();
	}

	private String executeType(final Class<?> clz, Type type, Set<String> imports, Method method, Field field) {
		if (type instanceof ParameterizedType) {
			Type rawType = ((ParameterizedType) type).getRawType();
			if (rawType instanceof Class<?>) {
				if (List.class.isAssignableFrom((Class<?>) rawType)) {
					Type[] actualType = ((ParameterizedType) type).getActualTypeArguments();
					return "list<" + executeType(clz, actualType[0], imports, method, field) + ">";
				} else if (Set.class.isAssignableFrom((Class<?>) rawType)) {
					Type[] actualType = ((ParameterizedType) type).getActualTypeArguments();
					return "set<" + executeType(clz, actualType[0], imports, method, field) + ">";
				} else if (Map.class.isAssignableFrom((Class<?>) rawType)) {
					Type[] actualType = ((ParameterizedType) type).getActualTypeArguments();
					return "map<" + executeType(clz, actualType[0], imports, method, field) + ", "
							+ executeType(clz, actualType[1], imports, method, field) + ">";
				} else {
					throw new IllegalStateException("��" + clz + "������" + field.getName() + "�����쳣!");
				}
			} else {
				if (method != null) {
					throw new IllegalStateException("��" + clz + "�ķ���" + method.getName() + "�쳣!");
				} else {
					throw new IllegalStateException("��" + clz + "������" + field.getName() + "�����쳣!");
				}

			}
		} else if (type instanceof Class<?>) {
			Class<?> fieldType = (Class<?>) type;
			if (fieldType == Boolean.class || fieldType == boolean.class) {
				return "bool";
			} else if (fieldType == Byte.class || fieldType == byte.class) {
				return "byte";
			} else if (fieldType == Short.class || fieldType == short.class) {
				return "i16";
			} else if (fieldType == Integer.class || fieldType == int.class) {
				return "i32";
			} else if (fieldType == Long.class || fieldType == long.class) {
				return "i64";
			} else if (fieldType == Double.class || fieldType == double.class) {
				return "double";
			} else if (fieldType == String.class) {
				return "string";
			} else if (fieldType.getAnnotation(ThriftStruct.class) != null) {
				// �����·����ͬ, ������
				if (!fieldType.getPackage().getName().equals(clz.getPackage().getName())) {
					imports.add(fieldType.getPackage().getName().replace(".", "_"));
				}
				return fieldType.getPackage().getName().replace(".", "_") + "." + fieldType.getSimpleName();
			} else {
				if (method != null) {
					throw new IllegalStateException("��" + clz + "�ķ���" + method.getName() + "�쳣!");
				} else {
					throw new IllegalStateException("��" + clz + "������" + field.getName() + "�����쳣!");
				}
			}
		} else {
			if (method != null) {
				throw new IllegalStateException("��" + clz + "�ķ���" + method.getName() + "�쳣!");
			} else {
				throw new IllegalStateException("��" + clz + "������" + field.getName() + "�����쳣!");
			}
		}
	}

}
