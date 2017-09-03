package com.zhaopin.thrift.rpc.util;

import org.springframework.util.StringUtils;

public final class NumberUtil {

	/**
	 * 转换16进制整形
	 * 
	 * @param value
	 * @return
	 */
	public final static int parseIntHex(String value) {
		try {
			if (StringUtils.isEmpty(value)) {
				return 0;
			}
			return Integer.parseInt(value, 16);
		} catch (Exception exp) {
			return 0;
		}
	}
	
	public final static long parseLongHex(String value) {
		try {
			if (StringUtils.isEmpty(value)) {
				return 0;
			}
			return Long.parseLong(value, 16);
		} catch (Exception exp) {
			return 0;
		}
	}

	public final static String toIntHex(int value) {
		try {
			return Integer.toHexString(value);
		} catch (Exception exp) {
			return "";
		}
	}
	
	public final static String toLongHex(long value) {
		try {
			return Long.toHexString(value);
		} catch (Exception exp) {
			return "";
		}
	}

	public final static int getInt(String val) {
		return Integer.parseInt(val);
	}

	public final static int getInt(String val, int def) {
		try {
			return Integer.parseInt(val);
		} catch (NumberFormatException exp) {
			return def;
		}
	}

	public final static long getLong(String val, long def) {
		try {
			return Long.parseLong(val);
		} catch (NumberFormatException exp) {
			return def;
		}
	}

	public final static int bytesToInt(byte[] bytes, int pos) {
		byte[] tmp = new byte[4];
		for (int t = 0; t < 4; ++t) {
			tmp[t] = bytes[pos + t];
		}
		return decodeFrameSize(tmp);
	}

	public final static byte[] intToBytes(int value) {
		byte[] buf = new byte[4];
		encodeFrameSize(value, buf);
		return buf;
	}

	public final static void encodeFrameSize(final int frameSize, final byte[] buf) {
		buf[0] = (byte) (0xff & (frameSize >> 24));
		buf[1] = (byte) (0xff & (frameSize >> 16));
		buf[2] = (byte) (0xff & (frameSize >> 8));
		buf[3] = (byte) (0xff & (frameSize));
	}

	public static final int decodeFrameSize(final byte[] buf) {
		return ((buf[0] & 0xff) << 24) | ((buf[1] & 0xff) << 16) | ((buf[2] & 0xff) << 8) | ((buf[3] & 0xff));
	}
}
