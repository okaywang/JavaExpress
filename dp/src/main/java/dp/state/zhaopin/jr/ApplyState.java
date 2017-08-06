package dp.state.zhaopin.jr;

/**
 * foo...Created by wgj on 2017/8/5.
 */
public interface ApplyState {
    void seePhonenumber(Applying applying);

    void sendInterview(Applying applying);

    void makeImproper(Applying applying);
}
