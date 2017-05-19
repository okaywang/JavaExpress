package demo;

import org.apache.ibatis.annotations.*;
import paging.SearchParams;

import java.util.List;

/**
 * Created by guojun.wang on 2017/3/7.
 */
public interface BlogDao {
    public Blog getBlog(@Param("id") long id);

    public List<Blog> getBlogs(@Param("ids") long[] ids);


    @Select({"<script>",
            "   select * from blog where id in",
            "   <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>",
            "       #{item}",
            "   </foreach>",
            "</script>"})
    @ResultMap("blogMap")
    public List<Blog> getBlogs2(@Param("ids") long[] ids);

    public List<Blog> search(@Param("searchParams") SearchParams searchParams);

    public int insertBlog(@Param("entity") Blog entity);

    @Insert("INSERT INTO Blog (blogname,blogtype) VALUES (#{entity.blogName}, 1)")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    public int insertBlog2(@Param("entity") Blog entity);
}
