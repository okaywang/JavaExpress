package baasweb;

/**
 * Created by wangguojun01 on 2018/2/27.
 */
public class EnvLocal extends EnvBase {
    @Override
    protected String getHost() {
        return "127.0.0.1";
    }
}
