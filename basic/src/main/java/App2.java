
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * foo...Created by wgj on 2017/3/26.
 */
public class App2 {
    protected App2() {

    }

    public static void main(String[] args) {
        new Exception("vvvvvvvvvvvvvvv").printStackTrace();
        System.out.println(System.getProperty("user.dir"));
    }

    static void print1(int n, int max, int raw) {
        System.out.println(n);
        if (n < max) {
            print1(2 * n, max, raw);
        } else {
            print2(n, raw);
        }
    }

    static void print2(int n, int raw) {
        System.out.println(n);
        if (n > raw) {
            print2(n / 2, raw);
        }
    }
}
