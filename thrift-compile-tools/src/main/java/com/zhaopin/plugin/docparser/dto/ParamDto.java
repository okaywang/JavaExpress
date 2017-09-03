package com.zhaopin.plugin.docparser.dto;

import java.util.List;

public class ParamDto {

	// 中文名称
	private String name;
	// 字段名称
	private String identifier;
	// 是否必须:0-非必须,1-必须
	private short isrequired = 1;
	// 是否验证:0-不验证,1-验证
	private short isvalidator;
	// 20-入参，30-出参
	private short type;
	// 数据类型 (string,int,map,list,object,bool)
	private String dataType;
	// 备注
	private String remark;
	// 默认值
	private String defaultValue;
	// 参数类型是map,list,object
	private List<ParamDto> struct;
	// 是否是集合(list,map,set)
	private boolean isCollection;
	// 集合类型(list,map,set)
	private String collectionType;
	// map 的key
	private String key;

	public enum RequiredEnum {
		Required((short) 1), OPTIONAL((short) 0);
		private short value;

		private RequiredEnum(short value) {
			this.value = value;
		}

		public short getValue() {
			return this.value;
		}
	}

	public enum ValidatorEenum {
		Required((short) 0), OPTIONAL((short) 1);
		private short value;

		private ValidatorEenum(short value) {
			this.value = value;
		}

		public short getValue() {
			return this.value;
		}
	}

	public enum TypeEnum {
		REQUEST((short) 20), RESPONSE((short) 30);
		private short value;

		private TypeEnum(short value) {
			this.value = value;
		}

		public short getValue() {
			return this.value;
		}
	}

	public enum DataTypeEnum {
		STRING("string"), SHORT("i16"), INT("i32"), LONG("i64"), MAP("map"), LIST("list"), OBJECT("object"), BOOL(
				"bool"), BYTE("byte"), DOUBLE("double"), DATE("date");
		private String value;

		private DataTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public List<ParamDto> getStruct() {
		return struct;
	}

	public void setStruct(List<ParamDto> struct) {
		this.struct = struct;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Short getIsrequired() {
		return isrequired;
	}

	public void setIsrequired(Short isrequired) {
		this.isrequired = isrequired;
	}

	public Short getIsvalidator() {
		return isvalidator;
	}

	public void setIsvalidator(Short isvalidator) {
		this.isvalidator = isvalidator;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isCollection() {
		return isCollection;
	}

	public void setCollection(boolean isCollection) {
		this.isCollection = isCollection;
	}

	public String getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
