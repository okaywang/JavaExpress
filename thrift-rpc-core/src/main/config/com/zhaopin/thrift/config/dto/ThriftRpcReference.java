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
	// bean的ID
	private String id;
	// 直连服务的情况下的IP
	private String target;
	// 服务的版本号
	private String version;
	// 服务的接口
	private String interfaceClass;
	// 服务重试次数
	private int retry;
	// 调用的超时时间
	private long timeout;
	// 服务的分组
	private String group;
	// 客户端容错策略
	private String strategy;
	// 服务轮询策略(weight按照权重轮询, random平均轮询)
	private String round = Constants.TOKEN_BASED;
	// 注册中心地址
	private ThriftRpcRegistry registry;
	// 服务选择器
	private LoadBalance selector = null;
	// 是否异步
	private int async = 0;

	public Object getObject() throws Exception {
		Class<?> clientProxy = getObjectType();
		// zookeeper的地址
		final String zkAddr = registry.getZkAddr();
		Invocation invoker = new Invocation();
		invoker.setService(interfaceClass);
		// 设置初测中心地址
		invoker.setZkAddr(zkAddr.trim());
		// 设置调用服务的分组
		if (group != null) {
			invoker.setGroup(group);
		} else {
			invoker.setGroup(Constants.DEF_GROUP);
		}
		// 设置服务的超时时间
		if (timeout < 0) {
			invoker.setTimeout(-1);
		} else {
			invoker.setTimeout(timeout);
		}
		// 设置服务的重试次数
		if (retry <= 0) {
			invoker.setRetryCount(1);
		} else {
			invoker.setRetryCount(retry);
		}
		if (Constants.WEIGHT_ROUND.equals(this.round)) {
			// 按照权重进行服务选择
			invoker.setBalance(BalanceStrategy.WEIGHT);
		} else if (Constants.RANDOM_ROUND.equals(this.round)) {
			// 轮询的服务选择策略
			invoker.setBalance(BalanceStrategy.RANDOM);
		} else if (Constants.TOKEN_BASED.equals(this.round)) {
			invoker.setBalance(BalanceStrategy.TOKEN_BASED);
		} else {
			// 用户自定义的服务选择策略
			invoker.setBalance(BalanceStrategy.USER_DEFINE);
			// 自定义服务选择器
			invoker.setSelector(selector);
		}
		// 设置服务的客户端策略
		invoker.setStrategy(getClusterStrategy(strategy));
		// 设置服务的版本号
		invoker.setVersion(version);
		// 对target进行分割
		invoker.setTarget(getTargets(target));
		invoker.setAsync(this.async);
		// 创建代理对象
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
