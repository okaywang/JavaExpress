package com.zhaopin;

import com.zhaopin.ihr.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by guojun.wang on 2017/11/14.
 */
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("AppContext.xml");
        IhrBusiness business = context.getBean(IhrBusiness.class);
        business.addBlog();

//        Blog blog = blogMapper.getBlog(345, 1);
//        System.out.println(blog);


    }
}
