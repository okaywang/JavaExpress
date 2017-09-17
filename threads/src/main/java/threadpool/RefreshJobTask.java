package threadpool;

/**
 * Created by Administrator on 2017/9/12.
 */
public class RefreshJobTask implements Runnable {
    private static int count = 0;
    private String taskName;

    public RefreshJobTask(int i) {
        this.taskName = "task " + i;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + taskName + " begin ...");
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + taskName + " end ...");

        System.out.println("-------------------------" + (++count) + "-------------------------");
    }
}
