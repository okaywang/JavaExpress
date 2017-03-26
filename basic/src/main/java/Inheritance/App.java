package Inheritance;

/**
 * foo...Created by wgj on 2017/3/26.
 */
public class App {
    public static void main(String[] args) {
        Instuments in = new Wind();
        System.out.println(in.getClass());
        in.play();
    }
}
