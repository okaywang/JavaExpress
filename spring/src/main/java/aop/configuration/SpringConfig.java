package aop.configuration;

import aop.configuration.captcha.Captcha2;
import aop.configuration.captcha.ICaptcha;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * foo...Created by wgj on 2017/4/4.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan
public class SpringConfig {

//    @Bean
//    public ICaptcha getCaptcha() {
//        return new Captcha2();
//    }
}
