package com.zhaopin;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by guojun.wang on 2017/11/14.
 */
public interface BlogMapper {
    @Select("SELECT * FROM blog WHERE id = #{id}")
    Blog getBlog(@Param("userId") int userId, @Param("id") int id);
}
