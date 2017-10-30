package nutz.job;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.impl.SimpleDataSource;
import org.nutz.dao.pager.Pager;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by guojun.wang on 2017/10/30.
 */
public class App {
    public static void main(String[] args) {
        SimpleDataSource dataSource = new SimpleDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://127.0.0.1/jeesite");
        dataSource.setUsername("postgres");
        dataSource.setPassword("111111");

        Dao dao = new NutDao(dataSource);

//        for (int i = 0; i < 100; i++) {
//            JobEntity testEntityToAdd = getTestEntityToAdd(i, new Random().nextInt(5));
//            dao.insert(testEntityToAdd);
//        }

//        System.out.println(get(dao,33));

//        JobEntity jobEntity = get(dao, 66);
//        jobEntity.setStatus(6);
//        dao.update(jobEntity);

//        List<JobEntity> items = search(dao, 12001, 1);
//        System.out.println(items.size());

        QueryResult search = search(dao, 12001, 1, new Pager(1, 10));
        System.out.println(search.getList().size());
        System.out.println(search.getPager().toString());
    }

    private static void createTable(Dao dao) {
        dao.create(JobEntity.class, true);
    }

    private static JobEntity add(Dao dao, JobEntity entity) {
        JobEntity insert = dao.insert(entity);
        return insert;
    }

    private static JobEntity get(Dao dao, long jobId) {
        JobEntity fetch = dao.fetch(JobEntity.class, jobId);
        return fetch;
    }

    private static int remove(Dao dao, long jobId) {
        int result = dao.delete(JobEntity.class, jobId);
        return result;
    }

    private static List<JobEntity> search(Dao dao, long companyId, int status) {
        List<JobEntity> entities = dao.query(JobEntity.class, Cnd.where("companyId", "=", companyId));
        return entities;
    }

    private static QueryResult search(Dao dao, long companyId, int status, Pager pager) {
        Cnd cnd = Cnd.where("companyId", "=", companyId);
        List<JobEntity> entities = dao.query(JobEntity.class, cnd, pager);
        int count = dao.count(JobEntity.class, cnd);
        pager.setRecordCount(count);
        return new QueryResult(entities, pager);
    }

    private static JobEntity getTestEntityToAdd(long companyId, int status) {
        JobEntity entity = new JobEntity();
        entity.setCompanyId(companyId);
        entity.setJobTitle("Java 工程师");
        entity.setStatus(status);
        entity.setDescription("熟悉spring." + UUID.randomUUID().toString());
        return entity;
    }
}
