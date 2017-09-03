package com.zhaopin.thrift.rpc.balance;

import java.util.List;
import java.util.Random;

import org.springframework.util.StringUtils;

import com.zhaopin.thrift.rpc.Constants;
import com.zhaopin.thrift.rpc.common.Invocation;
import com.zhaopin.thrift.rpc.common.ServerNode;
import com.zhaopin.thrift.rpc.invoker.ThriftInvocation;
import com.zhaopin.thrift.rpc.registry.RegistryFactory;
import com.zhaopin.thrift.rpc.registry.RegistryService;

public final class RandomLoadBalance implements LoadBalance {

	@Override
	public ServerNode select(ThriftInvocation thriftInvocation) {
		Invocation invocation = thriftInvocation.getInvocation();
		String group = invocation.getGroup();
		if (StringUtils.isEmpty(group)) {
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
			return doSelect(serviceList);
		}
		return null;
	}

	private ServerNode doSelect(List<ServerNode> serviceList) {
		Random random = new Random();
		int size = serviceList.size();
		int randomValue = random.nextInt(size) % size;
		ServerNode serverNode = serviceList.get(randomValue);
		// �����֧����Ŀ����Ժ�С
		if (serverNode.isTemporaryFailure()) {
			// ���ѡ���Ľڵ�����ʱʧ�ܽڵ�
			// ѡ���һ��������ʱʧ�ܵĽڵ�,��������ʵ������Ϊ�˱���zookeeper���ӳ�����
			for (int t = 0; t < size; ++t) {
				ServerNode target = serviceList.get(t);
				if (!target.isTemporaryFailure()) {
					return target;
				}
			}
		}
		return serverNode;
	}
}
