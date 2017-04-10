package aop.configuration.captcha;

import org.springframework.stereotype.Component;

/**
 * foo...Created by wgj on 2017/4/4.
 */
@Component
public interface ICaptcha {
    byte[] generate();

    void test(int x);
}
