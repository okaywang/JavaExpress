/**
 * Created by wgj on 2017/1/20.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello(String name) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("hello, my dear " + name);
    }
}
