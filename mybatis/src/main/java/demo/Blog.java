package demo;

/**
 * Created by guojun.wang on 2017/3/7.
 */
public class Blog {
    private long id;
    private String blogName;
    private BlogTypeEnum blogType;

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

    public BlogTypeEnum getBlogType() {
        return blogType;
    }

    public void setBlogType(BlogTypeEnum blogType) {
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
