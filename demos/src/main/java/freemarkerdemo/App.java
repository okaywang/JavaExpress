/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package freemarkerdemo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

/**
 * Created by guojun.wang on 2017/7/12.
 */
public class App {
    public static void main(String[] args) throws IOException, TemplateException {
        System.out.println("free marker test");
        Configuration e = new Configuration();
        e.setClassForTemplateLoading(App.class, "/template");
        Template template = e.getTemplate("hello.ftl");
        StringWriter out = new StringWriter();
        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("jobName", "Java 工程师");
        template.process(dataMap, out);
        System.out.println(out.toString());
    }
}
