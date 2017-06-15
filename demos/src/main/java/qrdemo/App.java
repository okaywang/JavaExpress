/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package qrdemo;

import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.glxn.qrgen.image.ImageType;
import org.apache.commons.codec.binary.Base64;


/**
 * Created by guojun.wang on 2017/6/14.
 */
public class App {
    public static void main(String[] args) {
        System.out.println("qr test");
        String test = getQRImageBase64("http://rd2.zhaopin.com", 140, 140);
        System.out.println(test);
    }


    /**
     * 生成图片二维码
     *
     * @param qrtext 地址
     * @param width  宽
     * @param height 高
     * @return
     */
    public static String getQRImageBase64(String qrtext, int width, int height) {
        byte[] imageBytes = net.glxn.qrgen.QRCode.from(qrtext).withErrorCorrection(ErrorCorrectionLevel.H).withHint(EncodeHintType.MARGIN, 1).withSize(width, height).to(ImageType.PNG).stream().toByteArray();
        String base64data = new String((new Base64()).encode(imageBytes));
        return "data:image/jpg;base64," + base64data;

    }

    /**
     * 生成图片二维码
     *
     * @param qrtext 地址
     * @param width  宽
     * @param height 高
     * @return
     */
    public static byte[] getQRImage(String qrtext, int width, int height) {
        byte[] imageBytes = net.glxn.qrgen.QRCode.from(qrtext).withErrorCorrection(ErrorCorrectionLevel.H).withHint(EncodeHintType.MARGIN, 1).withSize(width, height).to(ImageType.PNG).stream().toByteArray();
        return imageBytes;
    }
}
