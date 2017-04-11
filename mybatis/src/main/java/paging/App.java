package paging;

import demo.Blog;
import demo.BlogDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * foo...Created by wgj on 2017/4/11.
 */
public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("this is mybatis demo");
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, "home");
        //sqlSessionFactory.getConfiguration().setDefaultStatementTimeout(2);
        sqlSessionFactory.getConfiguration();
        SqlSession session = sqlSessionFactory.openSession(true);
        //Environment environment = new Environment();

        //sqlSessionFactory.getConfiguration().setEnvironment(environment);
        BlogDao mapper = session.getMapper(BlogDao.class);
        Blog blog = mapper.getBlog(1);
        SearchParams params = new SearchParams();
        params.setBlogName("1");
        params.setPage(0);
        params.setPageSize(10);
        List<Blog> blogs = mapper.search(params);
        System.out.println(blogs.size());
        System.out.println(blog);
    }
}
