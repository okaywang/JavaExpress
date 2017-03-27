/**
 * Created by wgj on 2017/1/20.
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        System.setOut(System.err);

        HelloService service = new HelloServiceImpl();
//        HelloService service2 = new HelloService2Impl();
        service.sayHello("lucy");

        HelloService service1 = (HelloService) new LoggingProxy().bind(service, new Class[]{HelloService.class});
        service1.sayHello("wgj 11111111111111111111");

        HelloService service2 = (HelloService) new PerformanceProxy().bind(service, new Class[]{HelloService.class});
        service2.sayHello("wgj 2222222222222222222222");

        System.out.println(service1.getClass());
        System.out.println(service2.getClass());

        Thread.sleep(1000000);
        service1.sayHello("vvvvvvvvvvvvvvvvvvvvvvvvvv");
//        HelloService helloService = (HelloService)Proxy.newProxyInstance(App.class.getClassLoader(), new Class[] { HelloService.class }, proxy);
//        helloService.sayHello("李大师");
    }


}
