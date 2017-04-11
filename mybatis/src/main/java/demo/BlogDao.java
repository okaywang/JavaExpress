package demo;

import org.apache.ibatis.annotations.Param;
import paging.SearchParams;

import java.util.List;

/**
 * Created by guojun.wang on 2017/3/7.
 */
public interface BlogDao {
    public Blog getBlog(@Param("id") long id);

    public List<Blog> search(@Param("searchParams") SearchParams searchParams);

    public int insertBlog(@Param("blog") Blog blog);
}
