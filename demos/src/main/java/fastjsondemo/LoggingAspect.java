package fastjsondemo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * foo...Created by wgj on 2017/8/20.
 */
@Aspect
@Component
public class LoggingAspect {
    @Around("execution(* fastjsondemo.ZpFoo.*(..))")
    public void processText(ProceedingJoinPoint PJ) throws Throwable {
        System.out.println("-------------------begin " + PJ.getSignature().getName() + "----------------------");
        PJ.proceed();
        System.out.println("-------------------end " + PJ.getSignature().getName() + "----------------------");
    }
}
