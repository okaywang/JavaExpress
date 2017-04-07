package demo;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;

/**
 * Created by guojun.wang on 2017/3/7.
 */
public class App {
    public static void main(String[] args) throws IOException {
        //System.out.println(BlogTypeEnum.Constellation);
        //BlogTypeEnum blogTypeEnum = Enum.valueOf(BlogTypeEnum.class, "Finacial");


        System.out.println("this is mybatis demo");
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, "company");
        //sqlSessionFactory.getConfiguration().setDefaultStatementTimeout(2);
        sqlSessionFactory.getConfiguration();
        SqlSession session = sqlSessionFactory.openSession(true);
        //Environment environment = new Environment();

        //sqlSessionFactory.getConfiguration().setEnvironment(environment);
        BlogDao mapper = session.getMapper(BlogDao.class);
        Blog blog = mapper.getBlog(1);
        System.out.println(sqlSessionFactory.getConfiguration().getDatabaseId());
        System.out.println(blog);


        org.apache.ibatis.binding.MapperProxy m;

//        for (int i = 10; i < 12; i++) {
//            Blog blog = new Blog();
//            blog.setId(i);
//            blog.setBlogName("python11111");
//            System.out.println(mapper.insertBlog(blog));
//        }


        //Blog blog = session.selectOne("demo.BlogDao.getBlog", 2);
        //System.out.println(blog);
    }
}
