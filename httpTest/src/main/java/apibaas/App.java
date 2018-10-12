package apibaas;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wangguojun01 on 2018/10/11.
 */
public class App {
    //arch_baas_api_web
    public static void main(String[] args) throws UnirestException {
        String host = "apibaas.58.com";
        //String host = "10.8.23.212:8001";
        String api = "http://" + host + "/chaincode/query";
//        String api = "http://" + host + "/chaincode/invoke";

        String secret = "9kFaSY3eZ6LPVw4E3HoBIMKOaxJRcVYx";
        TreeMap<String, String> parms = new TreeMap<>();
        parms.put("appKey", "2222b9e69537a471abccce80b2474fc8");
        parms.put("orgKey", "N4OGdf7SAn87mw3mKbVHiyUsMlwPCQBZ");
        parms.put("channelName", "ccpower");
        parms.put("chaincodeName", "codeyanni");
        parms.put("chaincodeVersion", "1");

        parms.put("func", "query");
        parms.put("args", "a");

//        parms.put("func", "invoke");
//        parms.put("args0", "a");
//        parms.put("args1", "b");
//        parms.put("args2", "20");

        parms.put("nonce", "1234");
        String sign = DonsonTest.generateWebSign(secret, parms);
        System.out.println(sign);
        Map<String, Object> items = new HashMap<>();
        items.putAll(parms);
        items.put("sign", sign);
        //http://10.8.10.95:8001/chaincode/query
        //http://apibaas.58.com/chaincode/query
        HttpResponse<String> response = Unirest.post(api)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .fields(items)
                .asString();

        System.out.println(response.getBody());

    }
}
