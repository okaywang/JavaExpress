package nutz.el;

import org.nutz.el.El;
import org.nutz.lang.Lang;
import org.nutz.lang.util.Context;

/**
 * Created by guojun.wang on 2017/10/27.
 */
public class App {
    public static void main(String[] args) {
        System.out.println(El.eval("3+4*5").equals(23));  // 将打印 true，够简单吧

        Context context = Lang.context();
        context.set("a", 10);
        System.out.println(El.eval(context, "a*10"));  // 将打印 100
    }
}
