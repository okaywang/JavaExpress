package com.zhaopin.thrift.rpc.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;

public class ExceptionStackTraceUtils {

	public static Logger LOGGER = LoggerFactory.getLogger(ExceptionStackTraceUtils.class);

	public static String getStackTrace(Throwable t) {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			try {
				t.printStackTrace(pw);
				return sw.toString();
			} finally {
				pw.close();
			}
		} catch (Exception exp) {
			LOGGER.error("print stack trace exception", exp);
		}
		return t.getMessage() != null ? t.getMessage() : "";
	}

}
