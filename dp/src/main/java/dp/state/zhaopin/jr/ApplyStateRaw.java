package dp.state.zhaopin.jr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * foo...Created by wgj on 2017/8/5.
 */
public class ApplyStateRaw implements ApplyState {
    private static final Logger logger = LoggerFactory.getLogger(ApplyStateRaw.class);

    public static ApplyStateRaw instance = new ApplyStateRaw();

    @Override
    public void seePhonenumber(Applying applying) {
        logger.info("applying {} perform seePhonenumber", applying.getGid());
        applying.setState(ApplyStateSeen.instance);
        logger.info("applying {} set state to Seen", applying.getGid());
    }

    @Override
    public void sendInterview(Applying applying) {
        logger.warn("applying {} is in raw state, invalid ops!", applying.getGid());
    }

    @Override
    public void makeImproper(Applying applying) {
        logger.info("applying {} perform makeImproper", applying.getGid());
        applying.setState(ApplyStateImproper.instance);
        logger.info("applying {} set state to improper", applying.getGid());
    }
}
