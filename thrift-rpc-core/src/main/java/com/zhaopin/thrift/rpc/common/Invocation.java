package com.zhaopin.thrift.rpc.common;

import java.util.List;

import com.zhaopin.thrift.rpc.Constants;
import com.zhaopin.thrift.rpc.balance.BalanceStrategy;
import com.zhaopin.thrift.rpc.balance.LoadBalance;
import com.zhaopin.thrift.rpc.invoker.ClusterStrategy;

/**
 * 服务调用相关的参数信息
 * 
 *
 */
public final class Invocation {
	// 调用的服务
	private String service;
	// 服务的版本
	private String version;
	// 服务的分组
	private String group = Constants.DEF_GROUP;
	// 注册中心的地址
	private String zkAddr;
	// 服务的超时时间
	private long timeout = Constants.DEF_CALL_WAIT_TIME;
	// 服务的客户端集群策略
	private ClusterStrategy strategy = ClusterStrategy.failover;
	// 服务的重试次数
	private int retryCount = Constants.DEF_RETRY;
	// 轮询策略
	private BalanceStrategy balance;
	// 设置目的服务地址
	private List<ServerNode> target;
	// 自定义服务选择策略
	private LoadBalance selector = null;
	// 调用方法的参数
	private Object[] args;

	private int async = 0;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getZkAddr() {
		return zkAddr;
	}

	public void setZkAddr(String zkAddr) {
		this.zkAddr = zkAddr;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public ClusterStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(ClusterStrategy strategy) {
		this.strategy = strategy;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public List<ServerNode> getTarget() {
		return target;
	}

	public void setTarget(List<ServerNode> target) {
		this.target = target;
	}

	public BalanceStrategy getBalance() {
		return balance;
	}

	public void setBalance(BalanceStrategy balance) {
		this.balance = balance;
	}

	public LoadBalance getSelector() {
		return selector;
	}

	public void setSelector(LoadBalance selector) {
		this.selector = selector;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public int getAsync() {
		return async;
	}

	public void setAsync(int async) {
		this.async = async;
	}

}
