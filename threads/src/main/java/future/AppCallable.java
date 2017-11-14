package future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by guojun.wang on 2017/11/13.
 */
public class AppCallable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(10000);
                return 333333333;
            }
        };

        List<Future<Integer>> futureTasks = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            FutureTask<Integer> future = new FutureTask<Integer>(callable);
            new Thread(future).start();
            futureTasks.add(future);
        }

        for (Future<Integer> f : futureTasks) {
            System.out.println(f.get());
        }
        //Thread.sleep(1000);
//        Integer x = future.get();
//        System.out.println(x);

//        System.out.println(x);
    }
}
