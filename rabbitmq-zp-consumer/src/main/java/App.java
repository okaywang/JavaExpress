
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.zhaopin.common.mq.MQResponse;
import com.zhaopin.mq.client.MQRpcProducer;
import com.zhaopin.mq.client.message.Message;
import com.zhaopin.common.mq.MQConsumeCallBack;
import com.zhaopin.common.mq.MQConsumer;
import com.zhaopin.common.mq.MQPayload;
import com.zhaopin.mq.client.MQRpcConsumer;


/**
 * Created by Administrator on 2017/7/16.
 */
public class App {
    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println(111);
           /* MQ服务所在的zookeeper */
        MQConsumer mqConsumer = new MQRpcConsumer("172.17.5.81:2181");

        mqConsumer.consume("plat.profile.snap1", null, new MQConsumeCallBack() {
            @Override
            public void handleMessage(MQPayload payload) throws Exception {
                System.out.println(Thread.currentThread().getName() + " :" + payload);
            }
        });


    }
}
