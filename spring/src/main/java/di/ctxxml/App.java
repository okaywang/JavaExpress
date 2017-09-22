package di.ctxxml;

import di.ctxxml.hello.MessagePrinter;
import di.ctxxml.hello.MessageService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * foo...Created by wgj on 2017/2/12.
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/ctx.xml");
        //App app = context.getBean(App.class);
        //System.out.println(app);
        MessagePrinter printer = context.getBean(MessagePrinter.class);
        printer.printMessage();
        System.out.println("------------------------------------------");
        MessageService ms = context.getBean("messagePrinterWithLog", MessageService.class);
        System.out.println(ms.getMessage());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++");
    }
}
