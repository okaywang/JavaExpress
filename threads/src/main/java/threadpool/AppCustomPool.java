package threadpool;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/9/12.
 */
public class AppCustomPool {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 20,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());

        for (int i = 0; i < 1000; i++) {
             executor.execute(new RefreshJobTask());
        }
    }

    private static void doWork() {

    }
}
