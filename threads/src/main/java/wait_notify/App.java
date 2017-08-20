package wait_notify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * foo...Created by wgj on 2017/8/15.
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

        public static void main(String[] args) throws InterruptedException {
        Object obj = new Object();
        logger.info("...................");
        System.out.println(1111);
        obj.wait();
        System.out.println(2222);
    }
}
