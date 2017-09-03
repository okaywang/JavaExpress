package com.zhaopin.thrift.rpc.balance;

import java.util.List;
import java.util.Random;

import com.zhaopin.thrift.rpc.Constants;
import com.zhaopin.thrift.rpc.common.Invocation;
import com.zhaopin.thrift.rpc.common.ServerNode;
import com.zhaopin.thrift.rpc.invoker.ThriftInvocation;
import com.zhaopin.thrift.rpc.registry.RegistryFactory;
import com.zhaopin.thrift.rpc.registry.RegistryService;

public class WeightLoadBalance implements LoadBalance {

	@Override
	public ServerNode select(ThriftInvocation thriftInvocation) {
		Invocation invocation = thriftInvocation.getInvocation();
		String group = invocation.getGroup();
		if (group == null || group.length() <= 0) {
			group = Constants.DEF_GROUP;
		}
		RegistryService registryService = RegistryFactory.getRegistry(invocation.getZkAddr());
		if (registryService == null) {
			return null;
		}
		final String service = invocation.getService();
		final String version = invocation.getVersion();
		List<ServerNode> serviceList = registryService.loadService(group, service, version);
		if (serviceList != null && serviceList.size() > 0) {
			try {
				return doSelect(serviceList);
			} catch (Exception exp) {
				LOGGER.warn("select server node exception!", exp);
			}
		}
		return null;
	}

	public ServerNode doSelect(List<ServerNode> serviceList) {
		int total = 0;
		for (ServerNode serverNode : serviceList) {
			total += serverNode.getWeight();
		}
		Random random = new Random();
		int randomVal = random.nextInt(total) % total;
		total = 0;
		ServerNode target = serviceList.get(0);
		for (int t = 0; t < serviceList.size(); ++t) {
			ServerNode serverNode = serviceList.get(t);
			total += serverNode.getWeight();
			if (total > randomVal) {
				target = serverNode;
				break;
			}
		}
		if (target.isTemporaryFailure()) {
			// 如果当前节点处于临时失败节点，则顺序选择第一个非临时失败节点
			for (ServerNode candidate : serviceList) {
				if (!candidate.isTemporaryFailure()) {
					return candidate;
				}
			}
		}
		return target;
	}

}
