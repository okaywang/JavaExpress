/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package concurrenthm;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by guojun.wang on 2017/9/1.
 */
public class AppBasic {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<String, String> maps = new ConcurrentHashMap<>();
        //替换成这个那么线程不安全
        //HashMap<String, String> maps = new HashMap<>();

        Thread[] threads = new Thread[1];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        maps.put("gt_" + Thread.currentThread().getName() + "_" + j, UUID.randomUUID().toString());
                    }
                }
            });
            threads[i].setName("th" + i);
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }

        System.out.println(maps.size());

    }
}
