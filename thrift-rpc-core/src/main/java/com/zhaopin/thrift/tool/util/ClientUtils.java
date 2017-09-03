package com.zhaopin.thrift.tool.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.zhaopin.common.trace.ZipkinContext;
import com.zhaopin.thrift.rpc.Constants;
import com.zhaopin.thrift.rpc.balance.BalanceStrategy;
import com.zhaopin.thrift.rpc.common.Invocation;
import com.zhaopin.thrift.rpc.common.ServerNode;
import com.zhaopin.thrift.rpc.proxy.ProxyFactory;
import com.zhaopin.thrift.rpc.util.EnvUtils;
import com.zhaopin.thrift.rpc.util.NumberUtil;

public class ClientUtils {

	/**
	 * 获取客户端的代理对象进行调用(仅仅用于测试接口，绝对不允许用在线上系统)
	 * 
	 * @param target
	 *            服务的目标地址 格式是 127.0.0.1:16713
	 * @param group
	 *            服务的分组
	 * @param version
	 *            服务的版本
	 * @param type
	 *            服务的接口类
	 * @return
	 */
	
	public static <T> T getClient(String target, String group, String version, Class<T> type) {
		return getClient(target, group, version, type, -1);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getClient(String target, String group, String version, Class<T> type, int thriftVersion)  {
		if (!EnvUtils.islocalEnv()) {
			throw new IllegalStateException("当前环境不是本地调试环境，该类不允许用于线上环境!");
		}
		Invocation invoker = new Invocation();
		invoker.setService(type.getName());
		invoker.setTarget(getTargets(target, thriftVersion));
		if (StringUtils.isEmpty(group)) {
			invoker.setGroup(group);
		} else {
			invoker.setGroup(Constants.DEF_GROUP);
		}
		invoker.setBalance(BalanceStrategy.TOKEN_BASED);
		invoker.setVersion(version);
		// 不允许超时(所以不允许线上使用)
		invoker.setTimeout(-1);
		// 本地测试环境不开启采样
		ZipkinContext.startZipkin(0, "localTest");
		return (T) ProxyFactory.getProxy(invoker, new Class<?>[] { type });
	}

	private static List<ServerNode> getTargets(String target, int thriftVersion) {
		if (!StringUtils.isEmpty(target)) {
			List<ServerNode> serverNodes = new ArrayList<ServerNode>();
			String[] tragets = target.split(";");
			for (String str : tragets) {
				String[] hostPort = str.split(":");
				int port = NumberUtil.getInt(hostPort[1].trim());
				ServerNode serverNode = new ServerNode(hostPort[0].trim(), port);
				if (thriftVersion > 0) {
					serverNode.setThriftVersion(thriftVersion);
				}
				serverNodes.add(serverNode);
			}
			return serverNodes;
		} else {
			return null;
		}
	}

}
