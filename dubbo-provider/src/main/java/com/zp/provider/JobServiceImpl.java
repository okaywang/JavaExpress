/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.zp.provider;

import com.zp.JobDto;
import com.zp.JobService;

/**
 * Created by guojun.wang on 2017/7/10.
 */
public class JobServiceImpl implements JobService {
    @Override
    public JobDto getJobInfo(int id) {
        System.out.println("getJobInfo start ...");
        JobDto jobDto = new JobDto();
        jobDto.setId(id);
        jobDto.setJobTitle("this is from server(dubbo)");
        System.out.println("getJobInfo end");
        return jobDto;
    }

    @Override
    public int getJobCount() {
        return 100;
    }
}
