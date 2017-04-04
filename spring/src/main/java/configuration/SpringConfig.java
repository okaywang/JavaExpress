package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * foo...Created by wgj on 2017/4/4.
 */
@Configuration
public class SpringConfig {

    @Bean
    public ICaptcha getCaptcha() {
        return new Captcha2();
    }
}
