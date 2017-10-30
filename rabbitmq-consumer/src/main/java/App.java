import com.rabbitmq.client.*;
import com.sun.xml.internal.messaging.saaj.soap.Envelope;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2017/7/16.
 */
public class App {
    private final static String QUEUE_NAME = "rabbitMQ.apply1";

    public static void main(String[] args) throws IOException, TimeoutException {
         ConcurrentLinkedQueue<String> _queue = new ConcurrentLinkedQueue<>();
         _queue.add("aaaa");
        _queue.add("bbbbbbbbbbb");
        _queue.add("ccccc");
        _queue.add("dddddd");
         _queue.poll();
        System.out.println(_queue.size());



        List<String> list = new ArrayList<>();
        int limit = 10;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
//        Channel[] channels = new Channel[10];
//        for (int i = 0; i < 10; i++) {
//            channels[i] = connection.createChannel();
//            System.out.println(channels[i].getChannelNumber());
//        }
//

        Channel channel = connection.createChannel();
        Channel channel2 = connection.createChannel();

        channel.basicQos(0, 30, false);
        channel2.basicQos(0, 30, false);
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.addShutdownListener((cause) -> {
            System.out.println("---------------------------my ShutdownListener, at thread: " + Thread.currentThread().getName());
            System.out.println(cause);
        });
//        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        Consumer consumer = new MyConsumer(channel);
        Consumer consumer2 = new MyConsumer(channel2);

        channel.basicConsume(QUEUE_NAME, false, consumer);
        channel2.basicConsume(QUEUE_NAME, false, consumer2);
        System.out.println("main over");
    }
}
