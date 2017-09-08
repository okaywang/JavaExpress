
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.zhaopin.common.mq.MQResponse;
import com.zhaopin.mq.client.MQRpcProducer;
import com.zhaopin.mq.client.message.Message;


/**
 * Created by Administrator on 2017/7/16.
 */
public class App {

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println(111);
         /* MQ服务所在的zookeeper */
        final MQRpcProducer producer = new MQRpcProducer("172.30.37.60:2181");
        /*
          根据不同的消息类型发布消息
          Message 简单即时消息
          DelayMessage 延时消息
          CronMessage 定时消息
         */
        MQResponse response = producer.publish(new Message("topic-123", "message"));
        System.out.println(response);

    }
}
