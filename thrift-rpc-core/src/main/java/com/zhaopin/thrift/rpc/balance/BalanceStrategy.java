package com.zhaopin.thrift.rpc.balance;

public enum BalanceStrategy {
	// 随机轮询
	RANDOM,
	// 按照权重轮训
	WEIGHT,
	// 基于token的服务灰度策略
	TOKEN_BASED,
	// 用户自定义的服务选择器
	USER_DEFINE
}
