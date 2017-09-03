package com.zhaopin.thrift.rpc.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zhaopin.thrift.rpc.common.TList;
import com.zhaopin.thrift.rpc.common.TType;
import com.zhaopin.thrift.rpc.protocol.TProtocol;

public class ListHelper {

	public static void writeBool(TProtocol oprot, List<Boolean> list) {
		if (list == null) {
			oprot.writeListBegin(new TList(TType.BOOL, -1));
			oprot.writeListEnd();
		} else {
			oprot.writeListBegin(new TList(TType.BOOL, list.size()));
			for (Boolean b : list) {
				oprot.writeBool(b);
			}
			oprot.writeListEnd();
		}
	}

	public static List<Boolean> readBool(TProtocol iprot) {
		TList tlist = iprot.readListBegin();
		if (tlist.getSize() == -1) {
			iprot.readListEnd();
			return null;
		}
		List<Boolean> list = new ArrayList<Boolean>();
		for (int t = 0; t < tlist.getSize(); ++t) {
			list.add(iprot.readBool());
		}
		iprot.readListEnd();
		return list;
	}

	public static void writeByte(TProtocol oprot, List<Byte> list) {
		if (list == null) {
			oprot.writeListBegin(new TList(TType.BYTE, -1));
			oprot.writeListEnd();
		} else {
			oprot.writeListBegin(new TList(TType.BYTE, list.size()));
			for (Byte b : list) {
				oprot.writeByte(b);
			}
			oprot.writeListEnd();
		}
	}

	public static List<Byte> readByte(TProtocol iprot) {
		TList tlist = iprot.readListBegin();
		if (tlist.getSize() == -1) {
			iprot.readListEnd();
			return null;
		}
		List<Byte> list = new ArrayList<Byte>();
		for (int t = 0; t < tlist.getSize(); ++t) {
			list.add(iprot.readByte());
		}
		iprot.readListEnd();
		return list;
	}

	public static void writeI16(TProtocol oprot, List<Short> list) {
		if (list == null) {
			oprot.writeListBegin(new TList(TType.I16, -1));
			oprot.writeListEnd();
		} else {
			oprot.writeListBegin(new TList(TType.I16, list.size()));
			for (Short i16 : list) {
				oprot.writeI16(i16);
			}
			oprot.writeListEnd();
		}
	}

	public static List<Short> readI16(TProtocol iprot) {
		TList tlist = iprot.readListBegin();
		if (tlist.getSize() == -1) {
			iprot.readListEnd();
			return null;
		}
		List<Short> list = new ArrayList<Short>();
		for (int t = 0; t < tlist.getSize(); ++t) {
			list.add(iprot.readI16());
		}
		iprot.readListEnd();
		return list;
	}

	public static void writeI32(TProtocol oprot, List<Integer> list) {
		if (list == null) {
			oprot.writeListBegin(new TList(TType.I32, -1));
			oprot.writeListEnd();
		} else {
			oprot.writeListBegin(new TList(TType.I32, list.size()));
			for (Integer i32 : list) {
				oprot.writeI32(i32);
			}
			oprot.writeListEnd();
		}
	}

	public static List<Integer> readI32(TProtocol iprot) {
		TList tlist = iprot.readListBegin();
		if (tlist.getSize() == -1) {
			iprot.readListEnd();
			return null;
		}
		List<Integer> list = new ArrayList<Integer>();
		for (int t = 0; t < tlist.getSize(); ++t) {
			list.add(iprot.readI32());
		}
		iprot.readListEnd();
		return list;
	}

	public static void writeI64(TProtocol oprot, List<Long> list) {
		if (list == null) {
			oprot.writeListBegin(new TList(TType.I64, -1));
			oprot.writeListEnd();
		} else {
			oprot.writeListBegin(new TList(TType.I64, list.size()));
			for (Long i64 : list) {
				oprot.writeI64(i64);
			}
			oprot.writeListEnd();
		}
	}

	public static List<Long> readI64(TProtocol iprot) {
		TList tlist = iprot.readListBegin();
		if (tlist.getSize() == -1) {
			iprot.readListEnd();
			return null;
		}
		List<Long> list = new ArrayList<Long>();
		for (int t = 0; t < tlist.getSize(); ++t) {
			list.add(iprot.readI64());
		}
		iprot.readListEnd();
		return list;
	}

	public static void writeDouble(TProtocol oprot, List<Double> list) {
		if (list == null) {
			oprot.writeListBegin(new TList(TType.DOUBLE, -1));
			oprot.writeListEnd();
		} else {
			oprot.writeListBegin(new TList(TType.DOUBLE, list.size()));
			for (Double dub : list) {
				oprot.writeDouble(dub);
			}
			oprot.writeListEnd();
		}
	}

	public static List<Double> readDouble(TProtocol iprot) {
		TList tlist = iprot.readListBegin();
		if (tlist.getSize() == -1) {
			iprot.readListEnd();
			return null;
		}
		List<Double> list = new ArrayList<Double>();
		for (int t = 0; t < tlist.getSize(); ++t) {
			list.add(iprot.readDouble());
		}
		iprot.readListEnd();
		return list;
	}

	public static void writeString(TProtocol oprot, List<String> list) {
		if (list == null) {
			oprot.writeListBegin(new TList(TType.STRING, -1));
			oprot.writeListEnd();
		} else {
			oprot.writeListBegin(new TList(TType.STRING, list.size()));
			for (String str : list) {
				oprot.writeString(str);
			}
			oprot.writeListEnd();
		}
	}

	public static List<String> readString(TProtocol iprot) {
		TList tlist = iprot.readListBegin();
		if (tlist.getSize() == -1) {
			iprot.readListEnd();
			return null;
		}
		List<String> list = new ArrayList<String>();
		for (int t = 0; t < tlist.getSize(); ++t) {
			list.add(iprot.readString());
		}
		iprot.readListEnd();
		return list;
	}
	
	public static void writeDate(TProtocol oprot, List<Date> list) {
		if (list == null) {
			oprot.writeListBegin(new TList(TType.DATE, -1));
			oprot.writeListEnd();
		} else {
			oprot.writeListBegin(new TList(TType.DATE, list.size()));
			for (java.util.Date date : list) {
				oprot.writeDate(date);
			}
			oprot.writeListEnd();
		}
	}

	public static List<Date> readDate(TProtocol iprot) {
		TList tlist = iprot.readListBegin();
		if (tlist.getSize() == -1) {
			iprot.readListEnd();
			return null;
		}
		List<Date> list = new ArrayList<Date>();
		for (int t = 0; t < tlist.getSize(); ++t) {
			list.add(iprot.readDate());
		}
		iprot.readListEnd();
		return list;
	}
}
