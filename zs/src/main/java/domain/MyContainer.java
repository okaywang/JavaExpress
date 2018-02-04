package domain;

/**
 * Created by wangguojun01 on 2018/2/2.
 */
public class MyContainer {
    private static final int max_count = 241;
    private ItemNode node;

    public MyContainer(ItemNode node) {
        this.node = node;
    }

    public void addNode(ItemNode node) {
        this.node.setNext(node);
    }


}
