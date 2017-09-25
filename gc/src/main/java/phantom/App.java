package phantom;

import data.ComputerInfo;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        ReferenceQueue<ComputerInfo> queue = new ReferenceQueue<ComputerInfo>();
        PhantomReference<ComputerInfo> sf = new PhantomReference<ComputerInfo>(new ComputerInfo(), queue);
        List<byte[]> items = new ArrayList<byte[]>();
        for (; ; ) {
            items.add(new byte[200 * 1024 * 1024]);
            System.out.println(sf.get());
            Thread.sleep(1000);
            System.gc();
        }
    }
}
