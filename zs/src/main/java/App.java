import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import common.AStockHelper;
import common.CurrentDetail;
import common.DaoHelper;
import common.InitHelper;
import domain.StandardDetailItem;
import org.apache.commons.io.IOUtils;
import provider.DayProvider;
import provider.MinuteItem;
import provider.MinuteProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by wangguojun01 on 2018/1/31.
 */
public class App {
    /*
    0930 72.51 5626\n\
0931 73.29 7743\n\
0932 73.30 10165\n\
0933 72.83 11374\n\
0934 73.20 13108\n\
0935 73.80 14700\n\
0936 74.40 17053\n\
0937 74.29 19071\n\
0938 74.88 21248\n\
0939 75.68 26138\n\
0940 75.20 29198\n\
0941 75.10 31456\n\
0942 74.50 33736\n\
0943 74.99 34724\n\
     */
    public static void main(String[] args) throws IOException {

        //InitHelper.initStocks();


//        DayProvider.getData("300216");

        String code = "300277";
        MinuteItem[] data = MinuteProvider.getData(code);

        long start = System.currentTimeMillis();
//        for (MinuteItem datum : data) {
//            System.out.println(datum);
//            DaoHelper.insert(code, datum);
//        }
        DaoHelper.insertBatch(code, data);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println("over");
//        MyContainer container = new MyContainer(node);
    }

    private static void ttt() {
        StandardDetailItem[] items = new StandardDetailItem[5];
        StandardDetailItem item = new StandardDetailItem("09:25", 5626, 10);
        items[0] = new StandardDetailItem("09:25", 5626, 0);
        items[1] = new StandardDetailItem("09:30", 7626, 0.1);
        items[2] = new StandardDetailItem("09:31", 5600, 0.03);
        items[3] = new StandardDetailItem("09:32", 3600, -0.09);
        items[4] = new StandardDetailItem("09:33", 3600, 0.05);

        represent(items);
    }

    public static void represent(StandardDetailItem[] items) {
        for (StandardDetailItem item : items) {
            System.out.println(new String());
            System.out.println(item.getPriceDelta());
        }
    }

    private static void test() throws IOException {
        String detail = new DayDetailProvider().getDetail("300216", LocalDate.now());
        System.out.println(detail);

        CurrentDetail current = new CurrentDetailProvider().getDetail("300216", LocalDate.now());
        System.out.println(current);
    }
}
