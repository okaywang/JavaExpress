/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.zp.consumer;

import com.zp.CompanyService;
import com.zp.JobDto;
import com.zp.JobException;
import com.zp.JobService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by guojun.wang on 2017/7/10.
 */
public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"application.xml"});
        context.start();
        JobService demoService = (JobService) context.getBean("jobService"); // 获取bean

        List<JobDto> search = demoService.search(1000, null);
        System.out.println(search);

        try {

            JobDto jobInfo = demoService.getJobInfo(3);
            System.out.println(jobInfo);
        }
        catch (JobException ex){
            ex.printStackTrace();
        }
        CompanyService companyService = (CompanyService) context.getBean("companyService");
        for (int i = 0; i < 1000; i++) {
            try {
                JobDto dto = demoService.getJobInfo(i);
                int count = demoService.getJobCount();
                String cname = companyService.getCompanyName(i);
                System.out.print(dto);
                System.out.println(count);
                System.out.println(cname);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
