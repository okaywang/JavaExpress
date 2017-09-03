package com.zhaopin.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThriftService {

	/*// thrift的版本
	public String rpcVersion() default "";*/

	// thrift的分组
	public String group() default "plat";

	// 服务的名称
	public String service() default "";

	// 服务的http版本
	public String version() default "1.0.0";

	// 服务的http版本
	public int weight() default 5;

}
