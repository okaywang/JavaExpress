package com.zhaopin;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by guojun.wang on 2017/11/14.
 */
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("AppContext.xml");
        BlogMapper mapper = context.getBean(BlogMapper.class);
        DBContext.setDBKey("rd2");
        Blog blog = mapper.getBlog(345, 1);
        System.out.println(blog);
    }
}
