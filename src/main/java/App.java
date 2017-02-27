import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wgj on 2017/1/21.
 */
public class App {

    public static void main(String... args) throws IOException {
        List<String> s = Arrays.asList("aaa","bbbb");

        ArrayList<String> bb;

        System.out.println(s);
        System.out.println(Thread.activeCount());
    }
}


