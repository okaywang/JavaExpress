package test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * foo...Created by wgj on 2017/4/2.
 */
public class RealHander implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke:" + method.toString());
        if (method.getReturnType() == String.class){
            return "vvvvvvvvvvvvvvvvv";
        }
        if (method.getReturnType() == Integer.class){
            return 33111;
        }
        return null;
    }
}
