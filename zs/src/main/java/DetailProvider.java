import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by wangguojun01 on 2018/2/1.
 */
public class DetailProvider {
    public String getDetail(String code, LocalDate localDate) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String url = new DetailUrl(code, localDate).getUrl();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            String data = EntityUtils.toString(entity1, "gb2312");
            EntityUtils.consume(entity1);
            return data;
        } finally {
            response1.close();
        }
    }
}
