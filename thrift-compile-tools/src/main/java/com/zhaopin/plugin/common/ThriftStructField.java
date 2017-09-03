package com.zhaopin.plugin.common;

import com.zhaopin.plugin.Constants;

public class ThriftStructField implements Comparable<ThriftStructField> {
	// ×Ö¶ÎÐòºÅ
	private int fieldIndex;
	// ×Ö¶ÎÀàÐÍ
	private String fieldType;
	// ×Ö¶ÎÃû×Ö
	private String fieldName;
	// ÊÇ·ñ±ØÐë
	private boolean required;
	// Ä¬ÈÏÖµ
	private String defaultValue;
	// ×Ö¶ÎµÄ×¢ÊÍ
	private String comment;

	public int getFieldIndex() {
		return fieldIndex;
	}

	public void setFieldIndex(int fieldIndex) {
		this.fieldIndex = fieldIndex;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		String thriftType = Constants.TYPE_MAPPING.get(fieldType);
		if (thriftType != null) {
			this.fieldType = thriftType;
		} else {
			this.fieldType = fieldType;
		}
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ThriftStructField) {
			ThriftStructField field = (ThriftStructField) obj;
			if (getFieldIndex() == field.getFieldIndex()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int compareTo(ThriftStructField other) {
		int value = getFieldIndex() - other.getFieldIndex();
		if (value == 0) {
			return 0;
		}
		return value > 0 ? 1 : -1;
	}
}
