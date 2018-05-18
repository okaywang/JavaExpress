import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by wangguojun01 on 2018/5/3.
 */
public class App9 {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        System.out.println(1111);
        String json = "{\"name\":\"wgj\",\"age\":10,\"school\":{\"id\":100,\"name\":\"peiking\"}}";
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(json);
        Object age = jsonObject.get("age");
        System.out.println(age);

        Object property = PropertyUtils.getProperty(jsonObject, "school.name");
        System.out.println(property);

        PropertyUtils.setProperty(jsonObject, "school.name","tinghua");
        System.out.println(PropertyUtils.getProperty(jsonObject, "school.name"));

        System.out.println(JSON.toJSONString(jsonObject));
    }
}
