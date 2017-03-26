package wait_notify_blocking_queue;

/**
 * foo...Created by wgj on 2017/3/26.
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> bq = new BlockingQueue<>(3);
        Runnable t1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    long tid = Thread.currentThread().getId();
                    System.out.println(tid + " : try to produce " + i);
                    try {
                        sleep(1000);
                        bq.put(String.valueOf(i));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(tid + " : produced " + i);

                }
            }
        };
        Runnable t2 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    long tid = Thread.currentThread().getId();
                    System.out.println(tid + " : try to comsume " + i);
                    try {
                        sleep(50);
                        String item = bq.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(tid + " : comsumed " + i);

                }
            }
        };

        new Thread(t1).start();
        new Thread(t2).start();

        System.out.println("main ...............");
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
