package annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by guojun.wang on 2017/4/6.
 */
public class App {
    public static void main(String[] args) throws NoSuchMethodException {
        System.out.println("annotation test");

        Class c = JobController.class;
        Method method1 = c.getMethod("add", new Class[]{Object.class});
        iteratorAnnotations(method1);


        Method method2 = c.getMethod("get", new Class[]{Object.class});
        iteratorAnnotations(method2);

    }

    public static void iteratorAnnotations(Method method) {
        // 判断 somebody() 方法是否包含MyAnnotation注解
        if (method.isAnnotationPresent(ResultCodeAnnotation.class)) {
            // 获取该方法的MyAnnotation注解实例
            ResultCodeAnnotation myAnnotation = method.getAnnotation(ResultCodeAnnotation.class);
            // 获取 myAnnotation的值，并打印出来
            String[] values = myAnnotation.value();
            for (String str : values)
                System.out.printf(str + ", ");
            System.out.println();
        }

        // 获取方法上的所有注解，并打印出来
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
    }
}
