package com.zhaopin.thrift.rpc.invoker;

import com.zhaopin.common.ThriftContext;
import com.zhaopin.common.container.RequestID;
import com.zhaopin.thrift.rpc.async.AsyncMethodCallback;
import com.zhaopin.thrift.rpc.codec.ThriftCodec;
import com.zhaopin.thrift.rpc.common.ServerNode;
import com.zhaopin.thrift.rpc.common.ThriftRequest;
import com.zhaopin.thrift.rpc.exception.TTransportException;
import com.zhaopin.thrift.rpc.transport.AsyncThriftFuture;
import com.zhaopin.thrift.rpc.transport.ThriftFutures;
import com.zhaopin.thrift.rpc.transport.ThriftChannels;
import com.zhaopin.thrift.rpc.transport.ThriftChannel;

public class AsyncInvoker extends DefaultInvoker {

	@Override
	public Object invoke(ThriftInvocation invoker) throws Throwable {
		// �ڷ��������쳣������£���Ҫ����,�������͵Ĵ����糬ʱ�쳣,���׳�
		Throwable fail = null;
		int retry = 0;
		while (retry < invoker.getInvocation().getRetryCount()) {
			try {
				doInvoke(invoker);
				return null;
			} catch (TTransportException exp) {
				fail = exp;
				LOGGER.info("exception", exp);
			} catch (Throwable exp) {
				fail = exp;
				break;
			}
			++retry;
		}
		LOGGER.error("[thrift]exception", fail);
		throw fail;
	}

	private void doInvoke(ThriftInvocation invoker) {
		ServerNode serverNode = selectProvider(invoker);
		// ���л���Ϣ
		ThriftCodec<?> thriftCodec = getThriftCodec(invoker.getCodecHelper());
		// ��ȡ�����
		String service = invoker.getInvocation().getService();
		String method = invoker.getMethod();
		int seqid = getSeqId(true);
		// �첽�������
		Object[] args = new Object[invoker.getArgs().length - 1];
		final int len = invoker.getArgs().length;
		for (int t = 0; t < len - 1; ++t) {
			args[t] = invoker.getArgs()[t];
		}
		@SuppressWarnings("unchecked")
		AsyncMethodCallback<Object> callback = (AsyncMethodCallback<Object>) invoker.getArgs()[len - 1];
		ThriftRequest thriftRequest = new ThriftRequest(seqid, service + ":" + method, thriftCodec, args,
				RequestID.getRequestID(), ThriftContext.getGrayToken());
		AsyncThriftFuture future = new AsyncThriftFuture(thriftRequest, callback);
		final int channelIndex = getChannelIndex();
		try {
			ThriftChannel conn = ThriftChannels.connect(serverNode.getHost(), serverNode.getPort(), channelIndex);
			if (conn == null) {
				throw new TTransportException(serverNode);
			}
			ThriftFutures.instance.store(seqid, future, conn.getChannelId());
			// ��Ŀ���ַ������Ϣ
			conn.send(thriftRequest);
		} catch (TTransportException exp) {
			ThriftChannels.disconnect(serverNode, channelIndex);
			throw exp;
		}
	}
}
