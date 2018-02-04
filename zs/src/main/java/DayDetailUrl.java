import common.AStockHelper;

import java.sql.DatabaseMetaData;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Locale;

/**
 * Created by wangguojun01 on 2018/2/1.
 */
public class DayDetailUrl {
    private String code;
    private LocalDate localDate;

    //private String url = "http://market.finance.sina.com.cn/downxls.php?date=2018-01-31&symbol=sz300216";
    private static final String url_pattern = "http://market.finance.sina.com.cn/downxls.php?date=%s&symbol=%s";
    private static final String response_encoding = "gb2312";

    public DayDetailUrl(String code, LocalDate localDate) {
        this.code = code;
        this.localDate = localDate;
    }

    public String getUrl() {
        return String.format(url_pattern, this.localDate, AStockHelper.prefix(this.code));
    }
}
