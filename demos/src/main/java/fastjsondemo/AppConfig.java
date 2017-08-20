package fastjsondemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * foo...Created by wgj on 2017/8/20.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan
public class AppConfig {
    @Bean
    public ZpFoo zpFoo() {
        return new ZpFoo();
    }
}
