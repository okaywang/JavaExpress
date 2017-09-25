package weak;

import data.ComputerInfo;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        WeakReference<ComputerInfo> sf = new WeakReference<ComputerInfo>(new ComputerInfo());
        List<byte[]> items = new ArrayList<byte[]>();
        for (; ; ) {
            items.add(new byte[200 * 1024 * 1024]);
            System.out.println(sf.get());
            Thread.sleep(1000);
            System.gc();
        }
    }
}
