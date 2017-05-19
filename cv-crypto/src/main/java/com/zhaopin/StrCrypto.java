package com.zhaopin;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;


/**
 * Created by guojun.wang on 2016/6/13.
 * 字符串混淆加密
 */
public class StrCrypto {

    private byte[] iv;
    private byte[] key;

    public StrCrypto(String iv, String key) {
        this.iv = iv.getBytes();
        this.key = key.getBytes();
    }

    /**
     * 加密
     *
     * @param rootCompanyId
     * @param resumeNumber
     * @return
     * @throws Exception
     */
    public String encrypt(long rootCompanyId, String resumeNumber) throws Exception {
        byte[] rawBytes = Translator.Translate((int) rootCompanyId, resumeNumber);
        byte[] encrptedBytes = encrypt(rawBytes, this.iv, this.key);
        String str = GetEncodedString(encrptedBytes);
        return str;
    }

    /**
     * 解密
     *
     * @param encrptedString
     * @throws Exception
     * @return:返回格式：（公司编号，简历编号）;
     */
    public String decrypt(String encrptedString) throws Exception {
        byte[] encrptedBytes = GetBytesFromEncodedString(encrptedString);
        byte[] rawBytes = decrypt(encrptedBytes, this.iv, this.key);
        String resumeNumber = Translator.Translate(rawBytes);
        return resumeNumber;
    }

    private byte[] GetBytesFromEncodedString(String str) {
        String result = str.replace("(", "+");
        result = result.replace(")", "/");
        int equalCount = str.length() % 4;
        for (int i = 0; i < equalCount; i++) {
            result += "=";
        }
        byte[] bytes = DatatypeConverter.parseBase64Binary(result);
        return bytes;
    }

    private String GetEncodedString(byte[] bytes) {
        String str = DatatypeConverter.printBase64Binary(bytes);
        String result = str.replace("+", "(");
        result = result.replace("/", ")");
        result = result.replace("=", "");
        return result;
    }

    private byte[] encrypt(byte[] bytes, byte[] iv, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom(iv);
        SecretKey sk = new SecretKeySpec(key, "RC2");
        Cipher cipher = Cipher.getInstance("RC2/ECB/NOPadding");
        cipher.init(Cipher.ENCRYPT_MODE, sk, sr);
        byte[] encrypted = cipher.doFinal(bytes);

        return encrypted;
    }

    private static byte[] decrypt(byte[] toDecrypt, byte[] iv, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom(iv);
        SecretKey sk = new SecretKeySpec(key, "RC2");
        Cipher cipher = Cipher.getInstance("RC2/ECB/NOPadding");
        cipher.init(Cipher.DECRYPT_MODE, sk, sr);
        byte[] decrypted = cipher.doFinal(toDecrypt);

        return decrypted;
    }

    public static class Translator {
        public static byte[] Translate(int companyId, String resumeNumber) {
            int userId = Integer.parseInt(resumeNumber.substring(2, 2 + 9));
            int id = Integer.parseInt(resumeNumber.substring(12, 12 + 9));

            byte[] bytesCompanyId = intToByteArray(companyId);
            byte[] bytesUserId = intToByteArray(userId);
            byte[] bytesChar = resumeNumber.substring(1, 1 + 1).getBytes();

            byte[] bytes2 = intToByteArray(id);
            byte[] padding = new byte[]{0, 0, 0};
            byte[] bytes = concat(padding, bytesUserId, bytesChar, bytesCompanyId, bytes2);// bytesUserId.Concat(bytesChar).Concat(bytesCompanyId).Concat(bytes2).ToArray();
            return bytes;
        }

        public static String Translate(byte[] bytes) {
            int paddingCount = 3;
            byte[] bytesUserId = Arrays.copyOfRange(bytes, paddingCount + 0, paddingCount + 4);//  bytes.Take(4).ToArray();
            byte[] byteM = Arrays.copyOfRange(bytes, paddingCount + 4, paddingCount + 4 + 1);// bytes.Skip(4).Take(1).ToArray();
            byte[] bytesCompanyId = Arrays.copyOfRange(bytes, paddingCount + 5, paddingCount + 5 + 4);//bytes.Skip(5).Take(4).ToArray();
            byte[] byte2 = Arrays.copyOfRange(bytes, paddingCount + 9, paddingCount + 9 + 4);//bytes.Skip(9).Take(4).ToArray();

            int companyId = bytesToInt(bytesCompanyId, 0);
            int userId = bytesToInt(bytesUserId, 0);
            char ch = (char) (byteM[0]);
            int id = bytesToInt(byte2, 0);
            String result = String.format("%d,J%c%09dR%09d00", companyId, ch, userId, id);
            return result;
        }


        public static byte[] intToByteArray(int value) {
            ByteBuffer bb = ByteBuffer.allocate(4);
            bb.order(ByteOrder.LITTLE_ENDIAN);
            return bb.putInt(value).array();
        }


        public static int bytesToInt(byte[] ary, int offset) {
            int value;
            value = (int) ((ary[offset] & 0xFF)
                    | ((ary[offset + 1] << 8) & 0xFF00)
                    | ((ary[offset + 2] << 16) & 0xFF0000)
                    | ((ary[offset + 3] << 24) & 0xFF000000));
            return value;
        }


        private static byte[] concat(byte[]... arrays) {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            if (arrays != null) {
                for (final byte[] array : arrays) {
                    if (array != null) {
                        out.write(array, 0, array.length);
                    }
                }
            }
            return out.toByteArray();
        }
    }
}
