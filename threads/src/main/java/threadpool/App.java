package threadpool;

import wait_notify_blocking_queue.BlockingQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * foo...Created by wgj on 2017/4/20.
 */
public class App {
    public static void main(String[] args) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        Executors.newFixedThreadPool(10);


        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            cachedThreadPool.execute(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " begin ...");
                    System.out.println(finalI);
                    try {
                        Thread.sleep(100 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " end ...");
                }
            });
        }
    }
}
