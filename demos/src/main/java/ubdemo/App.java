/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ubdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by guojun.wang on 2017/9/6.
 */
public class App {
    private static int count = 0;
    private static Random random = new Random();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(20);

        List<Future<Boolean>> futureList = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            Future<Boolean> future = es.submit(() -> {
                return userLogin(random.nextInt(1000));
            });
            futureList.add(future);
        }

        for (int i = 0; i < futureList.size(); i++) {
            Boolean result = futureList.get(i).get();
            //System.out.println(result);
        }
        //es.shutdown();
        //System.out.println("over");
        System.out.println(count);
    }

    private static boolean userLogin(long userId) {
        count++;
        //System.out.println(userId + " login");
        return true;
    }

    private UserBehaviorDto[] getFromRedis(int maxCount) {
        return null;
    }
}
