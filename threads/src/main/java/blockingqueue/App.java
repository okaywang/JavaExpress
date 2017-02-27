package blockingqueue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * foo...Created by wgj on 2017/2/26.
 */
public class App {
    static DelayQueue<DelayedElement> queue = new DelayQueue<>();

    public static void main(String[] args) throws InterruptedException {

        DelayedElement element = new DelayedElement("send sms", 5000);
        queue.put(element);
        System.out.println(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + " sending sms to boss");
        System.out.println(queue.take());
        System.out.println(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + " over");
    }


    public static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }
}
