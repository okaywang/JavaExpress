package com.zhaopin.thrift.rpc.invoker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.zhaopin.common.ThriftContext;
import com.zhaopin.common.container.RequestID;
import com.zhaopin.rpc.exception.RpcTransportException;
import com.zhaopin.thrift.rpc.RpcContext;
import com.zhaopin.thrift.rpc.codec.ThriftCodec;
import com.zhaopin.thrift.rpc.common.ServerNode;
import com.zhaopin.thrift.rpc.common.ThriftRequest;
import com.zhaopin.thrift.rpc.exception.RpcException;
import com.zhaopin.thrift.rpc.exception.TTransportException;
import com.zhaopin.thrift.rpc.monitor.ConsumerRegister;
import com.zhaopin.thrift.rpc.transport.ThriftFutures;

import brave.internal.HexCodec;

import com.zhaopin.thrift.rpc.transport.DefaultThriftFuture;
import com.zhaopin.thrift.rpc.transport.ThriftChannels;
import com.zhaopin.thrift.rpc.transport.ThriftChannel;
import com.zhaopin.thrift.rpc.transport.ThriftFuture;

public class DefaultInvoker extends AbstractInvoker {

	public static Map<String, ThriftCodec<?>> codecCache = new HashMap<String, ThriftCodec<?>>();

	public static final AtomicInteger ChannelIndex = new AtomicInteger(0);

	@Override
	public Object invoke(ThriftInvocation invoker) throws Throwable {
		ServerNode serverNode = selectProvider(invoker);
		// �汾���ݣ���Ҫ����Զ�˷����thrift�汾
		invoker.setThriftVersion(serverNode.getThriftVersion());
		// ���÷����ʵ�ʵ�ַ
		invoker.setRemoteAddr(serverNode.getHost() + ":" + serverNode.getPort());
		// ���л���Ϣ
		ThriftCodec<?> thriftCodec = getThriftCodec(invoker.getCodecHelper());
		// ��ȡ�����
		String service = invoker.getInvocation().getService();
		String method = invoker.getMethod();
		final int seqid = getSeqId(false);
		// thrift������������ϵ��ID
		ThriftRequest thriftRequest = new ThriftRequest(seqid, service + ":" + method, thriftCodec, invoker.getArgs(),
				RequestID.getRequestID(), ThriftContext.getGrayToken(), invoker.getSpanId(), invoker.getParentId());
		// ����thrift�İ汾
		thriftRequest.setThriftVersion(invoker.getThriftVersion());
		// ���thrift�ĸ���
		thriftRequest.addAttach(ThriftContext.getAttach());
		// �����Ƿ����
		thriftRequest.setSampled(invoker.isSampled());
		ThriftFuture<Object> future = new DefaultThriftFuture(thriftRequest);
		final int channelIndex = getChannelIndex();
		try {
			ThriftChannel thriftChannel = ThriftChannels.connect(serverNode, channelIndex);
			if (thriftChannel == null) {
				throw new TTransportException(serverNode);
			}
			Object thriftResponse = null;
			ThriftFutures.instance.store(seqid, future, thriftChannel.getChannelId());
			thriftChannel.send(thriftRequest);
			if (thriftChannel.isCreate()) {
				// ��Ҫע��consumer
				ConsumerRegister.register(invoker.getInvocation().getGroup(), service, invoker.getGrayVersion());
			}
			long timeout = invoker.getInvocation().getTimeout();
			if (timeout < 0) {
				thriftResponse = future.getResponse();
			} else {
				thriftResponse = future.getResponse(timeout);
			}
			return thriftResponse;
		} catch (TTransportException cause) {
			String remoteAddr = invoker.getRemoteAddr();
			LOGGER.error("transport exception {} {} {}", remoteAddr, invoker.getGrayVersion(),
					HexCodec.toLowerHex(invoker.getSpanId()), cause);
			// һ������transport���쳣, �Ƴ�������
			// ��Ǹ÷���Ϊ��ʱʧЧ�ڵ�,����ܵ�ԭ���Ƿ����ṩ��崻�������zookeeper��û��֪ͨ��,�����ʱ����ڣ���Ǹ÷���Ϊ��ʱʧ��
			serverNode.setTemporaryFailure();
			ThriftChannels.disconnect(serverNode, channelIndex);
			throw cause;
		} catch (RpcException cause) {
			if (cause.getCause() instanceof TTransportException) {
				String reason = "invoke " + service + ":" + method + " " + invoker.getRemoteAddr() + " fail!";
				throw new RpcTransportException(reason, cause.getCause());
			} else if (cause.getCause() instanceof org.apache.thrift.transport.TTransportException) {
				String reason = "invoke " + service + ":" + method + " " + invoker.getRemoteAddr() + " fail!";
				throw new RpcTransportException(reason, cause.getCause());
			}
			throw cause.getCause();
		}
	}

	protected int getChannelIndex() {
		int index = ChannelIndex.getAndIncrement() % RpcContext.channels;
		if (index > 1000000) {
			ChannelIndex.set(0);
		}
		return index;
	}

	protected ThriftCodec<?> getThriftCodec(String codecHelper) {
		ThriftCodec<?> thriftCodec = codecCache.get(codecHelper);
		if (thriftCodec != null) {
			return thriftCodec;
		}
		try {
			Class<?> cls = Thread.currentThread().getContextClassLoader().loadClass(codecHelper);
			thriftCodec = (ThriftCodec<?>) cls.newInstance();
			codecCache.put(codecHelper, thriftCodec);
			return thriftCodec;
		} catch (ClassNotFoundException exp) {
			LOGGER.error("exception", exp);
			throw new RpcException("" + codecHelper + " do not exist", exp);
		} catch (IllegalAccessException exp) {
			LOGGER.error("exception", exp);
			throw new RpcException("" + codecHelper + " access fail", exp);
		} catch (InstantiationException exp) {
			LOGGER.error("exception", exp);
			throw new RpcException("" + codecHelper + " instant fail", exp);
		}
	}

	protected void logInfo(Throwable error, ThriftInvocation invoker) {
		LOGGER.info("thrift invoke exception of {}", HexCodec.toLowerHex(invoker.getSpanId()), error);
	}

	protected void logError(Throwable error, ThriftInvocation invoker) {
		LOGGER.info("thrift invoke exception of {}", HexCodec.toLowerHex(invoker.getSpanId()), error);
	}
}
