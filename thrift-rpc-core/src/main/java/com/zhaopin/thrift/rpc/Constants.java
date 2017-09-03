package com.zhaopin.thrift.rpc;

import java.util.regex.Pattern;

public class Constants {
	// 服务器监听的默认端口
	public static final int DEFAULT_LISTEN_PORT = 15518;
	// 数据包最大的长度
	public static final int MAX_FRAME_SIZE = 64 * 1024 * 1024;
	// 服务器最多连接的数量
	public static final int MAX_CONNECTION = 1024;
	// 服务器boss线程默认的数量
	public static final int DEFAULT_BOSS_THREAD_COUNT = 1;
	// IO线程默认数量
	public static final int DEFAULT_IO_THREADS = Runtime.getRuntime().availableProcessors() * 2 + 1;
	// 服务端线程池队列的长度
	public static final int SERVER_THREAD_POOL_QUEUE_LEN = 50000;
	// 服务注册的默认版本
	public static final String DEFAULT_SERVICE_VERSION = "0.1.0";
	// zk注册中心根节点
	public static final String ZK_ROOT_PATH = "/HSF";
	// 默认zk中服务分组的名称
	public static final String DEF_GROUP = "default";
	// zk注册中心中服务名和版本的分隔符
	public static final String ZK_SERVICE_SEP = "#";
	// zk注册中心路径的分割符
	public static final String ZK_PATH_SEP = "/";
	// 调用远程服务时,默认的超时时间是3s
	public static final long DEF_CALL_WAIT_TIME = 3 * 1000;
	// 服务使用的注册中心的参数的名称
	public static final String PARAM_REGISTRY = "registry";
	// 服务调用重试次数的参数的名称
	public static final String PARAM_RETRY = "retry";
	// 调用的远程服务的名称
	public static String REMOTE_SERVICE = "remote_service";
	// zookeeper配置
	public static final int DEF_ZK_DELAY = 1000;
	// zookeeper重试次数
	public static final int DEF_ZK_RETRY = 3;
	// zookeeper连接超时时间
	public static final int DEF_ZK_TIMEOUT = 1000 * 60;

	public static final int POOL_EVICTION = 10 * 60 * 1000;

	public static final String REGISTRY_PROTOCOL = "registry";

	public static final String PARAM_CALL_WAIT = "call_wait";
	// 客户端集群策略的参数名称
	public static final String PARAM_CLUSTER_STRATEGY = "cluster_strategy";
	// 客户端集群策略failfast
	public static final String CLUSTER_FAILFAST = "failfast";
	// 客户端集群策略failover
	public static final String CLUSTER_FAILOVER = "failover";
	// 客户端集群策略failsafe
	public static final String CLUSTER_FAILSAFE = "failsafe";
	// 客户端集群策略forking
	public static final String CLUSTER_STRATEGY_FORKING = "forking";

	public static final Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*[,]+\\s*");
	// 默认重试的次数
	public static final int DEF_RETRY = 3;

	public static final int EXCEPTION_API_FRM = -1;
	// 默认配置文件的名称
	public static final String GLOBAL_CONFIG = "HSF.properties";
	// 异步调用标志的名称
	public static final String FLAG_ASYNC = "async";
	// 持久化统计日志的间隔
	public static long CONSUMER_PERSISTENT_INTERVAL = 30 * 1000;
	// zk中Consumer的根路径
	public static final String CONSUMER_ROOT = "/HSFConsumer";
	// 节点连接失败后,暂停对外服务的时间长度
	public static final long TEMPORARY_FAILURE = 10 * 1000;
	// 服务统计的间隔
	public static final int STATICS_INTERVAL = 1000 * 60;
	// 服务直连是的IP地址和端口
	public static final String ENDPOINT = "endpoint";
	// 服务版本参数名称
	public static final String PARAM_VERSION = "version";
	// 服务版本参数名称
	public static final String PARAM_GROUP = "group";
	// processor类的前缀
	public static final String ROCESSOR_CLASS_PREFIX = "_";
	// processor类的后缀
	public static final String ROCESSOR_CLASS_SUFFIX = "Processor_";
	// invoker类的前缀
	public static final String INVOKER_CLASS_PREFIX = "_";
	// invoker类的后缀
	public static final String INVOKER_CLASS_SUFFIX = "Invoker_";
	// codec类的前缀
	public static final String CODEC_CLASS_PREFIX = "_";
	// codec类的后缀
	public static final String CODEC_CLASS_SUFFIX = "Codec_";
	// 服务最小权重
	public static final int MIN_WEIGHT = 1;
	// 服务最大权重
	public static final int MAX_WEIGHT = 9;
	// 随机轮询
	public static final String RANDOM_ROUND = "random";
	// 权重轮询
	public static final String WEIGHT_ROUND = "weight";
	// 基于token的服务灰度策略
	public static final String TOKEN_BASED = "token";
	// 用户自定义策略
	public static final String USER_DEFINE = "user_def";

}
