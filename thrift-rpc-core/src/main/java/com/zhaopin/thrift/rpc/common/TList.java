package com.zhaopin.thrift.rpc.common;

public final class TList {

	private final byte elemType;

	private final int size;

	public TList() {
		this(TType.VOID, 0);
	}

	public TList(byte elemType, int size) {
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
