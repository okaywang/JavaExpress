package objectstream;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;

/**
 * Created by Administrator on 2017/7/16.
 */
public class App implements Serializable {
    private String name;
    private int userCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        App zpApp = new App();
        zpApp.setName("Zhaopin App");
        zpApp.setUserCount(1200000);
        System.out.println(zpApp);
        byte[] data = serialize(zpApp);
        Object obj = deserialize(data);
        System.out.println(obj);
    }

    private static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object obj = ois.readObject();
        return obj;
    }

    private static byte[] serialize(App obj) throws IOException {
        ByteOutputStream bos = new ByteOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        return bos.getBytes();
    }

    @Override
    public String toString() {
        return "App{" +
                "name='" + name + '\'' +
                ", userCount=" + userCount +
                '}';
    }
}
