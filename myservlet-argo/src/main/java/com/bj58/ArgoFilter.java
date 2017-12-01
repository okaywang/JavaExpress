package com.bj58;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * Created by wangguojun01 on 2017/11/29.
 */

@WebFilter(urlPatterns = {"/*"},
        dispatcherTypes = {DispatcherType.REQUEST},
        initParams = {@WebInitParam(name = "encoding", value = "UTF-8")}
)
public class ArgoFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.getWriter().write(String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void destroy() {

    }
}