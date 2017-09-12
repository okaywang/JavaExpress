/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by guojun.wang on 2017/9/12.
 */
public class RunnerConsumerThreadFactory implements ThreadFactory {
    private static final ThreadGroup RUNNER_CONSUMER_THREAD_GROUP = new ThreadGroup("欣欣向荣" + "-ThreadGroup");

    private final AtomicInteger threadNum = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(RUNNER_CONSUMER_THREAD_GROUP, r, "consumer" + "-Thread-" + threadNum.incrementAndGet());
    }
}