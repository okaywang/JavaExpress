import com.zhaopin.StrCrypto;

/**
 * Created by guojun.wang on 2017/2/8.
 */
public class App {
    public static void main(String[] args) throws Exception {
        StrCrypto crypto = new StrCrypto("abcdef", "123456");
        System.out.println(crypto.encrypt(12007036,"JR394077410R90250003000"));
    }
}
