package reflection;


import java.lang.reflect.Field;

/**
 * foo...Created by wgj on 2017/4/23.
 */
public class App {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Person p = new Person();
        p.setPname("wgj");
        System.out.println(p.getPname());
        Field field = p.getClass().getDeclaredField("page");
        field.setAccessible(true);
        System.out.println(field.getInt(p));

    }
}
