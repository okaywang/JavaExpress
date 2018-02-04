package provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.AStockHelper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class DayProvider {
    private static final String URL = "http://web.ifzq.gtimg.cn/appstock/app/fqkline/get?param=%s,day,2014-01-01,%s,%d,qfq&r=0.008972307388778633";

    public static DayItem getData(String code) {
        return getData(code, LocalDate.now());
    }

    public static DayItem getData(String code, LocalDate date) {
        String url = String.format(URL, AStockHelper.prefix(code), date.toString(), 1);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            HttpEntity entity = response1.getEntity();
            String data = EntityUtils.toString(entity, "gb2312");
            JSONObject jsonObject = JSON.parseObject(data);
            Map<String,Object> map = (Map<String,Object>)jsonObject.get("data");
            Map<String,Object>  o = (Map<String,Object>) map.get(AStockHelper.prefix(code));
            JSONArray qfqday = (JSONArray)o.get("qfqday");
            JSONArray items = (JSONArray)qfqday.get(0);
            DayItem dayItem = new DayItem();
            dayItem.setOpen(items.getFloat(1));
            dayItem.setClose(items.getFloat(2));
            dayItem.setVolume(items.getFloat(5).intValue());
            return dayItem;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
