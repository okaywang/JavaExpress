package com.zhaopin.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThriftService {

	/*// thrift�İ汾
	public String rpcVersion() default "";*/

	// thrift�ķ���
	public String group() default "plat";

	// ���������
	public String service() default "";

	// �����http�汾
	public String version() default "1.0.0";

	// �����http�汾
	public int weight() default 5;

}
