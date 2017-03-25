package Mybatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * foo...Created by wgj on 2017/3/25.
 */
public class MapperProxy<T> implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperMethod mapperMethod = new MapperMethod(method);
        return mapperMethod.execute(args);
    }
}
