package rsa;

import java.io.*;
import java.security.*;

/**
 * Created by wangguojun01 on 2018/4/28.
 */
public class KeyPairGenerator {
    public static final String KEY_PATH = "D:\\workspace\\JavaExpress\\bc\\src\\main\\resources\\";
    private static final String PUBLIC_KEY_FILENAME = KEY_PATH + "public";
    private static final String PRIVATE_KEY_FILENAME = KEY_PATH + "private";

    //创建公私钥对
    public void generate() throws Exception {
        //使用RSA算法获得密钥对生成器对象keyPairGenerator
        java.security.KeyPairGenerator keyPairGenerator = java.security.KeyPairGenerator.getInstance("RSA");
        //设置密钥长度为1024
        keyPairGenerator.initialize(1024);
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //获取公钥
        PublicKey publicKey = keyPair.getPublic();
        //获取私钥
        PrivateKey privateKey = keyPair.getPrivate();
        //保存公钥对象和私钥对象为持久化文件
        ObjectOutputStream oos1 = null;
        ObjectOutputStream oos2 = null;
        try {
            oos1 = new ObjectOutputStream(new FileOutputStream(PUBLIC_KEY_FILENAME));
            oos2 = new ObjectOutputStream(
                    new FileOutputStream(PRIVATE_KEY_FILENAME));
            oos1.writeObject(publicKey);
            oos2.writeObject(privateKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            oos1.close();
            oos2.close();
        }
    }

    public KeyPair getKeyPair() {
        //读取持久化的公钥对象
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILENAME));
            PublicKey publicKey = (PublicKey) ois.readObject();

            ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILENAME));
            PrivateKey privateKey = (PrivateKey) ois2.readObject();

            return new KeyPair(publicKey, privateKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}