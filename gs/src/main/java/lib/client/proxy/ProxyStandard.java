package lib.client.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by wangguojun01 on 2017/12/4.
 */
public class ProxyStandard implements InvocationHandler {
    private Class<?> interfaceClass;
    private String serviceName;
    private String lookup;
    private boolean async;
    public ProxyStandard(Class<?> type, String serviceName, String lookup, boolean async) {
        this.interfaceClass = type;
        this.serviceName = serviceName;
        this.lookup =  lookup;
        this.async = async;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return new Object();
    }
}
