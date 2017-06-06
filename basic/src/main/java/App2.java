
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * foo...Created by wgj on 2017/3/26.
 */
public class App2 {
    protected App2() {

    }

    public static void main(String[] args) {

        Method[] methods = String.class.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method m = methods[i];
            System.out.println("aaaaa");
            System.out.print(m.getModifiers());
            System.out.print("\t");
            System.out.print(m.getReturnType().getName());
            System.out.print("\t");
            System.out.print(m.getName());
            System.out.print("\t");
            System.out.print(m.getParameterTypes().length);
            System.out.println();

        }

//        StringBuilder sb = new StringBuilder();
//        sb.append("aaaaa");
//        System.out.println(String.format(" limit %d offset %d", 10, 2));
//
//        new Exception("vvvvvvvvvvvvvvv").printStackTrace();
//        System.out.println(System.getProperty("user.dir"));
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
