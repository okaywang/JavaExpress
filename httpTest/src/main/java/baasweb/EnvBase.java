package baasweb;

/**
 * Created by wangguojun01 on 2018/2/27.
 */
public abstract class EnvBase {
    protected String getProtocal() {
        return "http";
    }

    protected abstract String getHost();

    protected int getPort() {
        return 8080;
    }

    public String getUrl(String str) {
        return String.format("%s://%s:%d/%s", getProtocal(), getHost(), getPort(), str);
    }
}
