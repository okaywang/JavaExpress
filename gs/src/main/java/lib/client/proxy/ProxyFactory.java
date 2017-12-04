package lib.client.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by wangguojun01 on 2017/12/4.
 */
public class ProxyFactory {
    public static <T> T create(Class<?> type, String strUrl) {
        String serviceName = "";
        String lookup = "";
        strUrl = strUrl.replace("tcp://", "");
        String[] splits = strUrl.split("/");
        if(splits.length == 2) {
            serviceName = splits[0];
            lookup = splits[1];
        }
        InvocationHandler handler = new ProxyStandard(type, serviceName, lookup, false);
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{type}, handler);
    }
}
