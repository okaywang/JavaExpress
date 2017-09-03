package com.zhaopin.thrift.rpc.common;

public class TMap {
	// key������
	private final byte keyType;
	// value������
	private final byte valueType;
	// ��С
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
