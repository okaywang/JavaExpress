package annotation.api;

/**
 * Created by guojun.wang on 2017/5/27.
 */
public @interface HttpMapping {
    HttpMethod method() default HttpMethod.get;

    Class handler();

    enum HttpMethod {
        get, post;
    }
}
