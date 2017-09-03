package com.zhaopin.thrift.rpc.common;

import java.util.Map;

import com.zhaopin.thrift.rpc.util.UniqueIdUtils;

public class TMessage {
	// message的名称(接口完整路径#方法)
	private final String name;
	// 消息的类型
	private final byte type;
	// 消息的序列号
	private final int seqid;
	// 全局跟踪ID
	private final String traceId;
	// 灰度的token
	private final String grayToken;
	// parentId
	private long parentId;
	// spanId
	private long spanId;
	// thrift的版本 (可选值是0 4)
	// 0是 默认版本 4 是rpc新版本 1 为nodejs版本，目的是为了让运行时异常转换
	private int thriftVersion;
	// 是否采样
	private boolean sampled = false;
	// 请求的附件
	private Map<String, String> attachment;

	public TMessage(String name, byte type, int seqid) {
		this(name, type, seqid, null);
	}

	public TMessage(String name, byte type, int seqid, String traceId) {
		this(name, type, seqid, traceId, "");
	}

	public TMessage(String name, byte type, int seqid, String traceId, String grayToken) {
		this(name, type, seqid, traceId, grayToken, 0);
	}

	public TMessage(String name, byte type, int seqid, String traceId, String grayToken, long parentId) {
		this(name, type, seqid, traceId, grayToken, 0, 0);
	}

	public TMessage(String name, byte type, int seqid, String traceId, String grayToken, long parentId, long spanId) {
		this.name = name;
		this.type = type;
		this.seqid = seqid;
		if (traceId == null) {
			this.traceId = UniqueIdUtils.generate();
		} else {
			this.traceId = traceId;
		}
		if (grayToken == null) {
			grayToken = "";
		}
		this.grayToken = grayToken;
		this.parentId = parentId;
		this.spanId = spanId;
	}

	public TMessage(TMessage msg) {
		this(msg.getName(), msg.getType(), msg.getSeqid(), msg.getTraceId(), msg.getGrayToken(), msg.getParentId(),
				msg.getSpanId());
	}

	public TMessage(TMessage msg, byte type) {
		this(msg.getName(), type, msg.getSeqid(), msg.getTraceId(), msg.getGrayToken(), msg.getParentId(),
				msg.getSpanId());
		this.thriftVersion = msg.getThriftVersion();
		this.sampled = msg.isSampled();
	}

	public String getName() {
		return name;
	}

	public byte getType() {
		return type;
	}

	public int getSeqid() {
		return seqid;
	}

	public String getTraceId() {
		return traceId;
	}

	public String getGrayToken() {
		return grayToken;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public long getSpanId() {
		return spanId;
	}

	public void setSpanId(long spanId) {
		this.spanId = spanId;
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

	public void setAttachment(Map<String, String> attachment) {
		this.attachment = attachment;
	}
}
