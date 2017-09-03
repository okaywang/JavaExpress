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
	 * ��ȡ�ͻ��˵Ĵ��������е���(�������ڲ��Խӿڣ����Բ�������������ϵͳ)
	 * 
	 * @param target
	 *            �����Ŀ���ַ ��ʽ�� 127.0.0.1:16713
	 * @param group
	 *            ����ķ���
	 * @param version
	 *            ����İ汾
	 * @param type
	 *            ����Ľӿ���
	 * @return
	 */
	
	public static <T> T getClient(String target, String group, String version, Class<T> type) {
		return getClient(target, group, version, type, -1);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getClient(String target, String group, String version, Class<T> type, int thriftVersion)  {
		if (!EnvUtils.islocalEnv()) {
			throw new IllegalStateException("��ǰ�������Ǳ��ص��Ի��������಻�����������ϻ���!");
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
		// ������ʱ(���Բ���������ʹ��)
		invoker.setTimeout(-1);
		// ���ز��Ի�������������
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
