package com.zhaopin.plugin.parser;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import com.zhaopin.plugin.common.ThriftService;
import com.zhaopin.plugin.common.ThriftServiceFunc;
import com.zhaopin.plugin.common.ThriftServiceFuncParam;
import com.zhaopin.plugin.common.ThriftStruct;
import com.zhaopin.plugin.common.ThriftStructField;
import com.zhaopin.rpc.annotation.ThriftField;
import com.zhaopin.thrift.rpc.util.NumberUtil;

public abstract class AbstractThriftParser implements ThriftParser {
	// 源代码所在的路径
	private final File[] srcDir;

	private final File[] resDir;

	public AbstractThriftParser(String srcDir, String resDir) {
		this.srcDir = new File[] { new File(srcDir) };
		this.resDir = new File[] { new File(resDir) };
	}

	public AbstractThriftParser(File[] srcDir, File[] resDir) {
		this.srcDir = srcDir;
		this.resDir = resDir;
	}

	protected ThriftService doParseService(TypeDeclaration thriftObject, String pkgName, String className,
			Set<String> imports) {
		ThriftService thriftService = new ThriftService(pkgName, className);
		thriftService.getImports().addAll(imports);
		List<?> fields = thriftObject.bodyDeclarations();
		if (fields != null) {
			for (Object field : fields) {
				if (!(field instanceof MethodDeclaration)) {
					continue;
				}
				MethodDeclaration method = (MethodDeclaration) field;
				String methodName = method.getName().getFullyQualifiedName();
				ThriftServiceFunc thriftFunc = new ThriftServiceFunc(methodName);
				Type returnType = method.getReturnType2();
				String retType = resolveType(returnType);
				thriftFunc.setRetType(retType);
				Javadoc javadoc = method.getJavadoc();
				if (method.parameters() != null) {
					for (int t = 0; t < method.parameters().size(); ++t) {
						Object obj = method.parameters().get(t);
						if (obj instanceof SingleVariableDeclaration) {
							SingleVariableDeclaration parameter = (SingleVariableDeclaration) obj;
							ThriftServiceFuncParam funcParam = new ThriftServiceFuncParam();
							funcParam.setParamName(parameter.getName().getFullyQualifiedName());
							funcParam.setRequire(required(parameter.modifiers()));
							String paramType = resolveType(parameter.getType());
							funcParam.setParamType(paramType);
							funcParam.setParamIndex(t + 1);
							List<?> exceptions = method.thrownExceptionTypes();
							if (exceptions != null) {
								for (Object exception : exceptions) {
									if (exception instanceof SimpleType) {
										thriftFunc.addException(this.resolveType((SimpleType) exception));
									}
								}
							}
							funcParam.setComment(resovleParamComment(javadoc, funcParam.getParamName()));
							thriftFunc.addFuncParam(funcParam);
						}
					}
				}
				thriftFunc.setComment(resovleComment(javadoc));
				if (!thriftService.addServiceFunc(thriftFunc)) {
					throw new IllegalStateException(
							pkgName + "." + className + "含有相同名称" + thriftFunc.getFuncName() + "的方法");
				}
			}
		}
		return thriftService;
	}

	private String resovleParamComment(Javadoc javadoc, String paramName) {
		StringBuffer comment = new StringBuffer();
		if (javadoc != null) {
			for (Object obj : javadoc.tags()) {
				if (obj instanceof TagElement) {
					List<?> fragments = ((TagElement) obj).fragments();
					if (fragments != null && fragments.size() > 1 && fragments.get(0) instanceof SimpleName) {
						if (paramName.equals(((SimpleName) fragments.get(0)).getFullyQualifiedName())) {
							if (fragments.size() > 1 && fragments.get(1) instanceof TextElement) {
								comment.append(((TextElement) fragments.get(1)).getText());
								break;
							}
						}
					}
				}
			}
		}
		return comment.toString();
	}

