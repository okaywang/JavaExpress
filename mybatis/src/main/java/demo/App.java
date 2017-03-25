package demo;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by guojun.wang on 2017/3/7.
 */
public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("this is mybatis demo");
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //sqlSessionFactory.getConfiguration().setDefaultStatementTimeout(2);
        sqlSessionFactory.getConfiguration();
        SqlSession session = sqlSessionFactory.openSession(true);
        BlogDao mapper = session.getMapper(BlogDao.class);
        System.out.println(mapper.getBlog(1));

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
