package com.zhaopin.thrift.tool.service;

import com.zhaopin.rpc.annotation.ThriftInterface;

@ThriftInterface
public interface IThriftStatisticsReport {
	/**
	 * �����ϱ����ô���
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	public boolean report(int type, String value);

}
