package generic;

/**
 * Created by guojun.wang on 2017/4/7.
 */
public class App {
    public static void main(String[] args) {
        Foo bar = getObject(Foo.class);
        System.out.println(bar);

    }

    private static <T> T getObject(Class<T> classType) {
        Foo foo = new Foo();
        Bar bar = new Bar();
        return (T) foo;
    }

}
