package com.zhaopin.thrift.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.zhaopin.common.ThriftContext;
import com.zhaopin.common.container.RequestID;
import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.common.trace.ZipkinContext;
import com.zhaopin.thrift.rpc.common.Invocation;
import com.zhaopin.thrift.rpc.invoker.AsyncInvoker;
import com.zhaopin.thrift.rpc.invoker.ClusterStrategy;
import com.zhaopin.thrift.rpc.invoker.FailFastInvoker;
import com.zhaopin.thrift.rpc.invoker.FailOverInvoker;
import com.zhaopin.thrift.rpc.invoker.FailSafeInvoker;
import com.zhaopin.thrift.rpc.invoker.ThriftInvocation;
import com.zhaopin.thrift.rpc.invoker.ThriftInvoker;
import com.zhaopin.thrift.rpc.util.InvokeChainUtil;
import com.zhaopin.thrift.rpc.util.SnowFlake;
import com.zhaopin.thrift.rpc.util.UniqueIdUtils;
import com.zhaopin.thrift.tool.service.IThriftCommand;

import brave.Span.Kind;
import brave.internal.HexCodec;
import brave.Tracer;
import brave.propagation.TraceContext;

public class InvokerInvocationHandler implements InvocationHandler {

	public static Logger LOGGER = LoggerFactory.getLogger(InvokerInvocationHandler.class);
	// 服务调用相关信息
	private final Invocation invocation;
	// 远程调用
	private final ThriftInvoker invoker;

	public InvokerInvocationHandler(Invocation invocation) {
		this.invocation = invocation;
		if (invocation.getAsync() == 1) {
			this.invoker = new AsyncInvoker();
		} else if (ClusterStrategy.failover == invocation.getStrategy()) {
			this.invoker = new FailOverInvoker();
		} else if (ClusterStrategy.failfast == invocation.getStrategy()) {
			this.invoker = new FailFastInvoker();
		} else if (ClusterStrategy.failsafe == invocation.getStrategy()) {
			this.invoker = new FailSafeInvoker();
		} else {
			this.invoker = new FailOverInvoker();
		}
	}

