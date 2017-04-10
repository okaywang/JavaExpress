package aop.configuration.captcha;

/**
 * foo...Created by wgj on 2017/4/4.
 */
public class Captcha1 implements ICaptcha {
    @Override
    public byte[] generate() {
        System.out.println(this.getClass().getName());
        return new byte[0];
    }

    @Override
    public void test(int x) {

    }
}
