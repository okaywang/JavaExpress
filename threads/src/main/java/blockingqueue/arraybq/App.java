/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package blockingqueue.arraybq;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by guojun.wang on 2017/9/1.
 */
public class App {

    private static ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(3);

    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Condition condition1 = lock1.newCondition();
        Condition condition2 = lock2.newCondition();

        //lock1.lock();
        condition1.signal();



        Thread consumer = new Thread(() -> {
            while (true) {
                String item = null;
                try {
                    item = arrayBlockingQueue.take();
                    Thread.sleep(2000);
                    System.out.println("take " + item);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        consumer.start();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            arrayBlockingQueue.put("e" + i);
            System.out.println("put " + i);
        }

        System.out.println(11111);
    }
}
