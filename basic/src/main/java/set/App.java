package set;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/10/10.
 */
public class App implements Serializable {


    public static void main(String[] args) {

        HashSet<String> set1 = new HashSet<>();
        HashSet<String> set2 = new HashSet<>();

        set1.add("java");
        set1.add("c#");
        set1.add("math");
        set1.add("hacker");


        set2.add("math");
        set2.add("economic");
        set2.add("hacker");

        System.out.println(set1);
        System.out.println(set2);

        long start = System.currentTimeMillis();
        System.out.println("--------------------------------------");
        for (int i = 0; i < 10000000; i++) {
            HashSet<String> set3 = new HashSet<String>(set1);
            set3.retainAll(set2);
            System.out.println(set3);
//            hasIntersection(set1, set2);
//            System.out.println(set3);
        }
        System.out.println("--------------------------------------");
        System.out.println(System.currentTimeMillis() - start);

        System.out.println(set1);
        System.out.println(set2);
    }

    private static boolean hasIntersection(Set<String> set1, Set<String> set2) {
        if (set1 == null || set2 == null) {
            return false;
        }
        for (String str : set1) {
            if (set2.contains(str)) {
                return true;
            }
        }
        return false;
    }
}
