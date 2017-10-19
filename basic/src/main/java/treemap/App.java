package treemap;

import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Administrator on 2017/9/30.
 */
public class App {

    public static void main(String[] args) {

        TreeMap<String, Integer> hits = new TreeMap<>();

        hits.put("search=java", 100);
        hits.put("search=php", 30);
        hits.put("search=go", 200);
        hits.put("search=c#", 1);
        hits.headMap("22");
        System.out.println(hits);
    }
}
