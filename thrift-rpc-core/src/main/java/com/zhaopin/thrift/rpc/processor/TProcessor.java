package com.zhaopin.thrift.rpc.processor;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.thrift.rpc.protocol.TProtocol;

public interface TProcessor {

	public static Logger LOGGER = LoggerFactory.getLogger(TProcessor.class);

	/**
	 * ¥¶¿Ì«Î«Û
	 * 
	 * @param iprot
	 * @param oprot
	 * @throws Throwable
	 */
	public void process(TProtocol iprot, TProtocol oprot) throws Throwable;

}
