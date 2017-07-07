package com.zhaopin;

import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * foo...Created by wgj on 2017/7/4.
 */
public class CaptchaRequestHandler implements HttpRequestHandler {

    public void handleRequest (HttpServletRequest request,
                               HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        //writer.write("response from CaptchaRequestHandler, uri: "+request.getRequestURI());
        writer.write("3 + 5 = ?");
    }
}