package com.zhaopin.thrift.rpc.monitor;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zhaopin.common.container.RequestID;
import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.thrift.rpc.util.EnvUtils;
import com.zhaopin.thrift.rpc.util.IPResolver;
import com.zhaopin.thrift.rpc.util.UniqueIdUtils;
import com.zhaopin.thrift.tool.service.IThriftInvokeReport;
import com.zhaopin.thrift.tool.service.IThriftStatisticsReport;

public class InvokeStatistics implements Runnable {

	public static InvokeStatistics instance = new InvokeStatistics();

	public static Logger LOGGER = LoggerFactory.getLogger(InvokeStatistics.class);

	public static final int STATISTICS = 1;

	private static Map<Long, Map<String, CountRecord>> statistics = Maps.newConcurrentMap();

	private String format = "thrift://%s?group=%s&version=%s&method=%s&period=%d";

	private long interval = 1000;

	private volatile boolean stop = false;

	private volatile long starttime = System.currentTimeMillis() / interval;

	private IThriftStatisticsReport thriftReport;

	private int port;

	private String host = IPResolver.getIP();

	private static final String ignorePkg = IThriftInvokeReport.class.getPackage().getName();

	private boolean isQaEnv = EnvUtils.islocalEnv();

	public void statistics(StatisticsRecord record) {
		if (isQaEnv) {
			return;
		}
		try {
			// 如果是统计相关的服务，直接跳过
			if (record.getService().startsWith(ignorePkg)) {
				return;
			}
			// 没有锁策略，可能丢失数量, 但是没有必要如此准确
			long period = record.getStartTime() / interval;
			String key = String.format(format, record.getService(), record.getGroup(), record.getVersion(),
					record.getMethod(), period);
			Map<String, CountRecord> results = statistics.get(period);
			if (results == null) {
				results = Maps.newConcurrentMap();
				statistics.put(period, results);
			}
			CountRecord countRecord = results.get(key);
			if (countRecord == null) {
				countRecord = new CountRecord();
				results.put(key, countRecord);
			}
			if (record.isSuccess()) {
				countRecord.incSuccessCount();
			} else {
				countRecord.incFailCount();
			}
			countRecord.incTotalTime(record.getCost());
			if (countRecord.getMaxTime() < record.getCost()) {
				countRecord.setMaxTime(record.getCost());
			}
			if (countRecord.getMinTime() > record.getCost()) {
				countRecord.setMinTime(record.getCost());
			}
			if (record.getProvider() != null) {
				countRecord.setEndpoint(record.getProvider());
			} else {
				countRecord.setEndpoint(this.host + ":" + this.port);
			}
		} catch (Exception exp) {
			LOGGER.error("server statistics exception", exp);
		}
	}

	@Override
	public void run() {
		if (isQaEnv) {
			return;
		}
		while (!stop) {
			try {
				// 延迟三秒上报
				long endtime = (System.currentTimeMillis() - 3000) / interval;
				while (starttime < endtime) {
					Map<String, CountRecord> results = statistics.remove(starttime++);
					if (results == null || results.size() <= 0) {
						continue;
					}
					if (thriftReport != null) {
						// 记录上报
						RequestID.setRequestID(UniqueIdUtils.generate());
						thriftReport.report(STATISTICS, JSON.toJSONString(results));
					}
				}
			} catch (Exception exp) {
				LOGGER.warn("service statistics exception", exp);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException exp) {
				// 忽略异常
			}
		}
	}

	public void stopService() {
		this.stop = true;
	}

	protected class CountRecord {
		// 成功次数
		private AtomicLong successCount = new AtomicLong(0);
		// 失败次数
		private AtomicLong failCount = new AtomicLong(0);
		// 总时间
		private AtomicLong totalTime = new AtomicLong(0);
		// 最长时间
		private AtomicLong maxTime = new AtomicLong(0);
		// 最短时间
		private AtomicLong minTime = new AtomicLong(0);
		// 地址
		private String endpoint;

		public long getSuccessCount() {
			return successCount.get();
		}

		public void incSuccessCount() {
			this.successCount.incrementAndGet();
		}

		public long getFailCount() {
			return failCount.longValue();
		}

		public void incFailCount() {
			this.failCount.incrementAndGet();
		}

		public long getTotalTime() {
			return totalTime.get();
		}

		public void incTotalTime(long time) {
			this.totalTime.addAndGet(time);
		}

		public long getMaxTime() {
			return maxTime.get();
		}

		public void setMaxTime(long maxTime) {
			this.maxTime.set(maxTime);
		}

		public long getMinTime() {
			return minTime.get();
		}

		public void setMinTime(long minTime) {
			this.minTime.set(minTime);
		}

		public String getEndpoint() {
			return endpoint;
		}

		public void setEndpoint(String endpoint) {
			this.endpoint = endpoint;
		}

	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setThriftReport(IThriftStatisticsReport thriftReport) {
		this.thriftReport = thriftReport;
	}

}
