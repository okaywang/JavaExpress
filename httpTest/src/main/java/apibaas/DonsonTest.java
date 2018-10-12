package apibaas;

import java.util.*;

/**
 * Created by wangguojun01 on 2018/10/11.
 */
public class DonsonTest {
    public static void main(String[] args) {
//        Map<String,String> map = new TreeMap<>();
//        map.put("10.126.93.123","1");
//        map.put("10.120.93.123","2");
//        map.put("10.121.2.5","3");
//
//
//        Set<String> strings = map.keySet();
//        for (Object string :  strings.toArray()){
//            System.out.println(string);
//        }

        testSign();
    }

    public static void testSign() {
        String key = "7X4ORvb7UthwMCuzcdcAQsv3ROxDfUm1";
        TreeMap<String,String> parms = new TreeMap<>();
        parms.put("appKey","83abd376c8a75035b6fcf74339497945");
        parms.put("orgKey","8yNmfBMIhL8fJl4MEQX15MxVfhCCbZdS");
        parms.put("channelName","channelxx");
        parms.put("chaincodeName","ccmsp");
        parms.put("chaincodeVersion","1.0");
        parms.put("func","query");
        parms.put("args","a");
        parms.put("nonce","1234");
        String s = generateWebSign(key, parms);
        System.out.println("s:"+s);
        for (Map.Entry<String,String> entry : parms.entrySet()){
            System.out.println(entry.getKey());
        }
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


    public static String generateWebSign(String key, Map<String, String> args) {
        TreeMap<String, String> map = new TreeMap();
        map.putAll(args);
        Set<Map.Entry<String, String>> entries = map.entrySet();
        StringBuilder sb = new StringBuilder();
        Iterator i$ = entries.iterator();

        while(i$.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)i$.next();
            sb.append((String)entry.getKey());
            sb.append("=");
            sb.append((String)entry.getValue());
            sb.append("&");
        }

        sb.append("key=");
        sb.append(key);
        return MD5Util.MD5Encode(sb.toString()).toUpperCase();
    }
}
