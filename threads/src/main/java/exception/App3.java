package exception;

import java.util.Hashtable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by Administrator on 2017/9/22.
 */
public class App3 {
    public static void main(String[] args) {
        final CountDownLatch endLatch = new CountDownLatch(2);
        endLatch.countDown();
        System.out.println(endLatch.getCount());
        endLatch.countDown();
        System.out.println(endLatch.getCount());
        endLatch.countDown();
        System.out.println(endLatch.getCount());
        endLatch.countDown();
        System.out.println(endLatch.getCount());



//        ExecutorService exec = Executors.newCachedThreadPool(new HanlderThreadFactory());
//        exec.execute(new ExceptionThread());
    }
}
