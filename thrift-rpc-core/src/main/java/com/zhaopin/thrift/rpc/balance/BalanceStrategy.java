package com.zhaopin.thrift.rpc.balance;

public enum BalanceStrategy {
	// �����ѯ
	RANDOM,
	// ����Ȩ����ѵ
	WEIGHT,
	// ����token�ķ���ҶȲ���
	TOKEN_BASED,
	// �û��Զ���ķ���ѡ����
	USER_DEFINE
}
