package nutz.dao;

import org.nutz.dao.Dao;
import org.nutz.dao.entity.Record;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.impl.SimpleDataSource;
import org.nutz.dao.pager.Pager;

import java.util.List;

/**
 * Created by guojun.wang on 2017/10/27.
 */
public class App {
    public static void main(String[] args) {
        // 创建一个数据源
        SimpleDataSource dataSource = new SimpleDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://127.0.0.1/jeesite");
        dataSource.setUsername("postgres");
        dataSource.setPassword("111111");

        // 创建一个NutDao实例,在真实项目中, NutDao通常由ioc托管, 使用注入的方式获得.
        Dao dao = new NutDao(dataSource);

//        System.out.println(dao.meta());
        Person p = dao.fetch(Person.class, 1);
        System.out.println("---->" + p.getName());
        p.setName("yj");
        dao.updateWithVersion(p);
        p = dao.fetch(Person.class, 1);
        System.out.println("---->" + p.getName());
        //        // 创建表
//        dao.create(Person.class, false); // false的含义是,如果表已经存在,就不要删除重建了.
//
        System.out.println(dao.count(Person.class));
        Pager pager = dao.createPager(1, 3);
        List<Person> query = dao.query(Person.class, null, pager);
        System.out.println(query);
//        Person p = new Person();
//        p.setName("ggg");
//        p.setAge(12);
//        Person insert = dao.insert(p);
//        System.out.println(insert.getName());
//        System.out.println(p.getId());
    }
}
