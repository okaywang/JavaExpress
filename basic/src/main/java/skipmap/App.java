package skipmap;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by wangguojun01 on 2018/2/6.
 */
public class App {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, Long> sortedLocalCache = new ConcurrentSkipListMap<String, Long>();
        sortedLocalCache.put("20",System.currentTimeMillis());
        sortedLocalCache.put("13",System.currentTimeMillis());
        sortedLocalCache.put("96",System.currentTimeMillis());
        sortedLocalCache.put("63",System.currentTimeMillis());
        ConcurrentNavigableMap<String, Long> tailedMap = sortedLocalCache.tailMap("50");
        System.out.println(tailedMap);
        System.out.println(sortedLocalCache.size());

    }
}
