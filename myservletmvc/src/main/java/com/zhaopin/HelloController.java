package com.zhaopin;

import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * foo...Created by wgj on 2017/2/12.
 */
@Controller
@RequestMapping("/hello")
@Scope("prototype")
public class HelloController {
    private int i;
    //application/json;charset=UTF-8
    @RequestMapping(method = RequestMethod.GET, value = "test", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String test(String name) throws Exception {



        i++;
        javax.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //return String.valueOf(i);
        return "your name is " + request.getParameter("name");
    }


    @RequestMapping(method = RequestMethod.POST, value = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addPerson(@RequestBody PersonModel model, BindingResult result) {
        System.out.println("234");
        return "vv";
    }

    @RequestMapping(method = RequestMethod.POST, value = "location")
    @ResponseBody
    public String uploadLocation(HttpServletRequest request, double lat, double lng) {
        String strLat = request.getParameter("lat");
        return strLat + lat + lng;
    }
}
