package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangguojun01 on 2018/8/11.
 */
public class AppLogger {
    public static void main(String[] args) {
        Logger logger1 = LoggerFactory.getLogger("demo");
        logger1.info("xxxxxxxxxxxxxxxxxx");


        Logger logger2 = LoggerFactory.getLogger("demo.AppLogger");
        logger2.info("xxxxxxxxxxxxxxxxxx");
    }
}
