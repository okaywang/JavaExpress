package treeset;

import java.util.Hashtable;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * foo...Created by wgj on 2017/9/3.
 */
public class App {
    public static void main(String[] args) {

        TreeSet<Integer> set = new TreeSet<>();
        set.add(234);
        set.add(91);
        set.add(2342);
        set.add(123);
        System.out.println(set);

        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("工", "土");
        map.put("好", "奸");
        map.put("飞", "讯");
        map.put("肆", "律");
        System.out.println(map);
    }
}
