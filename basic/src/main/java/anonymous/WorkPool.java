package anonymous;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guojun.wang on 2017/10/22.
 */
public class WorkPool {

    public void addWorkItem(String channel, Runnable runnable){
        System.out.println(runnable.getClass().getFields());
        System.out.println(runnable.getClass().getDeclaredFields());
        System.out.println(runnable.getClass());
    }

}
