package com.zhaopin;

/**
 * Created by Administrator on 2017/9/3.
 */
@ThriftInterface
public interface JobService {
    String getJobName(String jobNumber);
}
