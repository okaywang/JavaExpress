package com.zhaopin.thrift.rpc.common;

public final class TSet {

	private final byte elemType;

	private final int size;

	public TSet() {
		this(TType.VOID, 0);
	}

	public TSet(byte elemType, int size) {
		this.elemType = elemType;
		this.size = size;
	}

	public byte getElemType() {
		return elemType;
	}

	public int getSize() {
		return size;
	}

}
