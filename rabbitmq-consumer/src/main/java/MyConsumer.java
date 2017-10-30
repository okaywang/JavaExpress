import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.impl.ChannelN;

import java.io.IOException;

/**
 * Created by Administrator on 2017/7/16.
 */
public class MyConsumer extends DefaultConsumer {
    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public MyConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//        String message = new String(body, "UTF-8");
//        System.out.println(" [x] Received '" + message + "'");
//        ChannelN channelN = (ChannelN) getChannel();
//        System.out.println(channelN.isOpen());
////        channelN.basicAck(100, false);
//        System.out.println(channelN.isOpen());
//        System.out.println(channelN.isOpen());
//        System.out.println(channelN.isOpen());
//        System.out.println(channelN.isOpen());
//        System.out.println(channelN.isOpen());
//        System.out.println(channelN.isOpen());
//        System.out.println(channelN.isOpen());
//        System.out.println(channelN.isOpen());
//        System.out.println(channelN.isOpen());
//        System.out.println(channelN.isOpen());
//        System.out.println(channelN.isOpen());
//        System.out.println(channelN.isOpen());
//        System.out.println(channelN.isOpen());
////        channelN.basicAck(100, false);
//        System.out.println(channelN.isOpen());
////        channelN.basicAck(100, false);
//        System.out.println(channelN.isOpen());
////        channelN.basicAck(envelope.getDeliveryTag(), false);

        String tname = Thread.currentThread().getName();
        System.out.println(tname + " processing msg tag:" + envelope.getDeliveryTag());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(tname + " processing msg tag:" + envelope.getDeliveryTag() + "over");
    }
}
