import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * foo...Created by wgj on 2017/2/7.
 */
public class ArrayListIsNotThreadSafe {

    public static void main(String[] args) throws Exception {
//        final List list = new ArrayList();
       final List list = new Vector();
        for (int i=0; i<100000; i++){
            list.add(i);
        }
        for (int i=0; i<10; i++)
            new Thread(){
                @Override
                public void run(){
                    for (int i=0; i<1000; i++)
                        list.remove(0);
                }
            }.start();
        for (int i=0; i<10; i++)
            new Thread(){
                @Override
                public void run(){
                    for (int i=0; i<1000; i++)
                        list.add(10000*i + i);
                }
            }.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {}
        System.out.println(list.size());
    }
}
