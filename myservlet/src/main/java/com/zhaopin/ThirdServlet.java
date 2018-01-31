package com.zhaopin;

import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * foo...Created by wgj on 2017/2/12.
 */
@javax.servlet.annotation.WebServlet(name = "FirstServlet")
public class ThirdServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setContentType("text/html");
        String state = request.getParameter("state");
        System.out.println(state);
        PrintWriter pw = response.getWriter();
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/second");
        requestDispatcher.forward(request,response);
    }
}
