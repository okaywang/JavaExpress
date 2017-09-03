package com.zhaopin.thrift.rpc.invoker;

import com.zhaopin.thrift.rpc.Constants;
import com.zhaopin.thrift.rpc.balance.LoadBalance;
import com.zhaopin.thrift.rpc.common.Invocation;

/**
 * thrift ���û���
 *
 */
public class ThriftInvocation {
	// ���÷��������Ϣ
	private final Invocation invocation;
	// ���õķ���
	private final String method;
	// ���õĲ���
	private final Object[] args;
	// ���õ����л���
	private final String codecHelper;
	// �Զ������ѡ�����
	private LoadBalance selector = null;
	// �Ҷ�ʵ�ʵ��õķ���
	private String grayVerion;
	// ����ʵ�ʵ��õĵ�ַ(����������־)
	private String remoteAddr;
	// spanId
	private final long spanId;
	// parentId
	private final long parentId;
	// thrift�İ汾
	private int thriftVersion;
	// �Ƿ����
	private boolean sampled;

	public ThriftInvocation(Invocation invocation, String method, Object[] args, long spanId, long parentId) {
		this.invocation = invocation;
		this.method = method;
		this.args = args;
		// ���ȶԽӿ���·������
		String interf = this.invocation.getService();
		int index = interf.lastIndexOf(".");
		if (index < 0) {
			throw new IllegalStateException("" + interf + "��ʽ����!");
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
