import sun.misc.GC;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import java.util.stream.Stream;

/**
 * Created by wgj on 2017/1/21.
 */
public class App {
    public static ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<>();
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static class ParseDate implements Runnable {
        int i;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                if (tl.get() == null) {
                    tl.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                }
                Date t = tl.get().parse("2015-03-01 19:22:" + i % 60);
                //System.out.println(Thread.currentThread().getId() + "____" + i + ": " + t);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    static ThreadLocal<SyhProger> tl2 = new ThreadLocal<>();

    public static void main(String[] args) throws ClassNotFoundException {
//        ExecutorService es = Executors.newFixedThreadPool(30);
//        for (int i = 0; i < 10000; i++) {
//            es.execute(new ParseDate(i));
//        }
        tl2.set(new SyhProger());
        System.out.println("1   " + tl2.get());
        System.gc();

        System.out.println("2    " + tl2.get());
        tl2 = null;

        System.gc();
        System.out.println("over");
    }


}
