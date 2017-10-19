package future;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by guojun.wang on 2017/10/18.
 */
public class AppFuture {
    public static Integer getValue() throws Exception {
        Thread.sleep(10);
        return new Random().nextInt(100);
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            //       call1();
           call2();;
        }
        long end = System.currentTimeMillis();
        System.out.println("total:" + (end - start));
    }

    private static void call1() throws Exception {
        Integer x = getValue();
        if (x > 10000) {
            System.out.println(x);
        }
    }

    private static void call2() throws Exception {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return getValue();
            }
        };
        FutureTask<Integer> future = new FutureTask<Integer>(callable);
        new Thread(future).start();
        Integer x = future.get();
        if (x > 10000) {
            System.out.println(x);
        }
    }
}
