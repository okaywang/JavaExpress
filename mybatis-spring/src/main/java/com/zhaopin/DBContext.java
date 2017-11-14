package com.zhaopin;

/**
 * Created by guojun.wang on 2017/11/14.
 */
public class DBContext {
    //define count of database and it must match with resources/properties/jdbc.properties
    private static final int DB_COUNT = 2;

    private static final ThreadLocal<String> tlDbKey = new ThreadLocal<String>();

    public static String getDBKey() {
        return tlDbKey.get();
    }

    public static void setDBKey(String dbKey) {
        tlDbKey.set(dbKey);
    }

    public static String getDBKeyByUserId(int userId) {
        int dbIndex = userId % DB_COUNT;
        return "rd" + (++dbIndex);
    }
}
