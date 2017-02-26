import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * foo...Created by wgj on 2017/2/26.
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        ArrayList<Future<Integer>> results = new ArrayList<Future<Integer>>();
        for (int i = 0; i < 5; i++) {
            results.add(executor.submit(new GameTask()));
        }
        System.out.println("total experience is ...");
        executor.shutdown();
        System.out.println("waiting ... ");
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        System.out.println("finsh");
        System.out.println(results.stream().mapToInt(x -> {
            try {
                return x.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return 0;
        }).sum());
    }
}
