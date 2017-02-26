package di.ctxxml.hello;

/**
 * foo...Created by wgj on 2017/2/12.
 */
public class MessagePrinter {

    final private MessageService service;

    public MessagePrinter(MessageService service) {
        this.service = service;
    }

    public void printMessage() {
        System.out.println(this.service.getMessage());
    }
}
