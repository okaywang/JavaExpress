import java.util.Vector;

/**
 * Created by wgj on 2017/1/21.
 */
public class App {
    public static void main(String[] args) {
        System.out.printf("33");
        Vector<String> vector = new Vector<String>();
        vector.add("1111");
        vector.add("aaa");
        vector.forEach(System.out::println);
        vector.forEach(App::P);
    }

    public static void P(String str)
    {
        System.err.println(str);
    }
}
