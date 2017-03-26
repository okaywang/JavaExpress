package com.zhaopin.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Locale;

/**
 * foo...Created by wgj on 2017/3/26.
 */
public class MyFilter1 implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("start1", Instant.now().toString());
        chain.doFilter(request, response);
        System.out.println("filter1");
    }

    public void destroy() {

    }
}
