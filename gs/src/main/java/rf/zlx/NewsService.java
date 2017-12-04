package rf.zlx;

import rf.contract.INewsService;
import rf.contract.News;

/**
 * Created by wangguojun01 on 2017/12/4.
 */
public class NewsService implements INewsService {
    @Override
    public News getNewsByID(int id) {
        News news = new News();
        news.setNewsID(id);
        news.setTitle("title85631");
        return news;
    }
}
