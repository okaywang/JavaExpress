package com.zhaopin.thrift.rpc.invoker;

/**
 * �ͻ��˼�Ⱥ����
 * @author LinuxLover
 *
 */
public enum ClusterStrategy {
	// failover
	failover,
	// failfast
	failfast,
	// failsafe
	failsafe,
	// fork
	fork
}
