package tardemo.zip;

/**
 * Created by wangguojun01 on 2018/5/17.
 */
public class TestZip {
    public static void main(String[] args)
    {
        try
        {
//            ZipCompress.zip("D:\\opt\\fabric\\cc\\download\\74\\src\\codetest.zip",
//                    "D:\\opt\\fabric\\cc\\download\\74\\src\\codetest");

            ZipCompress2 zipCom = new ZipCompress2("D:\\opt\\fabric\\cc\\download\\74\\src\\codetest.zip","D:\\opt\\fabric\\cc\\download\\74\\src\\codetest");
            zipCom.zip();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
