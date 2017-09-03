package com.zhaopin.thrift.rpc.registry;

import org.apache.curator.framework.recipes.cache.TreeCacheEvent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.thrift.rpc.RpcContext;
import com.zhaopin.thrift.rpc.executor.ThreadPoolExecutorFactory;
import com.zhaopin.thrift.rpc.server.ServiceStartup;
import com.zhaopin.thrift.rpc.util.IPResolver;

class ProcessCommand {

	public static final Logger LOGGER = LoggerFactory.getLogger(ProcessCommand.class);

	public void execute(TreeCacheEvent event, DefaultRegistry registry) {
		String path = event.getData().getPath();
		byte[] value = event.getData().getData();
		if (value != null) {
			LOGGER.info("zookeeper node add event {}, {}", path, new String(value));
			JSONObject json = JSON.parseObject(new String(value));
			String ip = IPResolver.getIP();
			if (json != null && ip.equals(json.getString("ip")) && json.getIntValue("port") == RpcContext.listenPort) {
				if ("stop".equals(json.getString("cmd"))) {
					// Í£Ö¹zookeeper¼àÌý
					registry.stopRegistry();
					// Í£Ö¹Ïß³Ì³Ø
					ThreadPoolExecutorFactory.getThreadPoolExecutor().shutdown();
					// Í£Ö¹·þÎñ
					ServiceStartup.startup.stopService();
				} else if ("print".equals(json.getString("cmd"))) {
					LOGGER.warn("the service list is {}", JSON.toJSONString(registry.getServiceList()));
				} else if ("refresh".equals(json.getString("cmd"))) {
					registry.refresh();
				}
			}
		}
	}

}
