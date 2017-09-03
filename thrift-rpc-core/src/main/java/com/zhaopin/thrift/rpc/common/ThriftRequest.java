package com.zhaopin.thrift.rpc.common;

import java.util.HashMap;
import java.util.Map;

import com.zhaopin.thrift.rpc.codec.ThriftCodec;

public class ThriftRequest {
	// �����ID
	private final int seqid;
	// ����ķ���
	private final String service;
	// ���л��ͷ����л���
	private final ThriftCodec<?> thriftCodec;
	// ����Ĳ���
	private final Object[] args;
	// ȫ�ָ���ID
	private final String traceId;
	// �Ҷȵ�token
	private final String grayToken;
	// zipkin��spanId
	private long spanId;
	// zipkin��parentId
	private long parentId;
	// thrift�İ汾
	private int thriftVersion;
	// �Ƿ����
	private boolean sampled;
	// ����ĸ���
	private Map<String, String> attachment = new HashMap<String, String>();

	public ThriftRequest(int seqid, String service, ThriftCodec<?> thriftCodec, Object[] args, String traceId) {
		this(seqid, service, thriftCodec, args, traceId, "");
	}

	public ThriftRequest(int seqid, String service, ThriftCodec<?> thriftCodec, Object[] args, String traceId,
			String grayToken) {
		this(seqid, service, thriftCodec, args, traceId, grayToken, 0);
	}

	public ThriftRequest(int seqid, String service, ThriftCodec<?> thriftCodec, Object[] args, String traceId,
			String grayToken, long spanId) {
		this(seqid, service, thriftCodec, args, traceId, grayToken, spanId, 0);
	}

	public ThriftRequest(int seqid, String service, ThriftCodec<?> thriftCodec, Object[] args, String traceId,
			String grayToken, long spanId, long parentId) {
		this.seqid = seqid;
		this.service = service;
		this.thriftCodec = thriftCodec;
		this.args = args;
		if (traceId == null) {
			traceId = "";
		}
		this.traceId = traceId;
		if (grayToken == null) {
			grayToken = "";
		}
		this.grayToken = grayToken;
		this.spanId = spanId;
		this.parentId = parentId;
	}

	/**
	 * �������ĸ���
	 * 
	 * @param attachment
	 */
	public void addAttach(Map<String, String> attachment) {
		if (attachment != null && attachment.size() > 0) {
			this.attachment.putAll(attachment);
		}
	}

	public int getSeqid() {
		return seqid;
	}

	public String getService() {
		return service;
	}

	public ThriftCodec<?> getThriftCodec() {
		return thriftCodec;
	}

	public Object[] getArgs() {
		return args;
	}

	public String getTraceId() {
		return traceId;
	}

	public String getGrayToken() {
		return grayToken;
	}

	public long getSpanId() {
		return spanId;
	}

	public void setSpanId(long spanId) {
		this.spanId = spanId;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
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

	public Map<String, String> getAttachment() {
		return attachment;
	}

}
