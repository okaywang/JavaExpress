import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgj on 2017/1/21.
 */
public class App {
    public static void main(String[] args) {
        ReferenceQueue<MyObject> queue = new ReferenceQueue<MyObject>();
        SoftReference<MyObject> softReference = new SoftReference<MyObject>(new MyObject(), queue);
        System.out.println(softReference.get());
        System.gc();
        System.out.println(softReference.get());

       List<byte[]> items = new ArrayList<byte[]>();
        while (true)
        {
            System.out.println(softReference.get());
            byte[] b = new byte[99999999];
           items.add(b);
        }


    }
}
