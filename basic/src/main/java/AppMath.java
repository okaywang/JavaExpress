/**
 * Created by wangguojun01 on 2018/3/8.
 */
public class AppMath {
    public static void main(String[] args) {
        double begin = 10;
        double rate = 0.005;
        int days = 20 * 12 * 4;
        double result = repeat(begin, rate, days);

        String tip = String.format("from %.2f to %.2f (growth:%.2f), with rate %.2f%%, using %d days", begin, result, result - begin, rate * 100, days);
        System.out.println(tip);
    }

    private static double repeat(double base, double rate, int days) {
        return base * Math.pow(1 + rate, days);
    }
}
