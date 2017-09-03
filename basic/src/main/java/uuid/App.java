package uuid;

import java.util.UUID;

/**
 * foo...Created by wgj on 2017/9/2.
 */
public class App {
    private static int x = 100;

    public static void main(String[] args) {
        RdHelper.showInfo();
        int type = Integer.parseInt(args[0]);
        int times = Integer.parseInt(args[1]);
        System.out.println("current type:" + type + ", times: " + times);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < times * 10000; i++) {
            UUID uuid = UUID.randomUUID();
            String str = null;
            if (type == 1) {
                str = uuid.toString().replace("-", "");
            } else if (type == 2) {
                str = getString(uuid);
            } else if (type == 3) {
                str = getString3(uuid);
            }
            process(str);
        }
        long end = System.currentTimeMillis();
        System.out.print("takes ");
        System.out.println(end - begin);
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

    private static String getString(UUID uuid) {
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();

        return Long.toHexString(mostSigBits) + Long.toHexString(leastSigBits);
    }
    private static String getString3(UUID uuid) {
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();

        return new StringBuilder().append(Long.toHexString(mostSigBits)).append(Long.toHexString(leastSigBits)).toString();
    }
}
