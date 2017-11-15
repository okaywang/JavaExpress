package com.zhaopin;


import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;

import javax.annotation.Resource;
import java.sql.Connection;
import java.util.Properties;

/**
 * Created by guojun.wang on 2017/11/15.
 */
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
public class MySqlIntercepter implements Interceptor {
    private ShardingDataSourceRouter shardingDataSourceRouter = new ShardingDataSourceRouter();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
            BoundSql boundSql = statementHandler.getBoundSql();
//            String sql = boundSql.getSql().toUpperCase();
            String table = "blog";
            shardingDataSourceRouter.onRoute(table, boundSql.getParameterObject());
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
