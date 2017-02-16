package com.zhaopin;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * foo...Created by wgj on 2017/2/12.
 */
@javax.servlet.annotation.WebServlet(name = "SecondServlet")
public class SecondServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.getSession().setAttribute("server",this.getClass().getName());
        response.sendRedirect("second.jsp");
    }
}
