package com.zhaopin.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zhaopin.thrift.rpc.Constants;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThriftReference {
	// ����İ汾
	public String version();

	// ���ԵĴ���
	public int retry() default Constants.DEF_RETRY;

	// ���ԵĴ���
	public long timeout() default Constants.DEF_CALL_WAIT_TIME;

	// ����ķ���
	public String group() default Constants.DEF_GROUP;

	// ����ֱ����ַ(���ڵ��Ե�Ŀ��)
	public String target() default "";

	// ��ѯ����
	public String round() default Constants.TOKEN_BASED;

	// �ͻ����ݴ����
	public String strategy() default Constants.CLUSTER_FAILOVER;

	// �����Զ���ѡ����
	public String selector() default "";

}
