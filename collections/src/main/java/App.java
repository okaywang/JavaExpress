import java.util.*;

/**
 * Created by wgj on 2017/1/21.
 */
public class App {
    public static void main(String[] args) {
        Integer i = 200;
        Integer j = 200;
        System.out.println(i == j);

        int[] ids = {3, 4, 6};
        Arrays.stream(ids).forEach(System.out::println);
        Arrays.stream(ids.getClass().getInterfaces()).forEach(System.out::println);
        Map<String,String> maps = new HashMap<>();
        Map<String,String> maps2 = new Hashtable<>();
        System.out.println("test collection");

        

        Thread t = null;
        t.run();
        t.start();
    }

    interface IMy{
        public static final int x = 3;
        void test();
    }
}
