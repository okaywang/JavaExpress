import common.AStockHelper;

import java.time.LocalDate;

/**
 * Created by wangguojun01 on 2018/2/1.
 */
public class CurrentDetailUrl {
    private String code;
    private LocalDate localDate;

    //private String url = "http://market.finance.sina.com.cn/downxls.php?date=2018-01-31&symbol=sz300216";
    private static final String url_pattern = "http://hq.sinajs.cn/rn=%d&list=%s";
    private static final String response_encoding = "gb2312";

    public CurrentDetailUrl(String code) {
        this.code = code;
    }

    public String getUrl() {
        return String.format(url_pattern, System.currentTimeMillis(), AStockHelper.prefix(this.code));
    }
}
