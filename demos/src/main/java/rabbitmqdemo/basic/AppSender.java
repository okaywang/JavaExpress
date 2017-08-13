package rabbitmqdemo.basic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * foo...Created by wgj on 2017/8/13.
 */
public class AppSender {
    //队列名称
    private final static String QUEUE_NAME = "user-behavior3333";
    public static void main(String[] args) throws IOException, TimeoutException {
        /**
         * 创建连接连接到MabbitMQ
         */
        ConnectionFactory factory = new ConnectionFactory();
        //设置MabbitMQ所在主机ip或者主机名
        factory.setHost("localhost");
        //创建一个连接
        Connection connection = factory.newConnection();
        //创建一个频道
        Channel channel = connection.createChannel();
        //指定一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        for (int i = 0; i < 20000; i++) {
            //发送的消息
            String message = i +  "hello world!" + System.currentTimeMillis();
            //往队列中发出一条消息
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }

        //关闭频道和连接
        channel.close();
        connection.close();
    }
}
