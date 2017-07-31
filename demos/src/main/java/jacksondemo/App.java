package jacksondemo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fastjsondemo.Person;

import java.io.IOException;
import java.util.Date;

/**
 * foo...Created by wgj on 2017/7/31.
 */
public class App {
    public static void main(String[] args) throws IOException {
        String jsonstr = "{\"msg\":{\"head\":{\"version\":\"1.0\",\"bizcode\":\"1006\",\"senddate\":\"20140827\",\"sendtime\":\"110325\",\"seqid\":\"1\"},\"body\":{\"datalist\":\"wahaha\",\"rstcode\":\"000000\",\"rstmsg\":\"成功\"}}}";
        ObjectMapper mapper = new ObjectMapper();

        //允许出现特殊字符和转义符

        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ;
        JsonNode root = mapper.readTree(jsonstr);
        //path与get作用相同,但是当找不到该节点的时候,返回missing node而不是Null.
        JsonNode msg = root.path("msg");
        JsonNode head = msg.path("head");
        JsonNode body = msg.path("body");

        String bizcode = head.path("bizcode").asText();
        String datalist = body.path("datalist").asText();

        System.err.println(bizcode);
        System.err.println(datalist);

        System.err.println(root.path("msg").path("body").path("datalist").asText());
    }

    private static void test() throws IOException {
        Person person = new Person();
        person.setId(345);
        person.setName("Jim");
        person.setBirthday(new Date());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(person);
        System.out.println(json);

        Person p2 = mapper.readValue(json, Person.class);
        System.out.println(p2);
    }
}
