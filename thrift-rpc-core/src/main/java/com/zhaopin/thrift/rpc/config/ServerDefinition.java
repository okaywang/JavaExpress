package com.zhaopin.thrift.rpc.config;

import com.zhaopin.thrift.rpc.Constants;

public class ServerDefinition {

	private int bossThreadCount = 0;

	private int workerThreadCount = 0;

	private int serverPort = Constants.DEFAULT_LISTEN_PORT;

	private int maxFrmSize = Constants.MAX_FRAME_SIZE;

	private int maxConnections = Constants.MAX_CONNECTION;

	private int tcpSendBufferSize = 0;

	private int tcpReceiveBufferSize = 0;

	private boolean tcpKeepAlive = true;

	private int acceptBackLog = 1024;

	public int getTcpSendBufferSize() {
		return tcpSendBufferSize;
	}

	public void setTcpSendBufferSize(int tcpSendBufferSize) {
		this.tcpSendBufferSize = tcpSendBufferSize;
	}

	public int getTcpReceiveBufferSize() {
		return tcpReceiveBufferSize;
	}

	public void setTcpReceiveBufferSize(int tcpReceiveBufferSize) {
		this.tcpReceiveBufferSize = tcpReceiveBufferSize;
	}

	public boolean isTcpKeepAlive() {
		return tcpKeepAlive;
	}

	public void setTcpKeepAlive(boolean tcpKeepAlive) {
		this.tcpKeepAlive = tcpKeepAlive;
	}

	public int getAcceptBackLog() {
		return acceptBackLog;
	}

	public void setAcceptBackLog(int acceptBackLog) {
		this.acceptBackLog = acceptBackLog;
	}

	public int getMaxFrmSize() {
		return maxFrmSize;
	}

	public void setMaxFrmSize(int maxFrmSize) {
		this.maxFrmSize = maxFrmSize;
	}

	public int getBossThreadCount() {
		return bossThreadCount;
	}

	public int getWorkerThreadCount() {
		return workerThreadCount;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setBossThreadCount(int bossThreadCount) {
		this.bossThreadCount = bossThreadCount;
	}

	public void setWorkerThreadCount(int workerThreadCount) {
		this.workerThreadCount = workerThreadCount;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

}
