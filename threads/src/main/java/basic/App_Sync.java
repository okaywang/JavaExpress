package basic;

import ch.qos.logback.classic.Level;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class App_Sync {
    public static void main(String[] args) throws InterruptedException {
//        ((ch.qos.logback.classic.Logger) log).setLevel(Level.INFO);
        ((ch.qos.logback.classic.Logger) log).setLevel(Level.DEBUG);
        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.debug("sleep (Thread.sleep)");
                Thread.sleep(2000);
                log.debug("wake ... ");

                log.debug("synchronized (App.class)");
                synchronized (App_Sync.class) {
                    log.debug("work ... ");
                    for (int i = 0; i < 100000000; i++) {
                        String.valueOf(i).hashCode();
                    }
                }
            }
        });

        log.info(thread.getState().toString());
        thread.start();
        log.info(thread.getState().toString());
        synchronized (App_Sync.class) {
            int bCount = 0;
            while (true) {
                Thread.sleep(1000);
                log.info(thread.getState().toString());
                if (thread.getState() == Thread.State.BLOCKED) {
                    bCount++;
                }
                if (bCount == 5) {
                    break;
                }
                if (thread.getState() == Thread.State.TERMINATED) {
                    break;
                }
            }
        }
    }
}
