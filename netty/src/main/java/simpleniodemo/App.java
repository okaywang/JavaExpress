package simpleniodemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/9/19.
 */
public class App {
    public static final int SIZE = 1024;
    public static final String PATH = "D:\\Projects\\JavaExpress\\netty\\content\\hadoop.txt";

    public static void main(String[] args) {
        //Executors.newCachedThreadPool()

        try {
            FileChannel fc = new FileInputStream(PATH).getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(SIZE);
            fc.read(buffer);
            //重值ByteBuffer中的数组
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
