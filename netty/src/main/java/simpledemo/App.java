package simpledemo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/18.
 */
public class App {
    public static void main(String[] args) {
        System.out.println(222);
        System.out.println(new Zhaopin().test());
    }

    static class Zhaopin {

        Map<String, String> aa = new HashMap<String, String>();

        int test() {
            return aa.size();
        }
    }
}
