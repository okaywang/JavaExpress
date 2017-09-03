package com.zhaopin.thrift.rpc.common;

public class TMessageType {

	public static final byte CALL = 1;

	public static final byte REPLY = 2;

	public static final byte EXCEPTION = 3;

	public static final byte ONEWAY = 4;

	public static final byte PUSH = 5;
	
	public static final byte RUNTIME_EXCEPTION = EXCEPTION + 80;

}
