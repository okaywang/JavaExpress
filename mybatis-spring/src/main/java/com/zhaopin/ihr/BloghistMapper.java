package com.zhaopin.ihr;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by guojun.wang on 2017/11/14.
 */
public interface BloghistMapper {
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    @Insert("insert into bloghist(bid) values(#{entity.bid})")
    int insert(@Param("entity") Bloghist entity);
}
