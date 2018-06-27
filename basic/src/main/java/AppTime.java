import com.sun.org.apache.bcel.internal.util.ByteSequence;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wangguojun01 on 2018/6/6.
 */
public class AppTime {
    public static void main(String[] args) throws InterruptedException {
        Date d = new Date();
        System.out.println(d.getHours());
        System.out.println(d.getMinutes());
        System.out.println(24 * 60);
        System.out.println(Integer.MAX_VALUE);


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String f = format.format(new Date());
        System.out.println(f);

        System.out.println(d);
        System.out.println(new Date(d.getYear(), d.getMonth(), d.getDate(), d.getHours(), d.getMinutes()));

        System.out.println(new Date().getTime());
        Thread.sleep(1000);
        System.out.println(new Date().getTime());


        String str = "abc";

    }
}
