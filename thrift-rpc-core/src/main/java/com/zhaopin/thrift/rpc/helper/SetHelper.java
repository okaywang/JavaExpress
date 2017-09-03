package com.zhaopin.thrift.rpc.helper;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.zhaopin.thrift.rpc.common.TSet;
import com.zhaopin.thrift.rpc.common.TType;
import com.zhaopin.thrift.rpc.protocol.TProtocol;

public class SetHelper {

	public static void writeBool(TProtocol oprot, Set<Boolean> list) {
		if (list == null) {
			oprot.writeSetBegin(new TSet(TType.BOOL, -1));
			oprot.writeSetEnd();
		} else {
			oprot.writeSetBegin(new TSet(TType.BOOL, list.size()));
			for (Boolean b : list) {
				oprot.writeBool(b);
			}
			oprot.writeSetEnd();
		}
	}

	public static Set<Boolean> readBool(TProtocol iprot) {
		TSet tset = iprot.readSetBegin();
		if (tset.getSize() == -1) {
			iprot.readSetEnd();
			return null;
		}
		Set<Boolean> list = new HashSet<Boolean>();
		for (int t = 0; t < tset.getSize(); ++t) {
			list.add(iprot.readBool());
		}
		iprot.readSetEnd();
		return list;
	}

	public static void writeByte(TProtocol oprot, Set<Byte> list) {
		if (list == null) {
			oprot.writeSetBegin(new TSet(TType.BYTE, -1));
			oprot.writeSetEnd();
		} else {
			oprot.writeSetBegin(new TSet(TType.BYTE, list.size()));
			for (Byte b : list) {
				oprot.writeByte(b);
			}
			oprot.writeSetEnd();
		}
	}

	public static Set<Byte> readByte(TProtocol iprot) {
		TSet tset = iprot.readSetBegin();
		if (tset.getSize() == -1) {
			iprot.readSetEnd();
			return null;
		}
		Set<Byte> list = new HashSet<Byte>();
		for (int t = 0; t < tset.getSize(); ++t) {
			list.add(iprot.readByte());
		}
		iprot.readSetEnd();
		return list;
	}

	public static void writeI16(TProtocol oprot, Set<Short> list) {
		if (list == null) {
			oprot.writeSetBegin(new TSet(TType.I16, -1));
			oprot.writeSetEnd();
		} else {
			oprot.writeSetBegin(new TSet(TType.I16, list.size()));
			for (Short i16 : list) {
				oprot.writeI16(i16);
			}
			oprot.writeSetEnd();
		}
	}

	public static Set<Short> readI16(TProtocol iprot) {
		TSet tset = iprot.readSetBegin();
		if (tset.getSize() == -1) {
			iprot.readSetEnd();
			return null;
		}
		Set<Short> list = new HashSet<Short>();
		for (int t = 0; t < tset.getSize(); ++t) {
			list.add(iprot.readI16());
		}
		iprot.readSetEnd();
		return list;
	}

	public static void writeI32(TProtocol oprot, Set<Integer> list) {
		if (list == null) {
			oprot.writeSetBegin(new TSet(TType.I32, -1));
			oprot.writeSetEnd();
		} else {
			oprot.writeSetBegin(new TSet(TType.I32, list.size()));
			for (Integer i16 : list) {
				oprot.writeI32(i16);
			}
			oprot.writeSetEnd();
		}
	}

	public static Set<Integer> readI32(TProtocol iprot) {
		TSet tset = iprot.readSetBegin();
		if (tset.getSize() == -1) {
			iprot.readSetEnd();
			return null;
		}
		Set<Integer> list = new HashSet<Integer>();
		for (int t = 0; t < tset.getSize(); ++t) {
			list.add(iprot.readI32());
		}
		iprot.readSetEnd();
		return list;
	}

	public static void writeI64(TProtocol oprot, Set<Long> list) {
		if (list == null) {
			oprot.writeSetBegin(new TSet(TType.I64, -1));
			oprot.writeSetEnd();
		} else {
			oprot.writeSetBegin(new TSet(TType.I64, list.size()));
			for (Long i64 : list) {
				oprot.writeI64(i64);
			}
			oprot.writeSetEnd();
		}
	}

	public static Set<Long> readI64(TProtocol iprot) {
		TSet tset = iprot.readSetBegin();
		if (tset.getSize() == -1) {
			iprot.readSetEnd();
			return null;
		}
		Set<Long> list = new HashSet<Long>();
		for (int t = 0; t < tset.getSize(); ++t) {
			list.add(iprot.readI64());
		}
		iprot.readSetEnd();
		return list;
	}

	public static void writeDouble(TProtocol oprot, Set<Double> list) {
		if (list == null) {
			oprot.writeSetBegin(new TSet(TType.DOUBLE, -1));
			oprot.writeSetEnd();
		} else {
			oprot.writeSetBegin(new TSet(TType.DOUBLE, list.size()));
			for (Double dub : list) {
				oprot.writeDouble(dub);
			}
			oprot.writeSetEnd();
		}
	}

	public static Set<Double> readDouble(TProtocol iprot) {
		TSet tset = iprot.readSetBegin();
		if (tset.getSize() == -1) {
			iprot.readSetEnd();
			return null;
		}
		Set<Double> list = new HashSet<Double>();
		for (int t = 0; t < tset.getSize(); ++t) {
			list.add(iprot.readDouble());
		}
		iprot.readSetEnd();
		return list;
	}

	public static void writeString(TProtocol oprot, Set<String> list) {
		if (list == null) {
			oprot.writeSetBegin(new TSet(TType.STRING, -1));
			oprot.writeSetEnd();
		} else {
			oprot.writeSetBegin(new TSet(TType.STRING, list.size()));
			for (String str : list) {
				oprot.writeString(str);
			}
			oprot.writeSetEnd();
		}
	}

	public static Set<String> readString(TProtocol iprot) {
		TSet tset = iprot.readSetBegin();
		if (tset.getSize() == -1) {
			iprot.readSetEnd();
			return null;
		}
		Set<String> list = new HashSet<String>();
		for (int t = 0; t < tset.getSize(); ++t) {
			list.add(iprot.readString());
		}
		iprot.readSetEnd();
		return list;
	}
	
	public static void writeDate(TProtocol oprot, Set<Date> list) {
		if (list == null) {
			oprot.writeSetBegin(new TSet(TType.DATE, -1));
			oprot.writeSetEnd();
		} else {
			oprot.writeSetBegin(new TSet(TType.DATE, list.size()));
			for (Date date : list) {
				oprot.writeDate(date);
			}
			oprot.writeSetEnd();
		}
	}

	public static Set<Date> readDate(TProtocol iprot) {
		TSet tset = iprot.readSetBegin();
		if (tset.getSize() == -1) {
			iprot.readSetEnd();
			return null;
		}
		Set<Date> list = new HashSet<Date>();
		for (int t = 0; t < tset.getSize(); ++t) {
			list.add(iprot.readDate());
		}
		iprot.readSetEnd();
		return list;
	}
}
