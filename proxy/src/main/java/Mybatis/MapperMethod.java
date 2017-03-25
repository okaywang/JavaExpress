package Mybatis;

import java.lang.reflect.Method;

/**
 * foo...Created by wgj on 2017/3/25.
 */
public class MapperMethod {
    private Method method;

    public MapperMethod(Method method) {
        this.method = method;
    }

    public Object execute(Object[] args) {
        if (method.getName().contains("Resume")) {
            ResumeEntity entity = new ResumeEntity();
            entity.setId(100);
            entity.setVersion("1-1(data of " + args[0] + ")");
            return entity;
        } else if (method.getName().contains("ById")) {
            BlogEntity entity = new BlogEntity();
            entity.setId(100);
            entity.setName("20170325复盘(data of " + args[0] + ")");
            return entity;
        }
        return null;
    }
}