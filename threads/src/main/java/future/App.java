package future;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by guojun.wang on 2017/10/18.
 */
public class App {
    public static void main(String[] args) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("do thing 1 at" + new Date());
                Thread.sleep(1000);
                return new Random().nextInt(100);
            }
        };
        FutureTask<Integer> future = new FutureTask<Integer>(callable);
        new Thread(future).start();
        try {
            System.out.println("do thing 2 at" + new Date());
            Thread.sleep(3000);// 可能做一些事情
            Integer v = future.get();
            System.out.println(v);
            System.out.println("all thing done at" + new Date());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
