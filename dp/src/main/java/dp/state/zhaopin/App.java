package dp.state.zhaopin;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import dp.state.zhaopin.jr.Applying;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * foo...Created by wgj on 2017/8/5.
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static final int INT = 24;

    public static void main(String[] args) {
        logger.debug("app start");

        Applying applying = new Applying(1234132);
        applying.sendInterview();
        applying.makeImproper();


        logger.debug("app over");


        // print internal state
//        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//        StatusPrinter.print(lc);
    }
}
