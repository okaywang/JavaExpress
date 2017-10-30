package nutz;

import org.nutz.castor.Castors;

import java.util.Calendar;

/**
 * Created by guojun.wang on 2017/10/27.
 */
public class App {
    public static void main(String[] args) {
        System.out.println(Castors.me().castTo("563", int.class));
        Calendar c = Castors.me().castTo("2009-11-12 15:23:12", Calendar.class);
        System.out.println(c.getTime());
    }
}
