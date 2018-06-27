import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by wangguojun01 on 2018/5/11.
 */
public class AppTest {
    public static void main(String[] args) {
        String[] ids = {"3", "4"};
        System.out.println("aabb".replace("aa", "v"));


        Map<String, String> map1 = new HashMap<>();
        map1.computeIfAbsent("wj", x -> {
            return x + "wangjun";
        });
        map1.computeIfAbsent("xh", x -> {
            return x + "xh";
        });
        map1.computeIfAbsent("wj", x -> {
            return x + "wgj";
        });
        System.out.println(map1);
    }
}
