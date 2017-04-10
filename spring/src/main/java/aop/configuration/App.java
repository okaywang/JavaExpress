package aop.configuration;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
 import aop.configuration.captcha.ICaptcha;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * foo...Created by wgj on 2017/4/4.
 */
public class App {
    public static void main(String[] args) {
        //ListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //beanFactory.getBean(App.class);

        ApplicationContext context = new AnnotationConfigApplicationContext("configuration");

        //ICaptcha captcha1 = context.getParentBeanFactory().getBean(ICaptcha.class);
        ICaptcha captcha2 = (ICaptcha) context.getBean("getCaptcha");

        ICaptcha captcha3 = (ICaptcha) context.getBean("getCaptcha");

        System.out.println(captcha2 == captcha3);
        captcha2.generate();
    }
}
