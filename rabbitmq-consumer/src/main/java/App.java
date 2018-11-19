import com.rabbitmq.client.*;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2017/7/16.
 */
public class App {
    private final static String QUEUE_NAME = "rabbitMQ.apply1";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new MyConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
