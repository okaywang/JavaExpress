package common;

import org.apache.commons.lang3.StringUtils;

import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Calendar;

/**
 * Created by wangguojun01 on 2018/2/1.
 */
public class AStockHelper {
    public static DecimalFormat DecimalFormat = new DecimalFormat("0.00");

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

    public static LocalDate previousDay() {
        int i = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if (i == 7) {
            return LocalDate.now().minusDays(2);
        }
        if (i == 1) {
            return LocalDate.now().minusDays(3);
        }
        return LocalDate.now().minusDays(1);
    }
}
