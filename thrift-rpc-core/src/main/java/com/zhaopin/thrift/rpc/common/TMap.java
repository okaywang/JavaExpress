package com.zhaopin.thrift.rpc.common;

public class TMap {
	// key的类型
	private final byte keyType;
	// value的类型
	private final byte valueType;
	// 大小
	private final int size;

	public TMap() {
		this(TType.STOP, TType.STOP, 0);
	}

	public TMap(byte keyType, byte valueType, int size) {
		this.keyType = keyType;
		this.valueType = valueType;
		this.size = size;
	}

	public byte getKeyType() {
		return keyType;
	}

	public byte getValueType() {
		return valueType;
	}

	public int getSize() {
		return size;
	}
}
