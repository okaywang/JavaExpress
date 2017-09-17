import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by wgj on 2017/1/21.
 */
public class App {
    public static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Scanner s = new Scanner(System.in);
                        String line = s.nextLine();
                        System.out.println(line);
                        byte[] bytes = Files.readAllBytes(Paths.get(App.class.getClassLoader().getResource("MafeiDebug.class").toURI()));
                        String result =  JavaClassExecutor.execute(bytes);
                        System.err.println(result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        do {
            count++;
            System.out.println(count);
            Thread.sleep(3000);
        } while (true);
    }
}
