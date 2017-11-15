package com.zhaopin;

/**
 * Created by guojun.wang on 2017/11/14.
 */
public class ShardingContextHolder {
    private static final ThreadLocal<String> tlDbKey = new ThreadLocal<String>();

    public static String getDBKey() {
        return tlDbKey.get();
    }

    public static void setDBKey(String dbKey) {
        tlDbKey.set(dbKey);
    }
}
