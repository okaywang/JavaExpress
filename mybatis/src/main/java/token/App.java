package token;

import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.parsing.GenericTokenParser;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/29.
 */
public class App {
    private static String[] apps = new String[10];
    public static void main(String[] args) {
        //LruCache myCache = new LruCache(5);

        LinkedHashMap<String, String> km = new LinkedHashMap<String, String>(3, 0.5f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > 3;
            }
        };
        km.put("a1", "22222");
        km.put("a3", "22222");
        km.put("a2", "22222");
        km.put("a9", "22222");
        km.put("a5", "22222");

        GenericTokenParser parser = new GenericTokenParser("${", "}", new MyTokenParser());
        String sql = "select * from blog where name=${name}";

        String sql2 = parser.parse(sql);
        System.out.println(sql2);
    }
}


