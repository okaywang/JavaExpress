package baasweb;

/**
 * Created by wangguojun01 on 2018/8/9.
 */
public class ChannelCreateApi extends HttpApiBase {
    EnvBase env = new EnvLocal();

    @Override
    String getUrl() {
        return env.getUrl("/channel/create");
    }

    @Override
    Class<?> getResponseType() {
        return null;
    }
}
