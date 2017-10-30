import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.ChannelN;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by guojun.wang on 2017/10/20.
 */
public class AppChannel {
    private final static String QUEUE_NAME = "rabbitMQ.apply1";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();



        Channel channelA = connection.createChannel();
        GetResponse responseA1 = channelA.basicGet(QUEUE_NAME, false);
        GetResponse responseA2 = channelA.basicGet(QUEUE_NAME, false);
        GetResponse responseA3 = channelA.basicGet(QUEUE_NAME, false);

        Channel channelB = connection.createChannel();
        GetResponse responseB1 = channelB.basicGet(QUEUE_NAME, false);
        GetResponse responseB2 = channelB.basicGet(QUEUE_NAME, false);
        GetResponse responseB3 = channelB.basicGet(QUEUE_NAME, false);
        channelB.basicAck(1,false);
        channelB.basicAck(1,false);
        channelB.basicAck(1,false);

        System.out.println();
        System.out.println(new String(responseA1.getBody()));
        System.out.println(new String(responseB2.getBody()));

    }
}
