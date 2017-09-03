package com.zhaopin.thrift.rpc.registry;

import java.util.List;
import java.util.Map;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.thrift.rpc.common.ServerNode;
import com.zhaopin.thrift.rpc.server.ServerNodeInfo;

/**
 * 注册中心服务
 *
 */
public interface RegistryService {

	public static final Logger LOGGER = LoggerFactory.getLogger(RegistryService.class);

	/**
	 * 开始监听zookeeper
	 */
	public void listen();

	/**
	 * 刷新缓存
	 */
	public void refresh();

	/**
	 * 根据token获取服务的灰度信息
	 * 
	 * @param token
	 *            token值
	 * @param group
	 *            服务的分组
	 * @param service
	 *            服务名称
	 * @param version
	 *            服务的版本
	 * @return
	 */
	public GrayPubService getGrayPubService(String token, String group, String service, String version);

	/**
	 * 
	 * @param group
	 *            服务分组
	 * @param service
	 *            服务#版本
	 * @return
	 */
	public List<ServerNode> loadService(String group, String service, String version);

	/**
	 * 注册服务
	 * 
	 * @param path
	 *            服务的路径
	 * @param value
	 *            注册的节点的值
	 * @return
	 */
	public boolean registerService(String path, ServerNodeInfo serverNodeInfo);

	/**
	 * 注册http接口
	 * 
	 * @param path
	 * @param info
	 * @return
	 */
	public boolean registerHttp(String path, String info);

	/**
	 * 获取服务列表
	 * 
	 * @return
	 */
	public Map<String, List<ServerNode>> getServiceList();

	/**
	 * 注册服务的消费者
	 * 
	 * @param group
	 * @param service
	 * @param version
	 */
	public void registerConsumer(String group, String service, String version);

	/**
	 * 停止监听zookeeper
	 */
	public void stopRegistry();

}
