package com.zhaopin.thrift.rpc.common;

import java.util.Map;

import com.zhaopin.thrift.rpc.util.UniqueIdUtils;

public class TMessage {
	// message������(�ӿ�����·��#����)
	private final String name;
	// ��Ϣ������
	private final byte type;
	// ��Ϣ�����к�
	private final int seqid;
	// ȫ�ָ���ID
	private final String traceId;
	// �Ҷȵ�token
	private final String grayToken;
	// parentId
	private long parentId;
	// spanId
	private long spanId;
	// thrift�İ汾 (��ѡֵ��0 4)
	// 0�� Ĭ�ϰ汾 4 ��rpc�°汾 1 Ϊnodejs�汾��Ŀ����Ϊ��������ʱ�쳣ת��
	private int thriftVersion;
	// �Ƿ����
	private boolean sampled = false;
	// ����ĸ���
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
