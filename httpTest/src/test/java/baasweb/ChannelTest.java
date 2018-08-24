package baasweb;

import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

/**
 * Created by wangguojun01 on 2018/8/9.
 */
public class ChannelTest {
    private static final Logger logger = LoggerFactory.getLogger(ChannelTest.class);

    EnvBase env = new EnvLocal();

    @Test
    public void createTest() throws UnirestException {
        String url = env.getUrl("/baaschannel/create");
        logger.info(url);
        HttpResponse<String> response = Unirest.get(url)
                .asString();
        logger.info(response.getBody());
    }

    @Test
    public void listTest() throws UnirestException {
        HttpApiBase api = new ChannelListApi();
        HttpResponse<String> response = Unirest.get(api.getUrl())
                .asString();
        String body = response.getBody();
        ChannelListApi.ChannelListItem channelListItem = JSON.parseObject(body, ChannelListApi.ChannelListItem.class);
        logger.info(channelListItem.toString());
    }
}
