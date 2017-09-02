package uuid;

import java.util.UUID;

/**
 * foo...Created by wgj on 2017/9/2.
 */
public class App {
    private static int x = 100;

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 10000 * 10000; i++) {
            UUID uuid = UUID.randomUUID();
            String str = uuid.toString().replace("-", "");
            //String str = getString(uuid);
            process(str);
        }
        long end = System.currentTimeMillis();
        System.out.print("takes ");
        System.out.println(end - begin);
    }

    private static String getString(UUID uuid) {
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();

        return Long.toHexString(mostSigBits) + Long.toHexString(leastSigBits);
    }

    /**
     * Returns val represented by the specified number of hex digits.
     */
    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }

    private static void process(String uuid) {
        if (x > 1000) {
            System.out.println(uuid);
        }
    }
}
