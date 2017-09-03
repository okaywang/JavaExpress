package com.zhaopin.thrift.rpc.processor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.zhaopin.common.ThriftContext;
import com.zhaopin.common.container.RequestID;
import com.zhaopin.rpc.annotation.ThriftService;
import com.zhaopin.thrift.rpc.Constants;
import com.zhaopin.thrift.rpc.RpcContext;
import com.zhaopin.thrift.rpc.common.TMessage;
import com.zhaopin.thrift.rpc.util.VersionUtils;
import com.zhaopin.thrift.rpc.validate.IValidator;
import com.zhaopin.thrift.rpc.validate.ThriftValidator;

import brave.internal.HexCodec;

public abstract class AbstractThriftProcessor implements TProcessor {
	// �ӿڷ����Ļ���
	private final Map<String, Method> methodMapping = new HashMap<String, Method>();
	// ������У��
	private final IValidator validator;
	// ����ķ���
	private String group;
	// ���õķ���
	private String service;
	// ����İ汾
	private String version;
	// ʵ���������
	private String implClass = null;

	/**
	 * ��������ĳ������
	 * 
	 * @param interfaceClass
	 *            �ӿڵ������
	 */
	public AbstractThriftProcessor(Class<?> interfaceClass, Object target) {
		Class<?> realClass = AopProxyUtils.ultimateTargetClass(target);
		this.service = interfaceClass.getName();
		this.validator = new ThriftValidator();
		Method[] methods = interfaceClass.getMethods();
		for (Method method : methods) {
			if (!match(method)) {
				continue;
			}
			Method realMethod = null;
			try {
				realMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
			} catch (Exception exp) {
				throw new IllegalStateException("��ȡ" + realClass + "�ķ���" + method.getName() + "�쳣!");
			}
			this.methodMapping.put(method.getName(), realMethod);
		}
		ThriftService thriftService = realClass.getAnnotation(ThriftService.class);
		if (thriftService != null) {
			this.version = VersionUtils.getRpcVersion(thriftService.version());
			this.group = thriftService.group();
			if (StringUtils.isEmpty(this.group)) {
				this.group = Constants.DEF_GROUP;
			}
			this.implClass = realClass.getName();
		}
	}

	/**
	 * �жϷ����Ƿ���Ҫ����
	 * 
	 * @param method
	 *            �ӿڵķ���
	 * @return
	 */
	private boolean match(Method method) {
		if ("toString".equals(method.getName()) && method.getParameterTypes().length == 0) {
			return false;
		} else if ("hashCode".equals(method.getName()) && method.getParameterTypes().length == 0) {
			return false;
		} else if ("equals".equals(method.getName()) && method.getParameterTypes().length == 1) {
			return false;
		}
		return true;
	}

	/**
	 * �Է����Ĳ�������У��
	 * 
	 * @param wraper
	 *            ʵ����Ĵ������
	 * @param methodName
	 *            ����������
	 * @param params
	 *            �����б�
	 */
	public void validate(Object target, String methodName, Object[] params) {
		// ��ӡ�������
		logBeforeInvoke(methodName, params);
		if (RpcContext.thriftMethodCheck) {
			Method method = getMethodByName(methodName);
			if (method == null) {
				throw new IllegalArgumentException("" + methodName + " û����Ӧ�ķ�������!");
			}
			this.validator.validate(target, method, params);
		}
	}

	protected void logBeforeInvoke(String methodName, Object[] args) {
		if (LOGGER.isInfoEnabled()) {
			JSONObject invLog = new JSONObject();
			invLog.put("group", this.group);
			invLog.put("service", this.service);
			invLog.put("version", this.version);
			invLog.put("impl", this.implClass);
			invLog.put("method", methodName);
			invLog.put("traceId", RequestID.getRequestID());
			invLog.put("gray_token", ThriftContext.getGrayToken());
			invLog.put("param", args);
			invLog.put("Client", ThriftContext.getClientAddr());
			invLog.put("spanId", HexCodec.toLowerHex(ThriftContext.getSpanId()));
			invLog.put("parentId", HexCodec.toLowerHex(ThriftContext.getParentId()));
			LOGGER.info("{\"thrift service request\":{}}", invLog);
		}
	}

	protected void logAfterInvoke(Object result, TMessage msg) {
		if (LOGGER.isInfoEnabled()) {
			final long cost = System.currentTimeMillis() - RequestID.getRequestTime();
			JSONObject invLog = new JSONObject();
			invLog.put("ExecuteTime", cost);
			invLog.put("group", getGroup());
			invLog.put("service", getService());
			invLog.put("version", getVersion());
			invLog.put("traceId", RequestID.getRequestID());
			invLog.put("gray_token", ThriftContext.getGrayToken());
			invLog.put("Response", result);
			invLog.put("spanId", HexCodec.toLowerHex(ThriftContext.getSpanId()));
			invLog.put("parentId", HexCodec.toLowerHex(ThriftContext.getParentId()));
			invLog.put("method", msg.getName());
			LOGGER.info("{\"thrift service response\":{}}", invLog);
		}
	}

	/**
	 * ��ȡ�ӿڵķ���
	 * 
	 * @param methodName
	 *            ��������
	 * @return
	 */
	public Method getMethodByName(String methodName) {
		return methodMapping.get(methodName);
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
