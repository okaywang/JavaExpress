package demo;

import org.apache.ibatis.annotations.Param;

/**
 * Created by guojun.wang on 2017/3/7.
 */
public interface BlogDao {
    public Blog getBlog(@Param("id") long id);

    public int insertBlog(@Param("blog") Blog blog);
}
