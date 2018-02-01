package common;

import org.apache.commons.lang3.StringUtils;

import java.security.InvalidParameterException;

/**
 * Created by wangguojun01 on 2018/2/1.
 */
public class AStockHelper {
    public static String prefix(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new IllegalArgumentException("code can not be empty");
        }
        if (code.length() != 6) {
            throw new InvalidParameterException("invalid code " + code);
        }
        String prefix = code.startsWith("6") ? "sh" : "sz";
        return prefix + code;
    }
}
