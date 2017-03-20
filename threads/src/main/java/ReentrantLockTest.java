import java.lang.management.ThreadInfo;
import java.util.Set;

/**
 * foo...Created by wgj on 2017/3/11.
 */
public class ReentrantLockTest {
    private Object obj = new Object();
    private Foo foo1 = new Foo();
    private Foo foo2 = new Foo();
    private Bar bar = new Bar();

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockTest app = new ReentrantLockTest();


        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    app.mytest0_1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    app.mytest0_2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.setName("MyT1");
        thread2.setName("MyT2");
        thread1.start();
        thread2.start();

        Thread.sleep(1000);
        app.listAllThreads();
        app.check();

        int i = 0;
        while (true) {
            if (i > 10000000) {
                i = 0;
                System.out.println(i);
            }
            i++;

        }
        //System.out.println(app.sum(Integer.parseInt(args[0])));
    }

    public void listAllThreads() {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread :
                threadSet) {
            System.out.println(thread);
        }
    }

    public void check() {

        System.out.println(getMonitorOwner(foo1));
        System.out.println(getMonitorOwner(foo2));
    }

    public long sum(int n) {

        if (n == 1) {
            return 1;
        }
        return n + sum(n - 1);
    }

    private int count = 0;

    public void mytest0_1() throws InterruptedException {
        synchronized (foo1) {
            System.out.println("1 start");
            System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
            Thread.sleep(1000000);
            System.out.println("1 over");
        }
    }

    public void mytest0_2() throws InterruptedException {
        synchronized (foo1) {
            System.out.println("2 start");
            System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
            Thread.sleep(1000000);
            System.out.println("2 over");
        }
    }

    public void mytest1() {

        System.out.println(obj.hashCode());
        System.out.println(System.identityHashCode(obj));
    }

    public void mytest2() {

        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet) {
            System.out.println(thread);
        }
        synchronized (obj) {
            System.out.println(Thread.holdsLock(obj));
            System.out.println(getMonitorOwner(obj));
        }
    }

    public static long getMonitorOwner(Object obj) {
        if (Thread.holdsLock(obj)) return Thread.currentThread().getId();
        ThreadInfo[] threadInfos = java.lang.management.ManagementFactory.getThreadMXBean().dumpAllThreads(true, false);
        for (java.lang.management.ThreadInfo ti : threadInfos) {
            for (java.lang.management.MonitorInfo mi : ti.getLockedMonitors()) {
                if (mi.getIdentityHashCode() == System.identityHashCode(obj)) {
                    return ti.getThreadId();
                }
            }
        }
        return 0;
    }
}

class Foo {
    @Override
    public int hashCode() {
        return 888999;
    }

}

class Bar {
    @Override
    public int hashCode() {
        return 888999;
    }
}
