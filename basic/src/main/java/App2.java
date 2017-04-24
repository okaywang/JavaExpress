import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * foo...Created by wgj on 2017/3/26.
 */
public class App2 {
    private static final Logger logger = LoggerFactory.getLogger(App2.class);

    protected App2() {

    }

    public static void main(String[] args) {

        print1(1000, 5000, 1000);
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
