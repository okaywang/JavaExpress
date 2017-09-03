package com.zhaopin.thrift.rpc.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.StringUtils;

import com.zhaopin.thrift.rpc.Constants;
import com.zhaopin.thrift.rpc.balance.BalanceStrategy;
import com.zhaopin.thrift.rpc.balance.LoadBalance;
import com.zhaopin.thrift.rpc.common.Invocation;
import com.zhaopin.thrift.rpc.common.ServerNode;
import com.zhaopin.thrift.rpc.invoker.ClusterStrategy;
import com.zhaopin.thrift.rpc.proxy.ProxyFactory;
import com.zhaopin.thrift.rpc.util.NumberUtil;

public final class ThriftRefUtils {

	public static Object getObject(ThriftRefDto thriftRefDto, ConfigurableListableBeanFactory beanFactory) {
		Invocation invoker = new Invocation();
		String interfaceName = thriftRefDto.getThriftInterface().getName();
		if (interfaceName.endsWith("$AsyncIface")) {
			interfaceName = interfaceName.substring(0, interfaceName.length() - "$AsyncIface".length());
		}
		invoker.setService(interfaceName);
		// ���ó������ĵ�ַ
		invoker.setZkAddr(thriftRefDto.getZkAddr());
		// ���õ��÷���ķ���
		if (thriftRefDto.getThriftReference().group() != null) {
			invoker.setGroup(thriftRefDto.getThriftReference().group());
		} else {
			invoker.setGroup(Constants.DEF_GROUP);
		}
		// ���÷���ĳ�ʱʱ��
		if (thriftRefDto.getThriftReference().timeout() < 0) {
			invoker.setTimeout(-1);
		} else {
			invoker.setTimeout(thriftRefDto.getThriftReference().timeout());
		}
		// ���÷�������Դ���
		if (thriftRefDto.getThriftReference().retry() <= 0) {
			invoker.setRetryCount(1);
		} else {
			invoker.setRetryCount(thriftRefDto.getThriftReference().retry());
		}
		String round = thriftRefDto.getThriftReference().round();
		if (Constants.WEIGHT_ROUND.equals(round)) {
			// ����Ȩ�ؽ��з���ѡ��
			invoker.setBalance(BalanceStrategy.WEIGHT);
		} else if (Constants.RANDOM_ROUND.equals(round)) {
			// ��ѯ�ķ���ѡ�����
			invoker.setBalance(BalanceStrategy.RANDOM);
		} else if (Constants.TOKEN_BASED.equals(round)) {
			// ����token�ĻҶȷ���
			invoker.setBalance(BalanceStrategy.TOKEN_BASED);
		} else {
			// �û��Զ���ķ���ѡ�����
			// �Զ������ѡ����
			LoadBalance loadBalance = beanFactory.getBean(round, LoadBalance.class);
			invoker.setBalance(BalanceStrategy.USER_DEFINE);
			invoker.setSelector(loadBalance);
		}
		// ���÷���Ŀͻ��˲���
		invoker.setStrategy(getClusterStrategy(thriftRefDto.getThriftReference().strategy()));
		// ���÷���İ汾��
		invoker.setVersion(thriftRefDto.getThriftReference().version());
		// ��target���зָ�
		invoker.setTarget(getTargets(thriftRefDto.getThriftReference().target()));
		// ������ͬ�����û����첽����
		invoker.setAsync(thriftRefDto.getThriftInterface().getName().endsWith("$AsyncIface") ? 1 : 0);
		// �����������
		return ProxyFactory.getProxy(invoker, new Class<?>[] { thriftRefDto.getThriftInterface() });
	}

	private static List<ServerNode> getTargets(String target) {
		if (!StringUtils.isEmpty(target)) {
			List<ServerNode> serverNodes = new ArrayList<ServerNode>();
			String[] tragets = target.split(";");
			for (String str : tragets) {
				String[] hostPort = str.split(":");
				int port = NumberUtil.getInt(hostPort[1].trim());
				serverNodes.add(new ServerNode(hostPort[0].trim(), port));
			}
			return serverNodes;
		} else {
			return null;
		}
	}

	private static ClusterStrategy getClusterStrategy(String strategy) {
		if (Constants.CLUSTER_FAILOVER.equals(strategy)) {
			return ClusterStrategy.failover;
		} else if (Constants.CLUSTER_FAILFAST.equals(strategy)) {
			return ClusterStrategy.failfast;
		} else if (Constants.CLUSTER_FAILSAFE.equals(strategy)) {
			return ClusterStrategy.failsafe;
		} else {
			return ClusterStrategy.failover;
		}
	}
}
