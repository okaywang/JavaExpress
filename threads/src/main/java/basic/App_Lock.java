package basic;

import ch.qos.logback.classic.Level;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


@Slf4j
public class App_Lock {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
//        ((ch.qos.logback.classic.Logger) log).setLevel(Level.INFO);
        ((ch.qos.logback.classic.Logger) log).setLevel(Level.DEBUG);
        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.debug("sleep (Thread.sleep)");
                Thread.sleep(2000);
                log.debug("wake ... ");

                log.debug("lock.lock()");
//                lock.lock();
                lock.tryLock(2000, TimeUnit.SECONDS);
                log.debug("work ... ");
                for (int i = 0; i < 100000000; i++) {
                    String.valueOf(i).hashCode();
                }
                lock.lock();
            }
        });

        log.info(thread.getState().toString());
        thread.start();
        log.info(thread.getState().toString());
        lock.lock();
        int wCount = 0;
        while (true) {
            Thread.sleep(1000);
            log.info(thread.getState().toString());
            if (thread.getState() == Thread.State.WAITING || thread.getState() == Thread.State.TIMED_WAITING) {
                wCount++;
            }
            if (wCount == 5) {
                break;
            }
            if (thread.getState() == Thread.State.TERMINATED) {
                break;
            }
        }
        lock.unlock();
    }
}
