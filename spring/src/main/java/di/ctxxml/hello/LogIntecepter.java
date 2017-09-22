package di.ctxxml.hello;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/9/22.
 */
public class LogIntecepter implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("afterReturning");
        System.out.println("\t method:" + method);
        System.out.println("\t returnValue:" + returnValue);
    }
}
