package fastjsondemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * idea most useful shortcuts
 * <p>
 * --> https://stackoverflow.com/questions/294167/what-are-the-most-useful-intellij-idea-keyboard-shortcuts
 * <p>
 * Created by guojun.wang on 2017/6/6.
 */
public class App {


    public static final int age1_adfa1 = 2;
    public static final String bge222 = "33";
    public static final int AAAA = 2;
    public static final int CCC = 2;
    private String xx;

    public static void main(String[] args) {
        Person person = new Person();
        person.setId(345);
        person.setName("Jim");
        person.setBirthday(new Date());

        String json = JSON.toJSONString(person);
        System.out.println(json);
        Person p2 = JSON.parseObject(json, Person.class);
        System.out.println(p2);


        regexTest();

        StringBuilder sb = new StringBuilder();
        sb.append("aaaa");
        sb.toString();


    }

    /**
     * this is a test
     */
    static void regexTest() {
        String str = "JM234234R3341";
        char[] data = new char[10];
        str.getChars(1, 4, data, 3);
//        String str2 = str.replaceAll("\\d", "x");
//        System.out.println(str2);

        String str3 = Pattern.compile("\\D").matcher(str).replaceAll("_");
        System.out.println(str3);
    }
}
