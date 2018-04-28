package threadgroup;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangguojun01 on 2018/3/5.
 */
public class App {
    public static void main(String[] args) {





        System.out.println(System.getenv());

        System.out.println(UUID.randomUUID().toString());

        //创建5个线程，并入group里面进行管理
        ThreadGroup threadGroup = new ThreadGroup("Searcher");
        Result result = new Result();
        SearchTask searchTask = new SearchTask(result);
        for (int i = 0; i < 5; i++) {
            Thread thred = new Thread(threadGroup, searchTask);
            thred.start();
            try {
                Thread.sleep(333);
                TimeUnit.SECONDS.sleep(1);
                TimeUnit.MINUTES.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //通过这种方法可以看group里面的所有信息
        System.out.printf("Number of Threads: %d\n", threadGroup.activeCount());
        System.out.printf("Information about the Thread Group\n");
        threadGroup.list();

        //这样可以复制group里面的thread信息
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        for (int i = 0; i < threadGroup.activeCount(); i++) {
            System.out.printf("Thread %s: %s\n", threads[i].getName(),
                    threads[i].getState());
        }

        waitFinish(threadGroup);
        //将group里面的所有线程都给interpet
        threadGroup.interrupt();
    }



    private static void waitFinish(ThreadGroup threadGroup) {
        while (threadGroup.activeCount() > 9) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
