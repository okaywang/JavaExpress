import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * foo...Created by wgj on 2017/2/18.
 */
public class App {
    public static void main(String[] args) {
        String str = StringUtils.leftPad("foobar11111111111111111", 10, '*');
        StringUtils.isEmpty("bb");
        System.out.println(StringUtils.repeat('*', 5));

        System.out.println(RandomUtils.nextInt(10,20));
    }
}
