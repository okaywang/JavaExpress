package dp.state.zhaopin.jr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * foo...Created by wgj on 2017/8/5.
 */
public class ApplyStateSeen implements ApplyState {
    private static final Logger logger = LoggerFactory.getLogger(ApplyStateSeen.class);
    public static ApplyStateSeen instance = new ApplyStateSeen();

    @Override
    public void seePhonenumber(Applying applying) {
        logger.warn("applying {} is in seen state, invalid ops!", applying.getGid());
    }

    @Override
    public void sendInterview(Applying applying) {
        logger.info("applying {} perform sendInterview", applying.getGid());
        applying.setState(ApplyStateSended.instance);
        logger.info("applying {} set state to sended",  applying.getGid());

    }

    @Override
    public void makeImproper(Applying applying) {
        logger.info("applying {} perform makeImproper", applying.getGid());
        applying.setState(ApplyStateImproper.instance);
        logger.info("applying {} set state to improper", applying.getGid());
    }
}
