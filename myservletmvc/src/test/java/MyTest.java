import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.zhaopin.PersonModel;

import java.io.IOException;

/**
 * foo...Created by wgj on 2017/3/20.
 */
public class MyTest {
    static {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public static void main(String[] args) throws UnirestException {
        PersonModel model = new PersonModel();
        model.setName("wgj");
        model.setAge(32);



        HttpResponse<String> response = Unirest.post("http://localhost:8090/hello/add.do")
                .header("content-type", "application/json")
                .body(model)
                .asString();
        System.out.println(response.getBody());
    }
}