	private String resovleComment(Javadoc javadoc) {
		StringBuffer comment = new StringBuffer();
		if (javadoc != null) {
			for (Object obj : javadoc.tags()) {
				if (obj instanceof TagElement) {
					List<?> fragments = ((TagElement) obj).fragments();
					if (fragments != null && fragments.size() == 1 && fragments.get(0) instanceof TextElement) {
						comment.append(((TextElement) fragments.get(0)).getText());
						break;
					}
				}
			}
		}
		return comment.toString();
	}

	protected ThriftStruct doParseStruct(TypeDeclaration thriftObject, String pkgName, String className,
			Set<String> imports, StructContext context) {
		ThriftStruct thriftStruct = new ThriftStruct(pkgName, className);
		thriftStruct.getImports().addAll(imports);
		FieldDeclaration[] fields = thriftObject.getFields();
		if (fields != null) {
			for (FieldDeclaration field : fields) {
				ThriftStructField structField = retriveProperty(field);
				if (structField != null) {
					boolean result = thriftStruct.addStructField(structField);
					String comment = getComment("@ThriftField(" + structField.getFieldIndex() + ")", context);
					structField.setComment(comment);
					if (!result) {
						throw new IllegalStateException(
								thriftStruct.getPkgName() + "." + thriftStruct.getClassName() + "包含相同序号的属性");
					}
				}
			}
		}
		return thriftStruct;
	}

	private String getComment(String tag, StructContext context) {
		for (int t = 0, len = context.contents.size(); t < len; ++t) {
			String line = context.contents.get(t);
			if (tag.equals(line.trim())) {
				// 开始在上下寻找注释
				StringBuffer comments = new StringBuffer();
				for (int k = t; k < len; ++k) {
					line = context.contents.get(k).trim();
					if (line.startsWith("//")) {
						// 是注释
						comments.append(line.substring(2)).append(" ");
					} else if (line.startsWith("@") || line.isEmpty()) {
						// 是注解
						continue;
					} else {
						break;
					}
				}
				for (int k = t; k >= 0; --k) {
					line = context.contents.get(k).trim();
					if (line.startsWith("//")) {
						// 是注释
						comments.append(line.substring(2)).append(" ");
					} else if (line.startsWith("@") || line.isEmpty()) {
						// 是注解
						continue;
					} else {
						break;
					}
				}
				return comments.toString();
			}
		}
		return "";
	}

	private ThriftStructField retriveProperty(FieldDeclaration field) {
		List<?> annotations = field.modifiers();
		if (annotations == null) {
			return null;
		}
		ThriftStructField structField = null;
		for (Object obj : annotations) {
			if (obj instanceof SingleMemberAnnotation) {
				SingleMemberAnnotation annotation = (SingleMemberAnnotation) obj;
				if (ThriftField.class.getName().equals(annotation.getTypeName().getFullyQualifiedName())
						|| ThriftField.class.getSimpleName().equals(annotation.getTypeName().getFullyQualifiedName())) {
					structField = new ThriftStructField();
					if (annotation.getValue() instanceof NumberLiteral) {
						String index = ((NumberLiteral) annotation.getValue()).getToken();
						structField.setFieldIndex(NumberUtil.getInt(index));
					}
					Type type = field.getType();
					if (type instanceof PrimitiveType) {
						PrimitiveType primitiveType = ((PrimitiveType) type);
						PrimitiveType.Code code = primitiveType.getPrimitiveTypeCode();
						structField.setFieldType(code.toString());
						structField.setRequired(required(annotations));
					} else if (type instanceof SimpleType) {
						SimpleType simpleType = (SimpleType) type;
						structField.setFieldType(simpleType.getName().getFullyQualifiedName());
						structField.setRequired(required(annotations));
					} else if (type instanceof ParameterizedType) {
						ParameterizedType primitiveType = ((ParameterizedType) type);
						structField.setFieldType(resolveParameterizedType(primitiveType));
						structField.setRequired(required(annotations));
					} else {
						throw new IllegalStateException(field + " 非法!");
					}
					List<?> fragments = field.fragments();
					VariableDeclarationFragment fragment = (VariableDeclarationFragment) fragments.get(0);
					structField.setFieldName(fragment.getName().getFullyQualifiedName());
					break;
				}
			}
		}
		return structField;
	}

