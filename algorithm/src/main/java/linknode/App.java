package linknode;

/**
 * Created by guojun.wang on 2017/11/10.
 */
public class App {
    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node4);

        printNode(node1);
        Node reversed = reverse2(node1);
        printNode(reversed);
    }

    private static Node reverse2(Node node) {
        Node next = node.getNext();
        if (next == null) {
            return node;
        }
        node.setNext(null);
        Node reversedNext = reverse2(next);
        reversedNext.appendLast(node);
        return reversedNext;
    }

//    private static Node reverse(Node node) {
//        if (node.getNext() == null) {
//            return node;
//        }
//        Node next = node.getNext();
//        node.setNext(null);
//        Node nextNext = node.getNext();
//        next.setNext(node);
//
//        Node aa = reverse(nextNext);
//        aa.setNext(node);
//        return aa;
//    }

    private static void printNode(Node node) {
        while (node != null) {
            System.out.print(node.getValue());
            System.out.print(",");
            node = node.getNext();
        }
        System.out.println();
    }
}

class Node {
    public Node(int value) {
        this.value = value;
    }

    private int value;
    private Node next;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void appendLast(Node node) {
        Node last = this;
        while (last.getNext() != null) {
            last = last.getNext();
        }
        last.setNext(node);
    }
}
