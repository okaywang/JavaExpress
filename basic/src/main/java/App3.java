import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by guojun.wang on 2017/11/9.
 */
public class App3 {
    public static void main(String[] args)  {
        B b = new B();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("D:\\Projects\\JavaExpress\\basic\\build\\classes\\main\\A.class");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(fis);


        String str = "abc";
        str.contains("b");
    }
}

class A {
    public A() {
        System.out.println("A");
    }
}

class B extends A {

    public B() {
        System.out.println("B");
    }
}

