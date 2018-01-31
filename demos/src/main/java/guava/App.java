package guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.primitives.Ints;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangguojun01 on 2017/12/12.
 */
public class App {
    static LoadingCache<String, String> LOADING_CACHE = CacheBuilder.newBuilder().refreshAfterWrite(10, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {
        @Override
        public String load(String key) {
            System.out.println(key + "load invoked ...");
            return "58";
        }
    });

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> countUp = Ints.asList(1, 2, 3, 4, 5);
        System.out.println(countUp);
        String x = LOADING_CACHE.get("x");
        System.out.println(x);
        Thread.sleep(10000);
        String y = LOADING_CACHE.get("yyy");
        System.out.println(y);



        Thread.sleep(30000000);
    }
}