	/**
	 * 反射拦截点
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		initTraceEnv();
		// 对于基类Object中的方法，需要单独处理，无需RPC调用
		String methodName = method.getName();
		Class<?>[] parameterTypes = method.getParameterTypes();
		if ("toString".equals(methodName) && parameterTypes.length == 0) {
			return invokeToString(method);
		}
		if ("hashCode".equals(methodName) && parameterTypes.length == 0) {
			return invokeHashCode(method);
		}
		if ("equals".equals(methodName) && parameterTypes.length == 1) {
			return invocation.equals(args[0]);
		}
		// 判断是否已经开启
		brave.Span client = null;
		boolean sampled = false;
		long spanId = 0, parentId = 0;
		if (ZipkinContext.inst.isInit()) {
			Tracer tracer = ZipkinContext.inst.getTracing().tracer();
			// 判断当前的span是否为空
			brave.Span current = tracer.currentSpan();
			long traceIdHigh = 0, traceIdLow = 0;
			if (current != null) {
				client = tracer.newChild(current.context()).name(invocation.getService()).start();
			} else {
				String requestId = RequestID.getRequestID();
				// 直接是客户端调用，需要生成
				if (StringUtils.isEmpty(requestId)) {
					requestId = UniqueIdUtils.generate();
					RequestID.setRequestID(requestId);
					traceIdHigh = HexCodec.lowerHexToUnsignedLong(requestId.substring(0, 16));
					traceIdLow = HexCodec.lowerHexToUnsignedLong(requestId.substring(16, 32));
					RequestID.setRequestID(requestId);
				} else {
					if (requestId.contains("-")) {
						LOGGER.warn("the trace id contain '-' character!");
					} else if (requestId.length() < 32) {
						LOGGER.warn("the trace id's length is less than 32 characters!");
					} else {
						traceIdHigh = HexCodec.lowerHexToUnsignedLong(requestId.substring(0, 16));
						traceIdLow = HexCodec.lowerHexToUnsignedLong(requestId.substring(16, 32));
					}
				}
				sampled = ZipkinContext.inst.getSampler().isSampled(traceIdLow);
				TraceContext traceCtx = null;
				traceCtx = brave.propagation.TraceContext.newBuilder().traceId(traceIdLow).traceIdHigh(traceIdHigh)
						.spanId(SnowFlake.instance.nextId()).parentId(null).sampled(sampled).build();
				client = tracer.toSpan(traceCtx).name(invocation.getService()).start();
			}
			client.kind(Kind.CLIENT);
			spanId = client.context().spanId();
			if (client.context().parentId() == null) {
				parentId = 0;
			} else {
				parentId = client.context().parentId();
			}
		}
		ThriftInvocation thriftInv = new ThriftInvocation(invocation, method.getName(), args, spanId, parentId);
		if (client != null && client.context() != null && client.context().sampled() != null) {
			sampled = client.context().sampled();
		}
		thriftInv.setSampled(sampled);
		// 调用远程服务
		logBeforeInvoke(methodName, args, thriftInv);
		try {
			Object result = invoker.invoke(thriftInv);
			logAfterInvoke(result, thriftInv.getGrayVersion(), thriftInv.getRemoteAddr(), thriftInv);
			// 调用链分析用途
			if (!StringUtils.isEmpty(thriftInv.getGrayVersion())) {
				InvokeChainUtil.mutateInvokeChain(RequestID.getRequestID(), invocation.getGroup(),
						invocation.getService(), thriftInv.getGrayVersion(), methodName);
			} else {
				InvokeChainUtil.mutateInvokeChain(RequestID.getRequestID(), invocation.getGroup(),
						invocation.getService(), invocation.getVersion(), methodName);
			}
			return result;
		} catch (Exception exp) {
			logAfterError(methodName, args, exp, spanId, parentId);
			throw exp;
		} finally {
			if (client != null) {
				client.finish();
			}
		}
	}

	private void logBeforeInvoke(String methodName, Object[] args, ThriftInvocation thriftInv) {
		if (LOGGER.isInfoEnabled()) {
			JSONObject invLog = new JSONObject();
			invLog.put("group", invocation.getGroup());
			invLog.put("service", invocation.getService());
			invLog.put("version", invocation.getVersion());
			invLog.put("method", methodName);
			invLog.put("traceId", RequestID.getRequestID());
			invLog.put("gray_token", ThriftContext.getGrayToken());
			invLog.put("param", args);
			invLog.put("spanId", HexCodec.toLowerHex(thriftInv.getSpanId()));
			invLog.put("parentId", HexCodec.toLowerHex(thriftInv.getParentId()));
			LOGGER.info("{\"thrift client request\":{}}", invLog);
		}
	}

	private void logAfterInvoke(final Object result, String targetVersion, String remoteAddr,
			ThriftInvocation thriftInv) {
		if (LOGGER.isInfoEnabled() && isRecordLog(invocation.getService())) {
			final long cost = System.currentTimeMillis() - RequestID.getRequestTime();
			JSONObject invLog = new JSONObject();
			invLog.put("ExecuteTime", cost);
			invLog.put("traceId", RequestID.getRequestID());
			invLog.put("gray_token", ThriftContext.getGrayToken());
			invLog.put("Response", result);
			invLog.put("service", this.invocation.getService());
			invLog.put("version", targetVersion);
			invLog.put("remoteAddr", remoteAddr);
			invLog.put("spanId", HexCodec.toLowerHex(thriftInv.getSpanId()));
			invLog.put("parentId", HexCodec.toLowerHex(thriftInv.getParentId()));
			invLog.put("method", thriftInv.getMethod());
			LOGGER.info("{\"thrift client response\":{}}", invLog);
		}
	}

	private boolean isRecordLog(String service) {
		if (service != null) {
			if (service.startsWith(IThriftCommand.class.getPackage().getName())) {
				return false;
			}
		}
		return true;
	}

	private void logAfterError(String methodName, Object[] args, Exception exp, long spanId, long parentId) {
		if (LOGGER.isInfoEnabled() && isRecordLog(invocation.getService())) {
			final long cost = System.currentTimeMillis() - RequestID.getRequestTime();
			JSONObject invLog = new JSONObject();
			invLog.put("method", methodName);
			invLog.put("ExecuteTime", cost);
			invLog.put("traceId", RequestID.getRequestID());
			invLog.put("gray_token", ThriftContext.getGrayToken());
			invLog.put("param", args);
			invLog.put("spanId", spanId);
			invLog.put("parentId", parentId);
			LOGGER.error("{\"thrift client exception\": {}", invLog, exp);
		}
	}

	private void initTraceEnv() {
		String traceId = RequestID.getRequestID();
		if (StringUtils.isEmpty(traceId)) {
			traceId = UUID.randomUUID().toString().replace("-", "");
		}
		RequestID.setRequestID(traceId);
	}

	private String invokeToString(Method method) {
		return "invoker: " + invocation.getService() + "." + method.getName() + "#" + invocation.getVersion();
	}

	private int invokeHashCode(Method method) {
		return ("invoker: " + invocation.getService() + "." + method.getName() + "#" + invocation.getVersion())
				.hashCode();
	}
}
