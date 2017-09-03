package com.zhaopin.thrift.rpc.common;

public class ThriftStructField {
	// ×Ö¶ÎÐòºÅ
	private final int fieldIndex;
	// ×Ö¶ÎÀàÐÍ
	private final String fieldType;
	// ×Ö¶ÎÃû×Ö
	private final String fieldName;

	public ThriftStructField(int fieldIndex, String fieldType, String fieldName) {
		this.fieldIndex = fieldIndex;
		this.fieldType = fieldType;
		this.fieldName = fieldName;
	}

	public int getFieldIndex() {
		return fieldIndex;
	}

	public String getFieldType() {
		return fieldType;
	}

	public String getFieldName() {
		return fieldName;
	}

	@Override
	public String toString() {
		return "ThriftStructField[index=" + fieldIndex + ",fieldType=" + fieldType + ",fieldName=" + fieldName + "]";
	}
}
