package com.zhaopin.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zhaopin.thrift.rpc.Constants;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThriftReference {
	// 服务的版本
	public String version();

	// 重试的次数
	public int retry() default Constants.DEF_RETRY;

	// 重试的次数
	public long timeout() default Constants.DEF_CALL_WAIT_TIME;

	// 服务的分组
	public String group() default Constants.DEF_GROUP;

	// 服务直连地址(用于调试的目的)
	public String target() default "";

	// 轮询策略
	public String round() default Constants.TOKEN_BASED;

	// 客户端容错策略
	public String strategy() default Constants.CLUSTER_FAILOVER;

	// 服务自定义选择器
	public String selector() default "";

}
