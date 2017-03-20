
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * foo...Created by wgj on 2017/3/11.
 */
public class App {
    int i1 = 100;
    String s1 = null;
    String s2 = "ab";
    int i2 = 200;

    public static void main(String[] args) throws Exception {
        App test = new App();
        Thread.sleep(10000);
        System.out.println(getInt(test, "i1"));
        System.out.println(getInt(test, "s1"));
        System.out.println(getInt(test, "s2"));
        System.out.println(getInt(test, "i2"));
    }

    static int getInt(App test, String name) throws Exception {
        Unsafe u = getUnsafe();
        long offset = u.objectFieldOffset(App.class.getDeclaredField(name));
        return u.getInt(test, offset);
    }

    static Unsafe getUnsafe() throws Exception {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (Unsafe) f.get(null);
    }
}
