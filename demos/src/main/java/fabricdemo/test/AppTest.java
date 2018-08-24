package fabricdemo.test;

/**
 * Created by wangguojun01 on 2018/8/13.
 */
public class AppTest {
    public static void main(String[] args) {
        String channelName = "a123456789012";
        System.out.println(channelName.matches("^[a-zA-Z][0-9a-zA-Z]{1,11}"));
    }
}
