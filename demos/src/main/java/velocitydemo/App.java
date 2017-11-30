package velocitydemo;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangguojun01 on 2017/11/30.
 */
public class App {
    public static void main(String[] args) {
        System.out.println(System.out);
        Map<String,String> items = new HashMap<>();
        items.put("code","85631");
        items.put("name","frank");
        items.put("jobName","java engineer");
        Context context = new VelocityContext(items);
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template template = ve.getTemplate("hellovelocity.vm");
        StringWriter sw = new StringWriter();
        template.merge(context,sw);
        System.out.println(sw.toString());
    }
}
