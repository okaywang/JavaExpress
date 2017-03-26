package com.zhaopin.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

/**
 * foo...Created by wgj on 2017/3/26.
 */
public class MyFilter2 implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("start2", Instant.now().toString());
        chain.doFilter(request, response);
        System.out.println("filter2");

    }

    public void destroy() {

    }
}
