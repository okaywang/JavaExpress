package com.zhaopin;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * foo...Created by wgj on 2017/2/12.
 */
@javax.servlet.annotation.WebServlet(name = "FirstServlet")
public class FirstServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        pw.write("<h1> hello first servlet by wgj!</h1>");
    }
}
