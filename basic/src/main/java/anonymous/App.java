package anonymous;

/**
 * Created by guojun.wang on 2017/10/22.
 */
public class App {
    public static void main(String[] args) {
        MyConsumer consumer = new MyConsumer();
        String item = "msg with tag 333";
        WorkPool wp = new WorkPool();
        wp.addWorkItem("channel1", new Runnable() {
            @Override
            public void run() {
                System.out.println(1111);
                consumer.hand(item);
            }
        });
    }
}
