package threadpool;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/9/12.
 */
public class AppCustomPool {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                new RunnerConsumerThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 10; i++) {
            try {
                executor.execute(new RefreshJobTask(i));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void doWork() {

    }
}
