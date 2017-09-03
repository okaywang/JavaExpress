package com.zhaopin.thrift.rpc.common;

import java.util.List;

import com.zhaopin.thrift.rpc.Constants;
import com.zhaopin.thrift.rpc.balance.BalanceStrategy;
import com.zhaopin.thrift.rpc.balance.LoadBalance;
import com.zhaopin.thrift.rpc.invoker.ClusterStrategy;

/**
 * ���������صĲ�����Ϣ
 * 
 *
 */
public final class Invocation {
	// ���õķ���
	private String service;
	// ����İ汾
	private String version;
	// ����ķ���
	private String group = Constants.DEF_GROUP;
	// ע�����ĵĵ�ַ
	private String zkAddr;
	// ����ĳ�ʱʱ��
	private long timeout = Constants.DEF_CALL_WAIT_TIME;
	// ����Ŀͻ��˼�Ⱥ����
	private ClusterStrategy strategy = ClusterStrategy.failover;
	// ��������Դ���
	private int retryCount = Constants.DEF_RETRY;
	// ��ѯ����
	private BalanceStrategy balance;
	// ����Ŀ�ķ����ַ
	private List<ServerNode> target;
	// �Զ������ѡ�����
	private LoadBalance selector = null;
	// ���÷����Ĳ���
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
