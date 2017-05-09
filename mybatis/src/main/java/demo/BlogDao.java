package demo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import paging.SearchParams;

import java.util.List;

/**
 * Created by guojun.wang on 2017/3/7.
 */
public interface BlogDao {
    public Blog getBlog(@Param("id") long id);

    public List<Blog> search(@Param("searchParams") SearchParams searchParams);

    public int insertBlog(@Param("entity") Blog entity);

    @Insert("INSERT INTO Blog (blogname,blogtype) VALUES (#{entity.blogName}, 1)")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id",keyColumn = "id")
    public int insertBlog2(@Param("entity") Blog entity);
}
