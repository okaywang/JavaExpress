package baasweb;

/**
 * Created by wangguojun01 on 2018/8/9.
 */
public abstract class HttpApiBase {
    abstract String getUrl();

    abstract Class<?> getResponseType();
}
