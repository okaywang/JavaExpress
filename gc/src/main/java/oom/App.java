package oom;

/**
 * Created by Administrator on 2017/8/29.
 */
public class App {
    //-Xmx40m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=E:\Java\dump
    public static void main(String[] args) {
        long[] arr = new long[0];
        for (int i=1; i<=1000000000; i*=2) {
             arr = new long[i];
            System.out.println("---------------size : " + arr.length);
            Runtime runtime = Runtime.getRuntime();
            System.out.printf("maxMemory : %.2fM\n", runtime.maxMemory()*1.0/1024/1024);
            System.out.printf("totalMemory : %.2fM\n", runtime.totalMemory()*1.0/1024/1024);
            System.out.printf("freeMemory : %.2fM\n", runtime.freeMemory()*1.0/1024/1024);
        }
    }
}
