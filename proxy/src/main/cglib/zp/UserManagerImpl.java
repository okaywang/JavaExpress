package zp;

/**
 * foo...Created by wgj on 2017/9/21.
 */
public class UserManagerImpl {
    public void addUser(String id, String password) {
        System.out.println(".: 掉用了UserManagerImpl.addUser()方法！ ");

    }

    public String delUser(String id) {
        System.out.println(".: 掉用了UserManagerImpl.delUser()方法！ ");
        return "success";

    }
}
