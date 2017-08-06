package dp.state.zhaopin.jr;

/**
 * foo...Created by wgj on 2017/8/5.
 */
public class Applying {
    private long gid;
    private ApplyState state;

    public Applying(long gid) {
        this(gid, ApplyStateRaw.instance);
    }

    public Applying(long gid, ApplyState state) {
        this.gid = gid;
        this.state = state;
    }

    public void seePhonenumber() {
        state.seePhonenumber(this);
    }

    public void sendInterview() {
        state.sendInterview(this);

    }

    public void makeImproper() {
        state.makeImproper(this);

    }

    public void setState(ApplyState state) {
        this.state = state;
    }

    public long getGid() {
        return gid;
    }

    public void setGid(long gid) {
        this.gid = gid;
    }
}
