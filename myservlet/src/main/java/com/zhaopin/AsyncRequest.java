package com.zhaopin;

import javax.servlet.AsyncContext;
import java.awt.*;
import java.io.PrintWriter;

/**
 * Created by wangguojun01 on 2017/12/1.
 */
public class AsyncRequest implements Runnable {
    private AsyncContext ctx;

    public AsyncRequest(AsyncContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run() {
        try {
            // 模拟长时间的处理
            Thread.sleep(10000);
            PrintWriter out = ctx.getResponse().getWriter();
            out.println("久等了...XD");
            System.out.println("AsyncRequest thread:" + Thread.currentThread().getId());
            // 这边才真正送出回应
            ctx.complete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
