package com.zhaopin.thrift.rpc.invoker;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;

import com.zhaopin.thrift.rpc.balance.BalanceStrategy;
import com.zhaopin.thrift.rpc.balance.GrayPubLoadBalance;
import com.zhaopin.thrift.rpc.balance.LoadBalance;
import com.zhaopin.thrift.rpc.balance.RandomLoadBalance;
import com.zhaopin.thrift.rpc.balance.WeightLoadBalance;
import com.zhaopin.thrift.rpc.common.Invocation;
import com.zhaopin.thrift.rpc.common.ServerNode;
import com.zhaopin.thrift.rpc.exception.RpcNoProviderException;

public abstract class AbstractInvoker implements ThriftInvoker {
	// 同步调用的消息的序号(全部是奇数)
	private static AtomicInteger SYNC_SEQ = new AtomicInteger(1);
	// 异步调用的消息的序号(全部是偶数)
	private static AtomicInteger ASYNC_SEQ = new AtomicInteger(2);
	// 最大的消息的Id
	private static int MAX_SEQID = Integer.MAX_VALUE - 100000;
	// 权重轮询
	protected static LoadBalance weightBalance = new WeightLoadBalance();
	// 随机轮询
	protected static LoadBalance randomBalance = new RandomLoadBalance();
	// 灰度方案
	protected static LoadBalance grayPubBalance = new GrayPubLoadBalance();

	protected LoadBalance getLoadBalance(Invocation invocation) {
		if (BalanceStrategy.RANDOM == invocation.getBalance()) {
			return randomBalance;
		} else if (BalanceStrategy.WEIGHT == invocation.getBalance()) {
			return weightBalance;
		} else if (BalanceStrategy.TOKEN_BASED == invocation.getBalance()) {
			return grayPubBalance;
		} else {
			return randomBalance;
		}
	}

	protected int getSeqId(boolean isAsync) {
		if (isAsync) {
			int seqid = ASYNC_SEQ.getAndAdd(2);
			if (seqid > MAX_SEQID) {
				ASYNC_SEQ.set(0);
			}
			return seqid;
		} else {
			int seqid = SYNC_SEQ.getAndAdd(2);
			if (seqid > MAX_SEQID) {
				SYNC_SEQ.set(0);
			}
			return seqid;
		}
	}

	protected ServerNode selectProvider(ThriftInvocation invoker) {
		// 如果设置了自定义的服务选择器
		LoadBalance loadBalance = invoker.getSelector();
		if (loadBalance != null) {
			// 执行服务的自定义选择策略
			Invocation invocation = invoker.getInvocation();
			invocation.setArgs(invoker.getArgs());
			return loadBalance.select(invoker);
		}
		// 判断是否是直接连接服务
		List<ServerNode> target = invoker.getInvocation().getTarget();
		ServerNode serverNode;
		if (target != null && target.size() > 0) {
			Random random = new Random();
			int index = random.nextInt() % target.size();
			serverNode = target.get(index);
		} else {
			serverNode = getLoadBalance(invoker.getInvocation()).select(invoker);
		}
		if (serverNode == null) {
			String service = invoker.getInvocation().getService();
			String version = invoker.getInvocation().getVersion();
			String group = invoker.getInvocation().getGroup();
			throw new RpcNoProviderException(service, invoker.getMethod(), version, group);
		}
		return serverNode;
	}

	protected void dumpMessage(String fileName, byte[] msg) {
		try {
			FileUtils.writeByteArrayToFile(new File(fileName), msg);
		} catch (IOException exp) {
			LOGGER.warn("dump message exception", exp);
		}
	}
}
