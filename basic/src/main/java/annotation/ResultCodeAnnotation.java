package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by guojun.wang on 2017/4/6.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ResultCodeAnnotation {
    String[] value() default "200";
}
