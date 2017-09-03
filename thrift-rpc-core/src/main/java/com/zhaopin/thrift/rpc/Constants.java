package com.zhaopin.thrift.rpc;

import java.util.regex.Pattern;

public class Constants {
	// ������������Ĭ�϶˿�
	public static final int DEFAULT_LISTEN_PORT = 15518;
	// ���ݰ����ĳ���
	public static final int MAX_FRAME_SIZE = 64 * 1024 * 1024;
	// ������������ӵ�����
	public static final int MAX_CONNECTION = 1024;
	// ������boss�߳�Ĭ�ϵ�����
	public static final int DEFAULT_BOSS_THREAD_COUNT = 1;
	// IO�߳�Ĭ������
	public static final int DEFAULT_IO_THREADS = Runtime.getRuntime().availableProcessors() * 2 + 1;
	// ������̳߳ض��еĳ���
	public static final int SERVER_THREAD_POOL_QUEUE_LEN = 50000;
	// ����ע���Ĭ�ϰ汾
	public static final String DEFAULT_SERVICE_VERSION = "0.1.0";
	// zkע�����ĸ��ڵ�
	public static final String ZK_ROOT_PATH = "/HSF";
	// Ĭ��zk�з�����������
	public static final String DEF_GROUP = "default";
	// zkע�������з������Ͱ汾�ķָ���
	public static final String ZK_SERVICE_SEP = "#";
	// zkע������·���ķָ��
	public static final String ZK_PATH_SEP = "/";
	// ����Զ�̷���ʱ,Ĭ�ϵĳ�ʱʱ����3s
	public static final long DEF_CALL_WAIT_TIME = 3 * 1000;
	// ����ʹ�õ�ע�����ĵĲ���������
	public static final String PARAM_REGISTRY = "registry";
	// ����������Դ����Ĳ���������
	public static final String PARAM_RETRY = "retry";
	// ���õ�Զ�̷��������
	public static String REMOTE_SERVICE = "remote_service";
	// zookeeper����
	public static final int DEF_ZK_DELAY = 1000;
	// zookeeper���Դ���
	public static final int DEF_ZK_RETRY = 3;
	// zookeeper���ӳ�ʱʱ��
	public static final int DEF_ZK_TIMEOUT = 1000 * 60;

	public static final int POOL_EVICTION = 10 * 60 * 1000;

	public static final String REGISTRY_PROTOCOL = "registry";

	public static final String PARAM_CALL_WAIT = "call_wait";
	// �ͻ��˼�Ⱥ���ԵĲ�������
	public static final String PARAM_CLUSTER_STRATEGY = "cluster_strategy";
	// �ͻ��˼�Ⱥ����failfast
	public static final String CLUSTER_FAILFAST = "failfast";
	// �ͻ��˼�Ⱥ����failover
	public static final String CLUSTER_FAILOVER = "failover";
	// �ͻ��˼�Ⱥ����failsafe
	public static final String CLUSTER_FAILSAFE = "failsafe";
	// �ͻ��˼�Ⱥ����forking
	public static final String CLUSTER_STRATEGY_FORKING = "forking";

	public static final Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*[,]+\\s*");
	// Ĭ�����ԵĴ���
	public static final int DEF_RETRY = 3;

	public static final int EXCEPTION_API_FRM = -1;
	// Ĭ�������ļ�������
	public static final String GLOBAL_CONFIG = "HSF.properties";
	// �첽���ñ�־������
	public static final String FLAG_ASYNC = "async";
	// �־û�ͳ����־�ļ��
	public static long CONSUMER_PERSISTENT_INTERVAL = 30 * 1000;
	// zk��Consumer�ĸ�·��
	public static final String CONSUMER_ROOT = "/HSFConsumer";
	// �ڵ�����ʧ�ܺ�,��ͣ��������ʱ�䳤��
	public static final long TEMPORARY_FAILURE = 10 * 1000;
	// ����ͳ�Ƶļ��
	public static final int STATICS_INTERVAL = 1000 * 60;
	// ����ֱ���ǵ�IP��ַ�Ͷ˿�
	public static final String ENDPOINT = "endpoint";
	// ����汾��������
	public static final String PARAM_VERSION = "version";
	// ����汾��������
	public static final String PARAM_GROUP = "group";
	// processor���ǰ׺
	public static final String ROCESSOR_CLASS_PREFIX = "_";
	// processor��ĺ�׺
	public static final String ROCESSOR_CLASS_SUFFIX = "Processor_";
	// invoker���ǰ׺
	public static final String INVOKER_CLASS_PREFIX = "_";
	// invoker��ĺ�׺
	public static final String INVOKER_CLASS_SUFFIX = "Invoker_";
	// codec���ǰ׺
	public static final String CODEC_CLASS_PREFIX = "_";
	// codec��ĺ�׺
	public static final String CODEC_CLASS_SUFFIX = "Codec_";
	// ������СȨ��
	public static final int MIN_WEIGHT = 1;
	// �������Ȩ��
	public static final int MAX_WEIGHT = 9;
	// �����ѯ
	public static final String RANDOM_ROUND = "random";
	// Ȩ����ѯ
	public static final String WEIGHT_ROUND = "weight";
	// ����token�ķ���ҶȲ���
	public static final String TOKEN_BASED = "token";
	// �û��Զ������
	public static final String USER_DEFINE = "user_def";

}
