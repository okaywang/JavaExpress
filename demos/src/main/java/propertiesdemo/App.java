package propertiesdemo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wangguojun01 on 2018/7/31.
 */
public class App {
    public static void main(String[] args) throws IOException {
        Properties ps = new Properties();
        //InputStream resourceAsStream = App.class.getClassLoader().getResourceAsStream("devpeer.properties");
        //InputStream resourceAsStream = App.class.getClassLoader().getResourceAsStream("devpeer.properties");
        ps.load(new FileInputStream("D:\\workspace\\JavaExpress\\demos\\src\\main\\resources\\devpeer.properties"));

        System.out.println(ps);

        for (String key : ps.stringPropertyNames()) {
            System.out.println(key + "=" + ps.getProperty(key));
        }

    }
}
