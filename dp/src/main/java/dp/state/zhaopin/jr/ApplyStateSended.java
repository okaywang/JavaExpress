package dp.state.zhaopin.jr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * foo...Created by wgj on 2017/8/5.
 */
public class ApplyStateSended implements ApplyState {
    private static final Logger logger = LoggerFactory.getLogger(ApplyStateSended.class);

    public static ApplyStateSended instance = new ApplyStateSended();

    @Override
    public void seePhonenumber(Applying applying) {
        logger.warn("applying {} is in sended state, invalid ops!", applying.getGid());
    }

    @Override
    public void sendInterview(Applying applying) {
        logger.warn("applying {} is in sended state, invalid ops!", applying.getGid());
    }

    @Override
    public void makeImproper(Applying applying) {
        logger.warn("applying {} is in sended state, invalid ops!", applying.getGid());

    }
}
