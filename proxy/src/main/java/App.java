import java.sql.SQLSyntaxErrorException;

/**
 * Created by wgj on 2017/1/20.
 */
public class App {
    public static void main(String[] args) {
        System.setOut(System.err);

        HelloService service = new HelloServiceImpl();
        service.sayHello("lucy");

        DynamicProxy proxy = new DynamicProxy();
        service = (HelloService) proxy.bind(service, new Class[]{HelloService.class});
        service.sayHello("wgj");
        
    }
}
