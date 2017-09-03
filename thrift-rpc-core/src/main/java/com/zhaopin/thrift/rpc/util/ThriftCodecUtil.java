package com.zhaopin.thrift.rpc.util;

import com.zhaopin.thrift.rpc.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThriftCodecUtil {

	public static Logger LOGGER = LoggerFactory.getLogger(ThriftCodecUtil.class);

	/**
	 * 获取struct类的codec类的路径
	 * 
	 * @param structName
	 * @return
	 */
	public static String getStructCodecName(String structName) {
		int index = structName.lastIndexOf(".");
		if (index < 0) {
			throw new IllegalStateException("ClassName \"" + structName + "\" is invalid!");
		}
		return structName.substring(0, index) + "." + Constants.CODEC_CLASS_PREFIX + structName.substring(index + 1)
				+ Constants.CODEC_CLASS_SUFFIX;
	}
}
