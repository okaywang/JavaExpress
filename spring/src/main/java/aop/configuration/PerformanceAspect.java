package aop.configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * foo...Created by wgj on 2017/4/4.
 */
@Aspect
@Component
public class PerformanceAspect {

    @Before("execution(* *.*(..))")
    public void Begin(JoinPoint joinPoint) {
        System.out.println("begin]==============================");
    }

    @After("execution(* aop.configuration.captcha.ICaptcha.generate(..))")
    public void After(JoinPoint joinPoint) {
        System.out.println("end]==============================");
    }

    @Around("execution(* aop.configuration.*.*(..))")
    public void processText(ProceedingJoinPoint PJ) throws Throwable {
        System.out.println("执行目标方法之前，模拟开始事务。");
        PJ.proceed();
        System.out.println("执行目标方法之后，模拟结束事务。");
    }
}
