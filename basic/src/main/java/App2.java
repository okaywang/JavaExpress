import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * foo...Created by wgj on 2017/3/26.
 */
public class App2 {
    private static final Logger logger = LoggerFactory.getLogger(App2.class);

    protected App2(){

    }
    public static void main(String[] args) {

        logger.info("this is a info message");

        System.out.println(java.lang.Runtime.getRuntime().availableProcessors());
        InputStream is;
        InputStreamReader isr;
    }
}
