package com.zhaopin.thrift.config.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;

import com.zhaopin.thrift.rpc.Constants;
import com.zhaopin.thrift.rpc.balance.BalanceStrategy;
import com.zhaopin.thrift.rpc.balance.LoadBalance;
import com.zhaopin.thrift.rpc.common.Invocation;
import com.zhaopin.thrift.rpc.common.ServerNode;
import com.zhaopin.thrift.rpc.invoker.ClusterStrategy;
import com.zhaopin.thrift.rpc.proxy.ProxyFactory;
import com.zhaopin.thrift.rpc.util.NumberUtil;

public class ThriftRpcReference implements FactoryBean<Object> {
	// bean��ID
	private String id;
	// ֱ�����������µ�IP
	private String target;
	// ����İ汾��
	private String version;
	// ����Ľӿ�
	private String interfaceClass;
	// �������Դ���
	private int retry;
	// ���õĳ�ʱʱ��
	private long timeout;
	// ����ķ���
	private String group;
	// �ͻ����ݴ����
	private String strategy;
	// ������ѯ����(weight����Ȩ����ѯ, randomƽ����ѯ)
	private String round = Constants.TOKEN_BASED;
	// ע�����ĵ�ַ
	private ThriftRpcRegistry registry;
	// ����ѡ����
	private LoadBalance selector = null;
	// �Ƿ��첽
	private int async = 0;

	public Object getObject() throws Exception {
		Class<?> clientProxy = getObjectType();
		// zookeeper�ĵ�ַ
		final String zkAddr = registry.getZkAddr();
		Invocation invoker = new Invocation();
		invoker.setService(interfaceClass);
		// ���ó������ĵ�ַ
		invoker.setZkAddr(zkAddr.trim());
		// ���õ��÷���ķ���
		if (group != null) {
			invoker.setGroup(group);
		} else {
			invoker.setGroup(Constants.DEF_GROUP);
		}
		// ���÷���ĳ�ʱʱ��
		if (timeout < 0) {
			invoker.setTimeout(-1);
		} else {
			invoker.setTimeout(timeout);
		}
		// ���÷�������Դ���
		if (retry <= 0) {
			invoker.setRetryCount(1);
		} else {
			invoker.setRetryCount(retry);
		}
		if (Constants.WEIGHT_ROUND.equals(this.round)) {
			// ����Ȩ�ؽ��з���ѡ��
			invoker.setBalance(BalanceStrategy.WEIGHT);
		} else if (Constants.RANDOM_ROUND.equals(this.round)) {
			// ��ѯ�ķ���ѡ�����
			invoker.setBalance(BalanceStrategy.RANDOM);
		} else if (Constants.TOKEN_BASED.equals(this.round)) {
			invoker.setBalance(BalanceStrategy.TOKEN_BASED);
		} else {
			// �û��Զ���ķ���ѡ�����
			invoker.setBalance(BalanceStrategy.USER_DEFINE);
			// �Զ������ѡ����
			invoker.setSelector(selector);
		}
		// ���÷���Ŀͻ��˲���
		invoker.setStrategy(getClusterStrategy(strategy));
		// ���÷���İ汾��
		invoker.setVersion(version);
		// ��target���зָ�
		invoker.setTarget(getTargets(target));
		invoker.setAsync(this.async);
		// �����������
		return ProxyFactory.getProxy(invoker, new Class<?>[] { clientProxy });
	}

	public Class<?> getObjectType() {
		try {
			if (this.async == 1) {
				return Thread.currentThread().getContextClassLoader().loadClass(this.interfaceClass + "$AsyncIface");
			}
			return Thread.currentThread().getContextClassLoader().loadClass(this.interfaceClass);
		} catch (ClassNotFoundException exp) {
			throw new IllegalStateException(exp.getMessage(), exp);
		}
	}

	private List<ServerNode> getTargets(String target) {
		if (target != null) {
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

	private ClusterStrategy getClusterStrategy(String strategy) {
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isSingleton() {
		return true;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getInterfaceClass() {
		return interfaceClass;
	}

	public void setInterfaceClass(String interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public ThriftRpcRegistry getRegistry() {
		return registry;
	}

	public void setRegistry(ThriftRpcRegistry registry) {
		this.registry = registry;
	}

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}

	public LoadBalance getSelector() {
		return selector;
	}

	public void setSelector(LoadBalance selector) {
		this.selector = selector;
	}

	public int getAsync() {
		return async;
	}

	public void setAsync(int async) {
		this.async = async;
	}

}
