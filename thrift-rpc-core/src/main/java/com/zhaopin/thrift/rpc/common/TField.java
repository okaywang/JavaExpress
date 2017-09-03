package com.zhaopin.thrift.rpc.common;

public class TField {
	// Ãû×Ö
	public final String name;
	// ÀàÐÍ
	public final byte type;
	// ±àºÅ
	public final short id;

	public TField() {
		this("", TType.STOP, (short) 0);
	}

	public TField(String name, byte type, short id) {
		this.name = name;
		this.type = type;
		this.id = id;
	}

	public String toString() {
		return "{\"name\":\"" + name + "\", \"type\":" + type + ", \"id\":" + id + "}";
	}

	public boolean equals(TField other) {
		return this.type == other.type && this.id == other.id;
	}

}