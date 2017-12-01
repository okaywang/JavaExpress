package com.bj58;

import java.lang.annotation.Annotation;

/**
 * Created by wangguojun01 on 2017/11/30.
 */
public class App {
    public static void main(String[] args) {
        Annotation[] annotations = HelloController.class.getAnnotations();
        System.out.println(annotations.length);
    }
}
