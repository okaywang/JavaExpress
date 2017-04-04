package test;

import java.lang.reflect.Proxy;

/**
 * foo...Created by wgj on 2017/4/2.
 */
public class App {
    public static void main(String[] args) {
        RealHander hander = new RealHander();
        IPersonDao dao = (IPersonDao) Proxy.newProxyInstance(App.class.getClassLoader(), new Class[]{IPersonDao.class}, hander);
        Class[] cls = dao.getClass().getInterfaces();
        System.out.println(dao.getName());
        System.out.println(dao.getCount());
    }
}
