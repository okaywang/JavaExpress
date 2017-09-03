package com.zhaopin.thrift.rpc.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.alibaba.com.caucho.hessian.io.HessianOutput;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeUtil {
	
	public static Logger LOGGER = LoggerFactory.getLogger(SerializeUtil.class);
	
	public static byte[] encode(Object obj) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			HessianOutput ho = new HessianOutput(os);
			ho.writeObject(obj);
			return os.toByteArray();
		} catch (Exception exp) {
			// �п��ܻ����������쳣
			LOGGER.error("encode exception", exp);
			return new byte[0];
		}
	}

	public static Object decode(byte[] obj) throws IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(obj);
		HessianInput hi = new HessianInput(is);
		return hi.readObject();
	}
}
