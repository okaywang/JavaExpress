package token;

import org.apache.ibatis.parsing.TokenHandler;

/**
 * Created by Administrator on 2017/8/29.
 */
public class MyTokenParser implements TokenHandler {
    @Override
    public String handleToken(String content) {
        return "GSL";
    }
}
