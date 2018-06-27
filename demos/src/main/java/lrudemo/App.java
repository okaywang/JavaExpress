package lrudemo;

/**
 * Created by wangguojun01 on 2018/6/22.
 */
public class App {
    public static void main(String[] args) {
        LRUCache<Long, Boolean> lruCache = new LRUCache(5);


        long x = 313;
        long y = 313;
        lruCache.put(33L, true);
        System.out.println(lruCache.containsKey(y));
        lruCache.put(x, true);
        System.out.println(lruCache.containsKey(y));
        lruCache.put(38L, true);
        lruCache.put(37L, true);

        lruCache.put(42L, true);
        System.out.println(lruCache.containsKey(y));
        lruCache.put(43L, true);
        lruCache.put(44L, true);
        System.out.println(lruCache.containsKey(y));
        //lruCache.containsKey()
        System.out.println(lruCache);
    }
}
