package rf.lfb;

import lib.client.proxy.ProxyFactory;
import rf.contract.INewsService;
import rf.contract.News;

/**
 * Created by wangguojun01 on 2017/12/4.
 */
public class App {
    public static void main(String[] args) {
        INewsService newsService = ProxyFactory.create(INewsService.class,"tcp://demoserver/NewsService");
        News obj = newsService.getNewsByID(100);
        System.out.println(obj);
    }
}
