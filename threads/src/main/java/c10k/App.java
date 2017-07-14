/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package c10k;

/**
 * Created by guojun.wang on 2017/7/14.
 */
public class App {
    public static void main(String[] args) {
        Thread[] threads = new Thread[100000];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new MyWorker(), "thread " + i);

            threads[i].start();
        }

    }

    private static void doWork(){
    }
}
