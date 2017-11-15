package com.zhaopin;

import org.apache.ibatis.binding.MapperMethod;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by guojun.wang on 2017/11/15.
 */

public class ShardingDataSourceRouter implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    public void onRoute(String shardingTableName, Object parameter) {
        MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) parameter;
        if(!paramMap.containsKey("userId")){
            ShardingContextHolder.setDBKey("rd1");
            return;
        }
        int userId = Integer.parseInt(paramMap.get("userId").toString());
        ShardingContextHolder.setDBKey(userId % 2 == 0 ? "rd1" : "rd2");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
