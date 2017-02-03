/**
 * Created by wgj on 2017/1/21.
 */
public class MathUtil {
    public static boolean isPrime(int number) {
        int tmp = number;
        if (tmp < 2) {
            return false;
        }
        for (int i = 2; Math.sqrt(tmp) >= i; i++) {
            if (tmp % i == 0) {
                return false;
            }
        }
        return true;
    }
}
