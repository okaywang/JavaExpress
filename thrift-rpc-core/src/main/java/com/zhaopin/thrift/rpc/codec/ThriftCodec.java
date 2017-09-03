package com.zhaopin.thrift.rpc.codec;

import com.zhaopin.thrift.rpc.protocol.TProtocol;

/**
 * 序列化接口(thrift解析后的文件使用该类)
 *
 */
public interface ThriftCodec<T> {

	public void encode(TProtocol oprot, Object[] args);

	public T decode(TProtocol iprot);

}
