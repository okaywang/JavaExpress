package fastjsondemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Date;

/**
 * Created by guojun.wang on 2017/6/6.
 */
public class App {
    public static void main(String[] args) {
        Person person = new Person();
        person.setId(345);
        person.setName("Jim");
        person.setBirthday(new Date());

        String json = JSON.toJSONString(person);
        System.out.println(json);
        Person p2 = JSON.parseObject(json, Person.class);
        System.out.println(p2);
    }
}
