import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgj on 2017/1/21.
 */
public class App {
    public static void main(String[] args) {
        ThreadLocal<MyObject> tl1 = new ThreadLocal<MyObject>();
        ThreadLocal<String> tl2 = new ThreadLocal<String>();
        tl1.set(new MyObject());
        tl2.set(new String("vvvvvvvvvvvvvvvvv"));
        System.out.println(tl1.get());
        System.out.println(tl2.get());
        tl1 = null;
        System.gc();
        System.out.println("over");
    }
}
