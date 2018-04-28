package reflection.zhaopinscf;

import com.alibaba.fastjson.JSON;
import reflection.zhaopinscf.remote.PositionServiceImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangguojun01 on 2018/4/24.
 */
public class PositionController {
    private PositionService positionService = new PositionServiceImpl();

    public String getPositionListV1() {
        long userId = 345;
        String ppu = "ppppppppp";
        Map<String, String> params = new HashMap<>();
        params.put("test1", "11111111");
        params.put("test2", "2222");

        String[] positionList = positionService.getPositionList(userId, ppu, params);
        return JSON.toJSONString(positionList);
    }


    public String getPositionListV2() {
        long userId = 345;
        String ppu = "ppppppppp";
        Map<String, String> params = new HashMap<>();
        params.put("test1", "11111111");
        params.put("test2", "2222");

        Object getPositionList = invokeMethod(positionService, "getPositionList", userId, ppu, params);
        return JSON.toJSONString(getPositionList);
    }

    private Object invokeMethod(Object obj, String methodName, Object... args) {
        try {
            Class[] cArg = new Class[3];
            cArg[0] = long.class;
            cArg[1] = String.class;
            cArg[2] = Map.class;
            Method method = obj.getClass().getMethod(methodName, cArg);
            Object result = method.invoke(obj, args);
            return result;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "";
    }
}
