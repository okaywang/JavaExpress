package fastjsondemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * foo...Created by wgj on 2017/8/20.
 */
public class ZpFoo {

    public void test1(Person person) {
        String json = JSON.toJSONString(person);
        System.out.println(json);
        Person p2 = JSON.parseObject(json, Person.class);
        System.out.println(p2);
    }

    public void test2(Person person) {
        String json = JSON.toJSONString(person, SerializerFeature.UseISO8601DateFormat);
        System.out.println(json);
        Person p2 = JSON.parseObject(json, Person.class);
        System.out.println(p2);
    }

    public void test3(Person person) {
        JSON.DEFFAULT_DATE_FORMAT = "yy-MM/dd";
        String json = JSON.toJSONString(person, SerializerFeature.WriteDateUseDateFormat);
        System.out.println(json);
        Person p2 = JSON.parseObject(json, Person.class);
        System.out.println(p2);
    }

}
