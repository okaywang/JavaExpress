package com.zhaopin.thrift.tool.service;

import com.zhaopin.rpc.annotation.ThriftInterface;

@ThriftInterface
public interface IThriftStatisticsReport {
	/**
	 * 服务上报调用次数
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	public boolean report(int type, String value);

}
