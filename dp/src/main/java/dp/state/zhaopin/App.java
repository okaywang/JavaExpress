package dp.state.zhaopin;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import dp.state.zhaopin.jr.Applying;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;
import java.util.concurrent.Executors;

/**
 * foo...Created by wgj on 2017/8/5.
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static final int INT = 24;
    public static int i;

    public static void main(String[] args) throws InterruptedException {
        logger.debug("app start");
        Applying applying = new Applying(1234132);
        applying.sendInterview();
        applying.makeImproper();

        logger.debug("app over");


        MDC.put("sId", "user1");
        logger.info("user1 doing ...");

        MDC.put("sId", "user2");
        logger.info("user2 doing ...");

        System.out.println(i);

        // print internal state
//        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//        StatusPrinter.print(lc);
    }
}
