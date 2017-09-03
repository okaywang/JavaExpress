package com.zhaopin.thrift.rpc.invoker;

import com.zhaopin.thrift.rpc.Constants;
import com.zhaopin.thrift.rpc.balance.LoadBalance;
import com.zhaopin.thrift.rpc.common.Invocation;

/**
 * thrift 调用环境
 *
 */
public class ThriftInvocation {
	// 调用服务相关信息
	private final Invocation invocation;
	// 调用的方法
	private final String method;
	// 调用的参数
	private final Object[] args;
	// 调用的序列化类
	private final String codecHelper;
	// 自定义服务选择策略
	private LoadBalance selector = null;
	// 灰度实际调用的服务
	private String grayVerion;
	// 服务实际调用的地址(仅仅用于日志)
	private String remoteAddr;
	// spanId
	private final long spanId;
	// parentId
	private final long parentId;
	// thrift的版本
	private int thriftVersion;
	// 是否采样
	private boolean sampled;

	public ThriftInvocation(Invocation invocation, String method, Object[] args, long spanId, long parentId) {
		this.invocation = invocation;
		this.method = method;
		this.args = args;
		// 首先对接口类路径进行
		String interf = this.invocation.getService();
		int index = interf.lastIndexOf(".");
		if (index < 0) {
			throw new IllegalStateException("" + interf + "格式错误!");
		}
		this.codecHelper = interf.substring(0, index) + "." + Constants.INVOKER_CLASS_PREFIX
				+ interf.substring(index + 1) + Constants.INVOKER_CLASS_SUFFIX + "$" + method + "_invoker";
		this.selector = invocation.getSelector();
		this.grayVerion = invocation.getVersion();
		this.spanId = spanId;
		this.parentId = parentId;
	}

	public Invocation getInvocation() {
		return invocation;
	}

	public String getMethod() {
		return method;
	}

	public Object[] getArgs() {
		return args;
	}

	public String getCodecHelper() {
		return codecHelper;
	}

	public LoadBalance getSelector() {
		return selector;
	}

	public void setSelector(LoadBalance selector) {
		this.selector = selector;
	}

	public String getGrayVersion() {
		return grayVerion;
	}

	public void setGrayVersion(String grayVerion) {
		this.grayVerion = grayVerion;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public long getSpanId() {
		return spanId;
	}

	public long getParentId() {
		return parentId;
	}

	public int getThriftVersion() {
		return thriftVersion;
	}

	public void setThriftVersion(int thriftVersion) {
		this.thriftVersion = thriftVersion;
	}

	public boolean isSampled() {
		return sampled;
	}

	public void setSampled(boolean sampled) {
		this.sampled = sampled;
	}

}
