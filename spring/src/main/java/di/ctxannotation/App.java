package di.ctxannotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

/**
 * foo...Created by wgj on 2017/2/26.
 */

public class App {
    @Autowired
    private UserDao userDao;

    public void test() {
        System.out.println(userDao.maxUserId());
    }

    public static void main(String[] args) {
       new App().test();
    }
}
