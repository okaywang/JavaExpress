import java.util.*;

/**
 * Created by wgj on 2017/1/21.
 */
public class App {
    public static void main(String[] args) {
        int[] ids = {3, 4, 6};
        Arrays.stream(ids).forEach(System.out::println);
        Arrays.stream(ids.getClass().getInterfaces()).forEach(System.out::println);
        Map<String,String> maps = new HashMap<>();
        Map<String,String> maps2 = new Hashtable<>();
        System.out.println("test collection");
    }
}
