package com.zhaopin.thrift.rpc.invoker;

/**
 * 客户端集群策略
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
