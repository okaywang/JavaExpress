package Mybatis;

/**
 * foo...Created by wgj on 2017/3/25.
 */
public class App {
    public static void main(String[] args) {
//        MapperProxyFactory<BlogDao> factory = new MapperProxyFactory<>(BlogDao.class);
//        MapperProxy<BlogDao> mapperProxy = new MapperProxy<>();
//        BlogDao dao = factory.newInstance(mapperProxy);
//        BlogEntity entity = dao.getById(3);
//        System.out.println(entity);


        MapperProxyFactory<ResumeDao> factory2 = new MapperProxyFactory<>(ResumeDao.class);
        MapperProxy<ResumeDao> mapperProxy2 = new MapperProxy<>();
        ResumeDao dao2 = factory2.newInstance(mapperProxy2);
        ResumeEntity entity2 = dao2.getResume(3);
        System.out.println(entity2);
    }
}
