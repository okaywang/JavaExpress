package nutzdemo;


import org.nutz.castor.Castors;
import org.nutz.lang.Lang;

import java.util.Date;
import java.util.Map;

/**
 * Created by wangguojun01 on 2017/12/18.
 */
public class App {
    public static void main(String[] args) {
        Date d = new Date(1514197623678L);
        System.out.println(d);


        Map<String,Object> map = Lang.map("{a:10, b:'ABC', c:true}");
        System.out.println(map);

        int age = Castors.me().castTo("33a", int.class);
        System.out.println(age);
    }
}
