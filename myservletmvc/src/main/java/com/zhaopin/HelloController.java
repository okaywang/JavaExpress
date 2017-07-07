package com.zhaopin;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * foo...Created by wgj on 2017/2/12.
 */
@Controller
@RequestMapping("/hello")
@Scope("prototype")
public class HelloController {

    @ModelAttribute
    public void preRun() {
        System.out.println("Test Pre-Run");
    }

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

    @RequestMapping(method = RequestMethod.GET, value = "test2", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public PersonModel test2(String name) throws Exception {
        i++;
        javax.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //return String.valueOf(i);
        PersonModel person = new PersonModel();
        person.setAge(30);
        person.setName("wgj");
        return person;
    }

    @RequestMapping(method = RequestMethod.GET, value = "testmodel", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public PersonModel testModel(String name) throws Exception {
        PersonModel pm = new PersonModel();
        pm.setAge(18);
        pm.setName("lucy");
        return pm;
    }


    @RequestMapping(method = RequestMethod.GET, value = "gray1", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String gray1(HttpServletRequest request) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("host: " + System.getenv("COMPUTERNAME"));
        sb.append("<br />");
        sb.append("url:" + request.getRequestURL().toString() + "?" + request.getQueryString());
        return sb.toString();
    }

    @RequestMapping(method = RequestMethod.POST, value = "gray2", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String gray2(HttpServletRequest request, HttpEntity<String> httpEntity) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("host: " + System.getenv("COMPUTERNAME"));
        sb.append("<br />");
        sb.append("url:" + request.getRequestURL().toString() + "?" + request.getQueryString());
        sb.append("<br />");
        sb.append("rootid:" + request.getHeader("rootid"));
        sb.append("<br />");
        sb.append("body:" + httpEntity.getBody());
        return sb.toString();
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
