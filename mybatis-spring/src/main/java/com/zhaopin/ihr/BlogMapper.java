package com.zhaopin.ihr;

import com.zhaopin.ihr.Blog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by guojun.wang on 2017/11/14.
 */
public interface BlogMapper {
    @Select("SELECT * FROM blog WHERE id = #{id}")
    Blog getBlog(@Param("userId") int userId, @Param("id") int id);

    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    @Insert("insert into blog(blogname,blogtype) values(#{entity.blogName},#{entity.blogType})")
    int insert(@Param("entity") Blog blog);
}
