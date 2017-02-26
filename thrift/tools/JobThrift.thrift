namespace java com.zhaopin.thrift

struct JobDto {
	1:i32 id;
	2:string jobName;
}

service JobService {
	JobDto getJob(1:i32 jobId)
}