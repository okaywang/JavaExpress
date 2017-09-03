package demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by guojun.wang on 2017/4/24.
 */
public class App {
    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println("gradle test");
        printResourceText("appconfig.properties");
        printResourceText("pg.properties");
    }

    private static void printResourceText(String filename) throws URISyntaxException, IOException {
        URI uri = App.class.getClassLoader().getResource(filename).toURI();
        byte[] bytes = Files.readAllBytes(Paths.get(uri));
        System.out.println(new String(bytes));
    }
}
