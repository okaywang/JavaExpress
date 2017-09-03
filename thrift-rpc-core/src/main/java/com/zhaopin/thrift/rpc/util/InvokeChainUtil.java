package com.zhaopin.thrift.rpc.util;

import org.springframework.util.StringUtils;

import com.zhaopin.thrift.rpc.Constants;
import com.zhaopin.thrift.rpc.RpcContext;
import com.zhaopin.thrift.rpc.monitor.StatisticsHttpRecord;
import com.zhaopin.thrift.rpc.monitor.StatisticsRecord;

public final class InvokeChainUtil {

	public final static String thriftInvokeFmt = "%s?group=%s&method=%s&major=%s&minor=%s&protocol=thrift";

	public final static String httpInvokeFmt = "%s?group=%s&method=%s&major=%s&minor=%s&uri=%s&protocol=http";

	/**
	 * 服务调用者调用
	 * 
	 * @param taskId
	 * @param group
	 * @param service
	 * @param version
	 * @param method
	 */
	public static void mutateInvokeChain(String taskId, String group, String service, String version, String method) {
		if (StringUtils.isEmpty(group)) {
			group = Constants.DEF_GROUP;
		}
		String[] verParts = version.split("\\.");
		if (verParts != null && verParts.length >= 3) {
			// 结构格式 service group method major minor
			String str = String.format(thriftInvokeFmt, service, group, method, verParts[0], verParts[1]);
			RpcContext.appendInvokeChainWithCheck(taskId, str);
		}

	}

	/**
	 * 服务提供者调用
	 * 
	 * @param taskId
	 * @param record
	 * @param state
	 */
	public static void mutateInvokeChain(String taskId, StatisticsRecord record, boolean state) {
		// 是否来自于thrift接口实现的调用
		String zkNodeValue = "";
		if (record != null) {
			String group = record.getGroup();
			if (StringUtils.isEmpty(group)) {
				group = Constants.DEF_GROUP;
			}
			String[] verParts = record.getVersion().split("\\.");
			if (verParts != null && verParts.length >= 3) {
				// 结构格式 service group method major minor
				zkNodeValue = String.format(thriftInvokeFmt, record.getService(), group, record.getMethod(),
						verParts[0], verParts[1]);
			}
		}
		// 标志来自于第一个thrift实现类，用来区分http
		if (state) {
			zkNodeValue = zkNodeValue + "&state=1";
		}
		RpcContext.appendInvokeChain(taskId, zkNodeValue);
	}

	public static void mutateHttpInvokeChain(String taskId, StatisticsHttpRecord record, boolean state) {
		// 是否来自于http接口实现的调用
		String zkNodeValue = "";
		if (record != null) {
			String group = record.getGroup();
			if (StringUtils.isEmpty(group)) {
				group = Constants.DEF_GROUP;
			}
			String[] verParts = record.getVersion().split("\\.");
			if (verParts != null && verParts.length >= 3) {
				// 结构格式 service group method major minor
				zkNodeValue = String.format(httpInvokeFmt, record.getService(), group, record.getMethod(), verParts[0],
						verParts[1], record.getUri());
			}
		}
		// 标志来自于第一个thrift实现类，用来区分http
		if (state) {
			zkNodeValue = zkNodeValue + "&state=1";
		}
		RpcContext.appendInvokeChain(taskId, zkNodeValue);
	}

	public static void removeInvokeChain(String taskId) {
		RpcContext.removeInvokeChain(taskId);
	}

}
