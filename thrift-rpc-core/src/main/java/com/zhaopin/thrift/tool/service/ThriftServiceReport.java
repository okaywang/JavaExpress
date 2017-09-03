package com.zhaopin.thrift.tool.service;

import com.zhaopin.rpc.annotation.ThriftReference;
import com.zhaopin.thrift.tool.annotation.InnerService;

@InnerService
public class ThriftServiceReport {

	@ThriftReference(group = "plat", version = "1.0.0")
	private IThriftStatisticsReport report;

	@ThriftReference(group = "plat", version = "1.0.0")
	private IThriftInvokeReport invokeReport;

	public IThriftStatisticsReport getReport() {
		return report;
	}

	public void setReport(IThriftStatisticsReport report) {
		this.report = report;
	}

	public IThriftInvokeReport getInvokeReport() {
		return invokeReport;
	}

	public void setInvokeReport(IThriftInvokeReport invokeReport) {
		this.invokeReport = invokeReport;
	}

}
