package com.zhaopin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * foo...Created by wgj on 2017/7/4.
 */
@EnableWebMvc
@Configuration
public class AppConfig {

    @Bean(name = "/captcha")
    public HttpRequestHandler httpRequestHandler () {
        return new CaptchaRequestHandler();
    }

    @Bean(name = "/captcha2")
    public AppConfig httpRequestHandler2 () {

        return new AppConfig();
    }
}