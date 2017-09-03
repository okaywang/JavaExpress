package hm;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/8/31.
 */
public class App {
    static final int MAXIMUM_CAPACITY = 1 << 30;

    public static void main(String[] args) {

        App[] apps = new App[10];
        apps[3] = new App();
        apps[4] = new App();

        ConcurrentHashMap<String,String>  a = new ConcurrentHashMap<>();
        a.put("","");
        Hashtable h;
        int max = 1 << 30;
        System.out.println(max);
        for (int i = 3; i < 10; i++) {
            System.out.print(i);
            System.out.print("-->");
            System.out.println(tableSizeFor(i));
        }
        HashMap<Person, String> hm = new HashMap<>(64);

        for (int i = 0; i < 100; i++) {
            Person key = new Person("name" + i, i / 20);
            hm.put(key, UUID.randomUUID().toString());
        }

    }

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