	private enum ContainerType {
		List, Set, Map, None
	}

	private String resolveType(Type type) {
		if (type instanceof PrimitiveType) {
			return transformPrivateType((PrimitiveType) type);
		} else if (type instanceof SimpleType) {
			if (isWraperType((SimpleType) type)) {
				return transformWraperType((SimpleType) type);
			} else {
				return ((SimpleType) type).getName().getFullyQualifiedName();
			}
		} else if (type instanceof ParameterizedType) {
			return resolveParameterizedType((ParameterizedType) type);
		} else {
			throw new IllegalStateException(type + " 非法!");
		}
	}

	private String resolveParameterizedType(ParameterizedType primitiveType) {
		Type basicType = primitiveType.getType();
		List<?> arguments = primitiveType.typeArguments();
		ContainerType containerType = ContainerType.None;
		if (basicType instanceof SimpleType) {
			SimpleType simpleType = (SimpleType) basicType;
			String typeName = simpleType.getName().getFullyQualifiedName();
			if (List.class.getName().equals(typeName) || List.class.getSimpleName().equals(typeName)) {
				containerType = ContainerType.List;
			} else if (Set.class.getName().equals(typeName) || Set.class.getSimpleName().equals(typeName)) {
				containerType = ContainerType.Set;
			} else if (Map.class.getName().equals(typeName) || Map.class.getSimpleName().equals(typeName)) {
				containerType = ContainerType.Map;
			}
		}
		if (containerType == ContainerType.None) {
			throw new IllegalStateException("invalid type " + primitiveType.toString());
		}
		if (containerType == ContainerType.List) {
			Object obj = arguments.get(0);
			if (obj instanceof PrimitiveType) {
				return "list<" + transformPrivateType((PrimitiveType) obj) + ">";
			} else if (obj instanceof SimpleType) {
				if (isWraperType((SimpleType) obj)) {
					return "list<" + transformWraperType((SimpleType) obj) + ">";
				}
				return "list<" + ((SimpleType) obj).getName().getFullyQualifiedName() + ">";
			} else if (obj instanceof ParameterizedType) {
				return "list<" + resolveParameterizedType((ParameterizedType) obj) + ">";
			} else {
				throw new IllegalStateException("invalid type " + primitiveType.toString());
			}
		} else if (containerType == ContainerType.Set) {
			Object obj = arguments.get(0);
			if (obj instanceof PrimitiveType) {
				return "set<" + transformPrivateType((PrimitiveType) obj) + ">";
			} else if (obj instanceof SimpleType) {
				if (isWraperType((SimpleType) obj)) {
					return "set<" + transformWraperType((SimpleType) obj) + ">";
				}
				return "set<" + ((SimpleType) obj).getName().getFullyQualifiedName() + ">";
			} else if (obj instanceof ParameterizedType) {
				return "set<" + resolveParameterizedType((ParameterizedType) obj) + ">";
			} else {
				throw new IllegalStateException("invalid type " + primitiveType.toString());
			}
		} else if (containerType == ContainerType.Map) {
			Object keyObj = arguments.get(0), valueObj = arguments.get(1);
			String keyType = "", valueType = "";
			if (keyObj instanceof PrimitiveType) {
				keyType = transformPrivateType((PrimitiveType) keyObj);
			} else if (keyObj instanceof SimpleType) {
				if (isWraperType((SimpleType) keyObj)) {
					keyType = transformWraperType((SimpleType) keyObj);
				} else {
					keyType = ((SimpleType) keyObj).getName().getFullyQualifiedName();
				}
			} else if (keyObj instanceof ParameterizedType) {
				keyType = resolveParameterizedType((ParameterizedType) keyObj);
			} else {
				throw new IllegalStateException("invalid type " + primitiveType.toString());
			}
			if (valueObj instanceof PrimitiveType) {
				valueType = transformPrivateType((PrimitiveType) valueObj);
			} else if (valueObj instanceof SimpleType) {
				if (isWraperType((SimpleType) valueObj)) {
					valueType = transformWraperType((SimpleType) valueObj);
				} else {
					valueType = ((SimpleType) valueObj).getName().getFullyQualifiedName();
				}
			} else if (valueObj instanceof ParameterizedType) {
				valueType = resolveParameterizedType((ParameterizedType) valueObj);
			} else {
				throw new IllegalStateException("invalid type " + primitiveType.toString());
			}
			return "map<" + keyType + "," + valueType + ">";
		} else {
			throw new IllegalStateException("invalid type " + primitiveType.toString());
		}
	}

