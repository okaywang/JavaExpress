package di.ctxannotation;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * foo...Created by wgj on 2017/2/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class AppTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void shouldGetValue() {
        System.out.println(userDao.maxUserId());
        Assert.assertTrue(userDao.maxUserId() > 0);
    }
}
