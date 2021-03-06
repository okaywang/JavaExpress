import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * foo...Created by wgj on 2017/3/26.
 */
public class App2 {
    protected App2() {

    }

    public static void main(String[] args) {

        System.out.println(Paths.get(Paths.get(System.getProperty("user.dir")).getRoot().toString(), "/opt/wf"));


        Class<Integer> integerClass = int.class;
        Class<Integer> integerClass1 = Integer.class;
        List<Foo> foos = new ArrayList<>();
        foos.add(new Foo(2, "2222", new Date(2002, 1, 1)));
        foos.add(new Foo(1, "1111", new Date(2001, 1, 1)));
        foos.add(new Foo(3, "3333", new Date(2003, 1, 1)));
        //foos.add(new Foo(4, "3333", null));

        //foos.stream().filter(i -> i.getBirthday() != null).sorted(((i, j) -> j.getBirthday().compareTo(i.getBirthday()));

        Comparator<Foo> comparator = Comparator.comparing(Foo::getBirthday);
        Collections.sort(foos, comparator.reversed());


        foos.forEach(System.out::println);


        Method[] methods = String.class.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method m = methods[i];
            System.out.println("aaaaa");
            System.out.print(m.getModifiers());
            System.out.print("\t");
            System.out.print(m.getReturnType().getName());
            System.out.print("\t");
            System.out.print(m.getName());
            System.out.print("\t");
            System.out.print(m.getParameterTypes().length);
            System.out.println();

        }

//        StringBuilder sb = new StringBuilder();
//        sb.append("aaaaa");
//        System.out.println(String.format(" limit %d offset %d", 10, 2));
//
//        new Exception("vvvvvvvvvvvvvvv").printStackTrace();
//        System.out.println(System.getProperty("user.dir"));
    }

    static void print1(int n, int max, int raw) {
        System.out.println(n);
        if (n < max) {
            print1(2 * n, max, raw);
        } else {
            print2(n, raw);
        }
    }

    static void print2(int n, int raw) {
        System.out.println(n);
        if (n > raw) {
            print2(n / 2, raw);
        }
    }
}
