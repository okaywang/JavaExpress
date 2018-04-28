package hash;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wangguojun01 on 2018/4/23.
 */
public class App {
    //look up hash value starting with "0000"
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        test("Hello, world!0");
        test("Hello, world!1");
        //...
        test("Hello, world!4249");
        test("Hello, world!4250");
        System.out.println("takes ms: " + (System.currentTimeMillis() - start));
    }

    private static void test(String str) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            System.out.println(sha256(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String sha256(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest messageDigest;
        messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(bytes);
        byte[] resultBytes = messageDigest.digest();
        String resultStr = Hex.encodeHexString(resultBytes);
        return resultStr;

    }
}
