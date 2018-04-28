package reflection.zhaopinscf;

import java.util.Map;

/**
 * Created by wangguojun01 on 2018/4/24.
 */
public interface PositionService {
    public String[] getPositionList(long userId, String ppu, Map<String,String> params);
}
