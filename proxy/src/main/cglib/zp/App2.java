package zp;

/**
 * foo...Created by wgj on 2017/9/21.
 */
public class App2 {
    public static void main(String[] args) {
        UserManagerImpl userManager = (UserManagerImpl) new CGLibProxy()
                .createProxyObject(new UserManagerImpl());
        String str = userManager.delUser("tom");
        System.out.println(str);
    }
}
