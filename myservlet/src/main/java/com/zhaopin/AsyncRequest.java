package com.zhaopin;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wangguojun01 on 2017/12/1.
 */
public class AsyncRequest implements Runnable {
    private AsyncContext ctx;

    public AsyncRequest(AsyncContext ctx) {
        this.ctx = ctx;
        this.ctx.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                event.getSuppliedResponse().getWriter().print("this is the result !!!");
                System.out.println("onComplete");
            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {

            }

            @Override
            public void onError(AsyncEvent event) throws IOException {

            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {

            }
        });
    }

    @Override
    public void run() {
        try {
            // 模拟长时间的处理
            Thread.sleep(5000);
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
