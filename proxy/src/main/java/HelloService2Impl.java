/**
 * foo...Created by wgj on 2017/3/25.
 */
public class HelloService2Impl implements HelloService {
    @Override
    public void sayHello(String name) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("滚滚滚滚滚滚滚滚滚滚滚滚滚滚滚滚滚滚滚滚滚滚 " + name);
    }
}
