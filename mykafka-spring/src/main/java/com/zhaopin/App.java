package com.zhaopin;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.logging.Logger;

/**
 * Created by guojun.wang on 2017/3/27.
 */
public class App {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("ctx.xml");
        Object obj = context.getBean("person");
        System.out.println(obj);
        //MessagePrinter printer = context.getBean(MessagePrinter.class);
        //printer.printMessage();
        System.out.println(1111111);
    }
}
