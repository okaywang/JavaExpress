package threadpool;

/**
 * Created by Administrator on 2017/9/12.
 */
public class RefreshJobTask implements Runnable {
    private static int count = 0;

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " begin ...");
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " end ...");

        System.out.println("-------------------------" + (++count) + "-------------------------");
    }
}
