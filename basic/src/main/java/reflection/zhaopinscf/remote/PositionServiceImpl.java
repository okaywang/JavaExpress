package reflection.zhaopinscf.remote;

import com.alibaba.fastjson.JSON;
import reflection.zhaopinscf.PositionService;

import java.util.Map;

/**
 * Created by wangguojun01 on 2018/4/24.
 */
public class PositionServiceImpl implements PositionService {
    @Override
    public String[] getPositionList(long userId, String ppu, Map<String, String> params) {
        String[] result = new String[3];
        result[0] = String.valueOf(userId);
        result[1] = ppu;
        result[2] = JSON.toJSONString(params.size());
        return result;
    }
}
