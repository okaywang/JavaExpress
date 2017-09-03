package com.zhaopin.thrift.rpc.monitor;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.zhaopin.common.container.RequestID;
import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.thrift.rpc.RpcContext;
import com.zhaopin.thrift.rpc.util.EnvUtils;
import com.zhaopin.thrift.rpc.util.UniqueIdUtils;
import com.zhaopin.thrift.tool.service.IThriftInvokeReport;

public class InvokeChainMonitor implements Runnable {

	public static Logger LOGGER = LoggerFactory.getLogger(InvokeChainMonitor.class);

	private boolean state = true;

	private IThriftInvokeReport invokeReport;

	@Override
	public void run() {
		if (EnvUtils.islocalEnv()) {
			return;
		}
		while (state) {
			try {
				Map<String, String> dependents = RpcContext.getDependents();
				for (Entry<String, String> entry : dependents.entrySet()) {
					String key = entry.getKey();
					LOGGER.info("detect dependent {}", key);
					String[] values = key.split(";");
					// 首先提取出第一条的标志
					if (values.length > 0) {
						String invoker = "";
						// 1~max是被调用的服务
						List<String> refs = Lists.newArrayList();
						for (int t = 0; t < values.length; ++t) {
							// 判断上一级调用是否相同
							if (values[t].endsWith("&state=1")) {
								invoker = values[t].substring(0, values[t].length() - "&state=1".length());
							} else {
								refs.add(values[t]);
							}
						}
						if (!StringUtils.isEmpty(invoker)) {
							if (invokeReport != null) {
								// 每一次调用前更新taskId
								RequestID.setRequestID(UniqueIdUtils.generate());
								invokeReport.registerInvoker(invoker, refs);
							}
						}
					}
				}
			} catch (Exception exp) {
				LOGGER.error("register invoker exception", exp);
			}
			try {
				Thread.sleep(20 * 1000);
			} catch (Exception exp) {

			}
		}
	}

	public void stop() {
		this.state = false;
	}

	public IThriftInvokeReport getInvokeReport() {
		return invokeReport;
	}

	public void setInvokeReport(IThriftInvokeReport invokeReport) {
		this.invokeReport = invokeReport;
	}

}
