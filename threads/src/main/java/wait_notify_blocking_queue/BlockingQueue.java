package wait_notify_blocking_queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * foo...Created by wgj on 2017/3/26.
 * http://stackoverflow.com/questions/2536692/a-simple-scenario-using-wait-and-notify-in-java
 */
public class BlockingQueue<T> {
    private Queue<T> queue = new LinkedList<T>();

    private int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(T element) throws InterruptedException {
        if (queue.size() == capacity){
            wait();
        }
        queue.add(element);
        notify();
    }

    public synchronized T take() throws InterruptedException {
        if (queue.isEmpty()){
            wait();
        }
        T item = queue.remove();
        notify();
        return item;
    }
}