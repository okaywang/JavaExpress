import java.lang.reflect.Proxy;
import java.sql.SQLSyntaxErrorException;

/**
 * Created by wgj on 2017/1/20.
 */
public class App {
    public static void main(String[] args) {
        System.setOut(System.err);

        HelloService service = new HelloServiceImpl();
        HelloService service2 = new HelloService2Impl();
        service.sayHello("lucy");

        DynamicProxy proxy = new DynamicProxy();
        service = (HelloService) proxy.bind(service, new Class[]{HelloService.class});
        service.sayHello("wgj");

        HelloService helloService = (HelloService)Proxy.newProxyInstance(App.class.getClassLoader(), new Class[] { HelloService.class }, proxy);
        helloService.sayHello("李大师");
    }


}
