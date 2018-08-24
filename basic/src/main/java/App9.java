import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Created by wangguojun01 on 2018/5/3.
 */
public class App9 {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        System.out.println(Instant.now().toString());
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneId.systemDefault()).format(Instant.now()));
        System.out.println("abc.go".split("\\.")[0]);

        String str = "[]";
        String[] strings = JSON.parseArray(str).toArray(new String[0]);
        System.out.println(strings);


        System.out.println(1111);
        String json = "{\"name\":\"wgj\",\"age\":10,\"school\":{\"id\":100,\"name\":\"peiking\"}}";
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(json);
        Object age = jsonObject.get("age");
        System.out.println(age);

        Object property = PropertyUtils.getProperty(jsonObject, "school.name");
        System.out.println(property);

        PropertyUtils.setProperty(jsonObject, "school.name", "tinghua");
        System.out.println(PropertyUtils.getProperty(jsonObject, "school.name"));

        System.out.println(JSON.toJSONString(jsonObject));
    }
}
