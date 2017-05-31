package generic;

/**
 * Created by guojun.wang on 2017/4/7.
 */
public class App {
    public static void main(String[] args) {
        Class<Foo> c = Foo.class;
        Foo bar = getObject(c);
        System.out.println(bar.getClass().getClassLoader());
        System.out.println(c.hashCode());
        System.out.println(bar);
    }

    private static <T> T getObject(Class<T> classType) {
        Foo foo = new Foo();
        Bar bar = new Bar();
        return (T) foo;
    }
}
