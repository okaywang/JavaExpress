package token;

import org.apache.ibatis.parsing.GenericTokenParser;

/**
 * Created by Administrator on 2017/8/29.
 */
public class App {
    public static void main(String[] args) {
        GenericTokenParser parser = new GenericTokenParser("${", "}", new MyTokenParser());
        String sql = "select * from blog where name=${name}";

        String sql2 = parser.parse(sql);
        System.out.println(sql2);
    }
}


