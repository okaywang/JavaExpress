package rf.contract;

import rf.contract.News;

/**
 * Created by wangguojun01 on 2017/12/4.
 */
public interface INewsService {
    News getNewsByID(int id);
}
