package rsa;

import java.security.Key;
import java.security.KeyPair;
import java.util.Scanner;

public class RSADemo {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyGenerator = new KeyPairGenerator();
//        keyGenerator.generate();
        KeyPair keyPair = keyGenerator.getKeyPair();
        System.out.println("current keyPair: " + keyPair.toString());
        System.out.println("请输入明文：");
        Scanner sca = new Scanner(System.in);
        String str = sca.nextLine();

        testRSA(str, keyPair.getPublic(), keyPair.getPrivate());
        testRSA(str, keyPair.getPrivate(), keyPair.getPublic());
    }

    private static void testRSA(String str, Key encryptKey, Key decryptKey) throws Exception {
        System.out.println("============================");
        String secret = RSAUtils.encryptWithRSA(str, encryptKey);
        System.out.println("经过RSA加密后的密文为：");
        System.out.println(secret);
        System.out.println("============================");
        String original = RSAUtils.decryptWithRSA(secret, decryptKey);
        System.out.println("经过RSA解密后的原文为：");
        System.out.println(original);
    }
}