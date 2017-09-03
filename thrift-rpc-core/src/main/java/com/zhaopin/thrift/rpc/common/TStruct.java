package com.zhaopin.thrift.rpc.common;

public class TStruct {
	
	private final String name;
	
	public TStruct() {
		this("");
	}
	
	public TStruct(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
