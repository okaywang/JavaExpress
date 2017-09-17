package birthday_paradox_demo;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2017/9/17.
 * in a set of {\displaystyle n} n randomly chosen people, some pair of them will have the same birthday.
 * By the pigeonhole principle, the probability reaches 100% when the number of people reaches 367
 * (since there are only 366 possible birthdays, including February 29).
 * However, 99.9% probability is reached with just 70 people, and 50% probability with 23 people.
 */
public class App {
    private final static Random random = new Random();
    private static int hitCountByZhangHeng = 0;
    private static AtomicInteger hitCount = new AtomicInteger(0);

    // string    + vs String.valueOf
    // Arrays.sort
    public static void main(String[] args) throws InterruptedException {
        int threadCount = 50;
        CountDownLatch latch = new CountDownLatch(threadCount);

        ExecutorService es = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        test();
                    }
                    latch.countDown();
                }
            });
        }
        latch.await();
        System.out.println("-------------------------------------------");
        System.out.println(hitCountByZhangHeng);
        System.out.println(hitCount);
        es.shutdown();

    }

    private static void test() {
        String[] items = getBirthday(23);
        String[] duplicates = findDuplicates(items);
//        Arrays.stream(items).forEach(System.out::println);
//        System.out.println("------------");
//        Arrays.stream(duplicates).forEach(System.out::println);
        if (duplicates.length > 0) {
            hitCountByZhangHeng++;
            hitCount.incrementAndGet();
        }
        System.out.println(duplicates.length > 0);
    }

    private static String[] findDuplicates(String[] items) {
        Set<String> set = new HashSet<>();
        List<String> duplicates = new ArrayList<>();
        for (String item : items) {
            if (set.contains(item)) {
                duplicates.add(item);
                continue;
            }
            set.add(item);
        }
        return duplicates.toArray(new String[duplicates.size()]);
    }

    private static String[] getBirthday(int count) {
        String[] items = new String[count];
        for (int i = 0; i < count; i++) {
            items[i] = String.format("%02d", 1 + random.nextInt(12)) + String.format("%02d", 1 + random.nextInt(30));
        }
        return items;
    }
}
