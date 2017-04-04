package configuration;

import javafx.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * foo...Created by wgj on 2017/4/4.
 */
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("configuration");
        ICaptcha captcha = context.getBean(ICaptcha.class);
        captcha.generate();
    }
}