	private boolean isWraperType(SimpleType simpleType) {
		String name = simpleType.getName().getFullyQualifiedName();
		if (Boolean.class.getName().equals(name) || Boolean.class.getSimpleName().equals(name)) {
			return true;
		} else if (Byte.class.getName().equals(name) || Byte.class.getSimpleName().equals(name)) {
			return true;
		} else if (Short.class.getName().equals(name) || Short.class.getSimpleName().equals(name)) {
			return true;
		} else if (Integer.class.getName().equals(name) || Integer.class.getSimpleName().equals(name)) {
			return true;
		} else if (Long.class.getName().equals(name) || Long.class.getSimpleName().equals(name)) {
			return true;
		} else if (Double.class.getName().equals(name) || Double.class.getSimpleName().equals(name)) {
			return true;
		} else if (String.class.getName().equals(name) || String.class.getSimpleName().equals(name)) {
			return true;
		}
		return false;
	}

	private String transformWraperType(SimpleType simpleType) {
		String name = simpleType.getName().getFullyQualifiedName();
		if (Boolean.class.getName().equals(name) || Boolean.class.getSimpleName().equals(name)) {
			return "bool";
		} else if (Byte.class.getName().equals(name) || Byte.class.getSimpleName().equals(name)) {
			return "byte";
		} else if (Short.class.getName().equals(name) || Short.class.getSimpleName().equals(name)) {
			return "i16";
		} else if (Integer.class.getName().equals(name) || Integer.class.getSimpleName().equals(name)) {
			return "i32";
		} else if (Long.class.getName().equals(name) || Long.class.getSimpleName().equals(name)) {
			return "i64";
		} else if (Double.class.getName().equals(name) || Double.class.getSimpleName().equals(name)) {
			return "double";
		} else if (String.class.getName().equals(name) || String.class.getSimpleName().equals(name)) {
			return "string";
		} else {
			throw new IllegalArgumentException("invalid type" + simpleType);
		}
	}

	private String transformPrivateType(PrimitiveType primitiveType) {
		PrimitiveType.Code code = primitiveType.getPrimitiveTypeCode();
		if (code == PrimitiveType.BOOLEAN) {
			return "bool";
		} else if (code == PrimitiveType.BYTE) {
			return "byte";
		} else if (code == PrimitiveType.SHORT) {
			return "i16";
		} else if (code == PrimitiveType.INT) {
			return "i32";
		} else if (code == PrimitiveType.LONG) {
			return "i64";
		} else if (code == PrimitiveType.DOUBLE) {
			return "double";
		} else {
			throw new IllegalArgumentException("参数" + primitiveType + "异常!");
		}

	}

	private boolean required(List<?> annotations) {
		for (Object obj : annotations) {
			if (obj instanceof MarkerAnnotation) {
				MarkerAnnotation annotation = (MarkerAnnotation) obj;
				if (NotNull.class.getName().equals(annotation.getTypeName().getFullyQualifiedName())
						|| NotNull.class.getSimpleName().equals(annotation.getTypeName().getFullyQualifiedName())) {
					return true;
				}
			}
		}
		return false;
	}

	public File[] getSrcDir() {
		return srcDir;
	}

	public File[] getResDir() {
		return resDir;
	}

	protected static class StructContext {
		//
		public List<String> contents;

		public StructContext(List<String> contents) {
			this.contents = contents;
		}
	}
}
