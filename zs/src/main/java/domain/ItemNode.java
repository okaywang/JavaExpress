package domain;

/**
 * Created by wangguojun01 on 2018/2/2.
 */
public class ItemNode {
    private StandardDetailItem data;
    private ItemNode next;

    public ItemNode(StandardDetailItem data) {
        this.data = data;
    }

    public StandardDetailItem getData() {
        return data;
    }
//
//    public void setData(StandardDetailItem data) {
//        this.data = data;
//    }

    public ItemNode getNext() {
        return next;
    }

    public void setNext(ItemNode next) {
        this.next = next;
    }
}
