package zp;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;

/**
 * foo...Created by wgj on 2017/9/21.
 */
public class App1 {
    public static void main(String[] args) {
        //System.out.println(11);

        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(UserManagerImpl.class);

        /*enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "Hello cglib!";
            }
        });*/

        //java lambda expression 与上面的代码段效果一样
        enhancer.setCallback((FixedValue) () -> {

            return "Hello aaaa!";
        });

        UserManagerImpl proxy = (UserManagerImpl) enhancer.create();
        System.out.println(proxy.getClass());
        String ret = proxy.delUser("mf");

        System.out.println(ret);
    }
}
