package com.zhaopin;

/**
 * Created by guojun.wang on 2017/3/7.
 */
public class Blog {
    private long id;
    private String blogName;
    private int blogType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public int getBlogType() {
        return blogType;
    }

    public void setBlogType(int blogType) {
        this.blogType = blogType;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", blogName='" + blogName + '\'' +
                ", blogType=" + blogType +
                '}';
    }
}
