package threadpool;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by guojun.wang on 2017/10/23.
 */
public class AppPool {
    public static final ExecutorService RUNNER_CONSUMER_EXECUTOR = new ThreadPoolExecutor(3,
            5, 1, TimeUnit.MINUTES, new SynchronousQueue<Runnable>(),
            new RunnerConsumerThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println("---------------------------------------------");
            RUNNER_CONSUMER_EXECUTOR.submit(new AppPool.ConsumerRunner(i));
            System.out.println(i);

        }
    }

    static class ConsumerRunner implements Runnable {
        private int state = 0;

        public ConsumerRunner(int state) {
            this.state = state;
        }

        @Override
        public void run() {
            System.out.println(Instant.now().toString() + "\t" + state + "begin");
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Instant.now().toString() + "\t" + state + "end");
        }
    }
}
