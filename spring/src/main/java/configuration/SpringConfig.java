package configuration;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * foo...Created by wgj on 2017/4/4.
 */
@Configuration
public class SpringConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ICaptcha getCaptcha() {
        return new Captcha2();
    }
}
