package provider;

import common.AStockHelper;
import common.CurrentDetail;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;

public class MinuteProvider {
    private static final Logger logger = LoggerFactory.getLogger(MinuteProvider.class);
    private static final String URL = "http://data.gtimg.cn/flashdata/hushen/minute/%s.js";

    public static MinuteItem[] getData(String code) {
        String url = String.format(URL, AStockHelper.prefix(code));
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            HttpEntity entity = response1.getEntity();
            String data = EntityUtils.toString(entity, "gb2312");
            String[] items = data.split("\n");
            MinuteItem[] minuteItems = new MinuteItem[items.length - 2];
            float lastPrice = DayProvider.getData(code, AStockHelper.previousDay()).getClose();
            for (int i = 2; i < items.length - 1; i++) {
                String[] subItems = items[i].split(" ");
                MinuteItem mi = new MinuteItem();
                mi.setTime(subItems[0]);
                mi.setPrice(Float.parseFloat(subItems[1]));
                mi.setVolume(Integer.parseInt(subItems[2].replace("\\n\\", "")));
                mi.setDelta(mi.getPrice() - lastPrice);
                minuteItems[i - 2] = mi;
                lastPrice = mi.getPrice();
            }
            EntityUtils.consume(entity);
            return minuteItems;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
