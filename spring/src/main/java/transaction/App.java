package transaction;

/**
 * Created by Administrator on 2017/9/17.
 */
public class App {
    public static void main(String[] args) {
        sendMessage("Hr click see");
    }

    private static void sendMessage(String message){
        sendMessageToSearch(message);
        sendMessageToPlat(message);
    }

    private static void sendMessageToSearch(String message){
        System.out.println("search ok");
    }

    private static void sendMessageToPlat(String message){
        System.out.println("plat ok");
    }
}
