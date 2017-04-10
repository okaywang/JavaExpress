
package aop.configuration.captcha;

import org.springframework.stereotype.Component;

/**
 * foo...Created by wgj on 2017/4/4.
 */
@Component
public class Captcha2 implements ICaptcha {

    @Override
    public byte[] generate() {
        System.out.println(this.getClass().getName());
        return new byte[0];
    }

    @Override
    public void test(int x) {

    }
}
