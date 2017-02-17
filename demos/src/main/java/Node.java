import org.omg.CORBA.NO_IMPLEMENT;

/**
 * Created by guojun.wang on 2017/2/17.
 */
public class Node {
    private Object value;
    private Node next;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public static void main(String[] args) {
        Node yf1 = new Node();
        yf1.setValue("George");

        Node yf2 = new Node();
        yf2.setValue("lx");

        Node yf3 = new Node();
        yf3.setValue("xh");

        Node yf4 = new Node();
        yf4.setValue("wgj");

        yf1.setNext(yf2);
        yf2.setNext(yf3);
        yf3.setNext(yf4);

        showNode(yf1);

        Node revertedNode = revert(yf1);

        showNode(revertedNode);
    }

    private static void showNode(Node node) {
        Node current = node;
        while (current != null) {
            System.out.print(current.getValue());
            current = current.getNext();
            if (current != null) {
                System.out.print("->");
            }
        }
        System.out.println();
    }

    private static Node revert(Node node) {
        if (node == null) {
            return null;
        }
        Node previous = null;
        while (true) {
            Node next = node.getNext();// after node's getNext called, node can invoke it's setNext method
            node.setNext(previous);
            previous = node;
            node = next;
            if (next == null) {
                return previous;
            }
        }
    }
}
