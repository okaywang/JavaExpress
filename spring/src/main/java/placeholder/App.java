package placeholder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

/**
 * Created by guojun.wang on 2017/3/31.
 */
public class App {
    public static void main(String[] args) {


        ApplicationContext context = new ClassPathXmlApplicationContext("placeholder/ctx-placeholder.xml");
        JdbcConfig obj = context.getBean(JdbcConfig.class);
        System.out.println(obj);

        MyConfig2 obj2 = context.getBean(MyConfig2.class);
        System.out.println(obj2);

//        Object o = context.getBean("propertyConfigurer");
//        System.out.println(o);
//        Properties properties = (Properties)context.getBean("configProperties");
//        System.out.println(properties);

    }
}
