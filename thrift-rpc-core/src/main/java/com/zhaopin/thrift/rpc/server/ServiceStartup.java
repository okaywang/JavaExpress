package com.zhaopin.thrift.rpc.server;

import com.zhaopin.thrift.rpc.config.ServerDefinition;

public class ServiceStartup {

	public static ServiceStartup startup = new ServiceStartup();

	private ThriftServerTransport transport;

	protected void startService(final int servicePort) {
		// ���÷���������˿�
		// ���������Ĳ���
		ServerDefinition serverDefinition = new ServerDefinition();
		serverDefinition.setServerPort(servicePort);
		this.transport = new ThriftServerTransport(serverDefinition);
		// ����������
		this.transport.start();
	}

	public void stopService() {
		this.transport.stop();
	}
}
