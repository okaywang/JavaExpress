import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * foo...Created by wgj on 2017/2/26.
 */
public class GameTask implements Callable<Integer> {
    private final static Random random = new Random(1);

    public Integer call() throws Exception {
        int seconds = random.nextInt(10);
        TimeUnit.SECONDS.sleep(seconds);
        int experience = random.nextInt(1000);
        System.out.println(String.format("thread:%d takes %d seconds, get experience %d", Thread.currentThread().getId(), seconds, experience));
        return experience;
    }
}
