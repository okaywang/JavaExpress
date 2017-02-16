package com.zhaopin;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

/**
 * foo...Created by wgj on 2017/2/12.
 */
@Controller
@RequestMapping("/hello")
@Scope("prototype")
public class HelloController {
    private int i;
    @RequestMapping(method = RequestMethod.GET, value = "test")
    @ResponseBody
    public String test() {
        i ++;
        javax.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return String.valueOf(i);
        //return new Date().toString();
    }
}
