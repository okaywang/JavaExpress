package com.zhaopin.thrift.rpc.server;

import com.zhaopin.thrift.rpc.config.ServerDefinition;

public class ServiceStartup {

	public static ServiceStartup startup = new ServiceStartup();

	private ThriftServerTransport transport;

	protected void startService(final int servicePort) {
		// 设置服务的启动端口
		// 构造启动的参数
		ServerDefinition serverDefinition = new ServerDefinition();
		serverDefinition.setServerPort(servicePort);
		this.transport = new ThriftServerTransport(serverDefinition);
		// 启动服务器
		this.transport.start();
	}

	public void stopService() {
		this.transport.stop();
	}
}
