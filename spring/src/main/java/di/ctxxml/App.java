package di.ctxxml;

import di.ctxxml.hello.MessagePrinter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * foo...Created by wgj on 2017/2/12.
 */
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/ctx.xml");
        //App app = context.getBean(App.class);
        //System.out.println(app);
        MessagePrinter printer = context.getBean(MessagePrinter.class);
        printer.printMessage();
    }
}
