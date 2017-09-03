package com.zhaopin.thrift.rpc.common;

import java.util.HashMap;
import java.util.Map;

import com.zhaopin.thrift.rpc.codec.ThriftCodec;

public class ThriftRequest {
	// 请求的ID
	private final int seqid;
	// 请求的服务
	private final String service;
	// 序列化和反序列化类
	private final ThriftCodec<?> thriftCodec;
	// 请求的参数
	private final Object[] args;
	// 全局跟踪ID
	private final String traceId;
	// 灰度的token
	private final String grayToken;
	// zipkin的spanId
	private long spanId;
	// zipkin的parentId
	private long parentId;
	// thrift的版本
	private int thriftVersion;
	// 是否采样
	private boolean sampled;
	// 请求的附件
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
	 * 添加请求的附件
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
