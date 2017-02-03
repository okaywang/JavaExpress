import java.util.Arrays;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * Created by wgj on 2017/1/21.
 */
public class App {
    public static void main(String[] args) {
        System.out.println("...app...");
        int[] items = {3, 7};
        IntConsumer intConsumer = System.out::println;
        Arrays.stream(items).forEach(intConsumer.andThen(System.err::println));

        long m1 = System.currentTimeMillis();
        long count1 = IntStream.range(1, 10000000).filter(MathUtil::isPrime).count();
        System.out.println(count1);
        long m2 = System.currentTimeMillis();
        System.out.println(m2 - m1);
        long count2 = IntStream.range(1, 10000000).parallel().filter(MathUtil::isPrime).count();
        System.out.println(count2);
        long m3 = System.currentTimeMillis();
        System.out.println(m3 - m2);
    }
}
