package com.zhaopin;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by guojun.wang on 2017/11/14.
 */
public class MyRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return ShardingContextHolder.getDBKey();
    }
}
