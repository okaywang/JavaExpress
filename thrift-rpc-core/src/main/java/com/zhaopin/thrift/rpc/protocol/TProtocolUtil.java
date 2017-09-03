package com.zhaopin.thrift.rpc.protocol;

import com.zhaopin.thrift.rpc.common.TField;
import com.zhaopin.thrift.rpc.common.TList;
import com.zhaopin.thrift.rpc.common.TMap;
import com.zhaopin.thrift.rpc.common.TSet;
import com.zhaopin.thrift.rpc.common.TType;
import com.zhaopin.thrift.rpc.exception.RpcException;

public class TProtocolUtil {

	private static int maxSkipDepth = Integer.MAX_VALUE;

	public static void setMaxSkipDepth(int depth) {
		maxSkipDepth = depth;
	}

	public static void skip(TProtocol prot, byte type) {
		skip(prot, type, maxSkipDepth);
	}

	public static void skip(TProtocol prot, byte type, int maxDepth) {
		if (maxDepth <= 0) {
			throw new RpcException("Maximum skip depth exceeded");
		}
		switch (type) {
		case TType.BOOL:
			prot.readBool();
			break;
		case TType.BYTE:
			prot.readByte();
			break;
		case TType.I16:
			prot.readI16();
			break;
		case TType.I32:
			prot.readI32();
			break;
		case TType.I64:
			prot.readI64();
			break;
		case TType.DOUBLE:
			prot.readDouble();
			break;
		case TType.DATE:
			prot.readI64();
			break;
		case TType.STRING:
			prot.readBinary();
			break;
		case TType.STRUCT:
			prot.readStructBegin();
			while (true) {
				TField field = prot.readFieldBegin();
				if (field.type == TType.STOP) {
					break;
				}
				skip(prot, field.type, maxDepth - 1);
				prot.readFieldEnd();
			}
			prot.readStructEnd();
			break;
		case TType.MAP:
			TMap map = prot.readMapBegin();
			for (int i = 0; i < map.getSize(); i++) {
				skip(prot, map.getKeyType(), maxDepth - 1);
				skip(prot, map.getValueType(), maxDepth - 1);
			}
			prot.readMapEnd();
			break;
		case TType.SET:
			TSet set = prot.readSetBegin();
			for (int i = 0; i < set.getSize(); i++) {
				skip(prot, set.getElemType(), maxDepth - 1);
			}
			prot.readSetEnd();
			break;
		case TType.LIST:
			TList list = prot.readListBegin();
			for (int i = 0; i < list.getSize(); i++) {
				skip(prot, list.getElemType(), maxDepth - 1);
			}
			prot.readListEnd();
			break;
		default:
			break;
		}
	}

}
