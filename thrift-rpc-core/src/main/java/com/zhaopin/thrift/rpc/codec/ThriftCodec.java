package com.zhaopin.thrift.rpc.codec;

import com.zhaopin.thrift.rpc.protocol.TProtocol;

/**
 * ���л��ӿ�(thrift��������ļ�ʹ�ø���)
 *
 */
public interface ThriftCodec<T> {

	public void encode(TProtocol oprot, Object[] args);

	public T decode(TProtocol iprot);

}
