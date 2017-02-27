package com.zhaopin.service;

import com.zhaopin.thrift.JobDto;
import com.zhaopin.thrift.JobService;
import org.apache.thrift.TException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

/**
 * foo...Created by wgj on 2017/2/26.
 */
public class JobServiceImpl implements JobService.Iface {
    @Override
    public JobDto getJob(int jobId) throws TException {
        JobDto dto = new JobDto();
        dto.setId(1200000);
        dto.setJobName("地址勘探" + LocalTime.now().toString());
        return dto;
    }
}
