

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * foo...Created by wgj on 2017/4/25.
 */
public class App {
    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println(args.length);
        System.out.println(" -classpath " + System.getProperty("java.class.path"));


        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = runtimeMxBean.getInputArguments();
        arguments.forEach(System.out::println);


        System.out.println("this is gradle test project");
        printResource("pg.properties");
        printResource("redis.properties");
    }

    private static void printResource(String filename) throws URISyntaxException, IOException {
        InputStream inputStream = App.class.getClassLoader().getResourceAsStream(filename);
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        System.out.println(result);
    }
}
