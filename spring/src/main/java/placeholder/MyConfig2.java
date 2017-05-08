package placeholder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by guojun.wang on 2017/3/31.
 */
@Component
public class MyConfig2 {
    @Value("${jdbc.username}")
    private String username;

    @Value("${urls.getjob}")
    private String url_getjob;

    @Value("${urls.getjob}")
    private static String url_getjob_static;

    public String getUrl_getjob() {
        return url_getjob;
    }

    public void setUrl_getjob(String url_getjob) {
        this.url_getjob = url_getjob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static String getUrl_getjob_static() {
        return url_getjob_static;
    }

    public static void setUrl_getjob_static(String url_getjob_static) {
        MyConfig2.url_getjob_static = url_getjob_static;
    }

    @PostConstruct
    public void run() {
        System.out.println("this is " + username);
    }

    @Override
    public String toString() {
        return "MyConfig2{" +
                "username='" + username + '\'' +
                ", url_getjob='" + url_getjob + '\'' +
                '}';
    }
}
