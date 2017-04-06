package annotation;

/**
 * Created by guojun.wang on 2017/4/6.
 */
public class JobController {
    @ResultCodeAnnotation(value = {"200", "404", "500", "407"})
    public Object add(Object job) {
        return null;
    }

    @ResultCodeAnnotation(value = {"200", "404"})
    public Object get(Object job) {
        return null;
    }
}
