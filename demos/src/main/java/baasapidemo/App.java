//import com.bj58.arch.guarantee.common.util.SignUtil;
//import com.bj58.arch.guarantee.common.util.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by donson on 2018/8/12.
 */
public class App {

    public static void main(String[] args) {
        testSign();
    }

    public static void testSign() {
        String key = "hqZANYptrTdLgyj8lxtV540MgWHB6pNT";
        TreeMap<String,String> parms = new TreeMap<>();
        parms.put("appKey","5132625b8a293de170fb20da7e76d0f2");
        parms.put("orgKey","DQAXsM3FYP7QxHVkJnpAjmK4uFgT0to7");
        parms.put("channelName","mychannel123");
        parms.put("chaincodeName","test1");
        parms.put("chaincodeVersion","1.0");
        parms.put("func","invoke");
        parms.put("args0","a");
        parms.put("args1","b");
        parms.put("args2","10");
        parms.put("nonce","1234");
//        String s = SignUtil.generateWebSign(key, parms);
//        System.out.println("s:"+s);
//        for (Map.Entry<String,String> entry : parms.entrySet()){
//            System.out.println(entry.getKey());
//        }
    }

    public static void testSign1() {
        String key = "wexfg13x";
        Map<String,String> parms = new HashMap<>();
        parms.put("orderNo","125638828840074240");
        parms.put("bizCode","12");
        parms.put("channel","2");
        parms.put("plat","2");
        parms.put("tid","123796");
        parms.put("orderExpireTime","2018-03-12 15:30:30");
        parms.put("sign","89D6F55D527030627187A070E1E4C4EB");
        parms.put("jsonpCallback","jsonpCallback");
        //String s = SignUtil.generateWebSign(key, parms);
        //System.out.println("s:"+s);
        for (Map.Entry<String,String> entry : parms.entrySet()){
            System.out.println(entry.getKey());
        }
    }
}
