package com.zhaopin.thrift.rpc.registry;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.framework.api.CreateBuilder;
import org.apache.curator.framework.api.ExistsBuilder;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.data.Stat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zhaopin.thrift.rpc.Constants;
import com.zhaopin.thrift.rpc.RpcContext;
import com.zhaopin.thrift.rpc.common.ServerNode;
import com.zhaopin.thrift.rpc.exception.RpcException;
import com.zhaopin.thrift.rpc.server.ServerNodeInfo;
import com.zhaopin.thrift.rpc.util.IPResolver;

public class DefaultRegistry implements RegistryService {
	// ���ص�IP��ַ
	private final String localIp = IPResolver.getIP();
	// ע���ʽ
	private final String fmt = "thrift://%s?group=%s&major=%s&minor=%s";
	// �����б�Ļ������
	private final Map<String, List<ServerNode>> serviceList = new HashMap<String, List<ServerNode>>();
	// zookeeper�ĵ�ַ
	private final String zkAddr;
	// zookeeper�ͻ���
	protected CuratorFramework zkClient;
	// ����ڵ����
	private TreeCache[] providerListeners = new TreeCache[2];
	// ��д��
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);

	public DefaultRegistry(String zkAddr) {
		this.zkAddr = zkAddr;
		connect();
	}

	protected void connect() {
		Builder builder = CuratorFrameworkFactory.builder();
		builder = builder.connectString(this.zkAddr);
		builder = builder.retryPolicy(new ExponentialBackoffRetry(Constants.DEF_ZK_DELAY, Constants.DEF_ZK_RETRY));
		builder = builder.connectionTimeoutMs(Constants.DEF_ZK_TIMEOUT);
		zkClient = builder.build();
		zkClient.start();
	}

	@Override
	public void registerConsumer(String group, String service, String version) {
		Map<String, String> json = new HashMap<String, String>();
		json.put("dir", System.getProperty("user.dir"));
		String path = "/thrift/reference/" + group + "/" + service + "#" + version + "/" + localIp + ":"
				+ RpcContext.listenPort;
		try {
			if (this.zkClient.checkExists().forPath(path) == null) {
				this.zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path,
						JSON.toJSONBytes(json));
			}
		} catch (Exception exp) {
			LOGGER.error("ע��consumer�쳣 {} {} {}", group, service, version, exp);
		}
	}

	@Override
	public void listen() {
		// ȷ������ĸ�Ŀ¼�������
		createRootIfNotExists("/HSF");
		createRootIfNotExists("/thrift/service");
		if (!clearNodeContent("/thrift/command")) {
			createRootIfNotExists("/thrift/command");
		}
		loadGroups();
		initListener();
	}

	@Override
	public void refresh() {
		loadGroups();
	}

	@Override
	public boolean registerService(String path, ServerNodeInfo serverNodeInfo) {
		try {
			byte[] value = JSON.toJSONString(serverNodeInfo).getBytes("utf-8");
			String result = zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
					.forPath(path, value);
			LOGGER.info("register zookeeper {} {} path is {}", path, JSON.toJSONString(serverNodeInfo), result);
			return true;
		} catch (NodeExistsException exp) {
			LOGGER.warn("[thrift] exception", exp);
		} catch (UnsupportedEncodingException exp) {
			LOGGER.warn("[thrift] exception", exp);
		} catch (Exception exp) {
			LOGGER.warn("[thrift] exception", exp);
		}
		return false;
	}

	@Override
	public boolean registerHttp(String path, String info) {
		try {
			String result = zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
					.forPath(path, info.getBytes("utf-8"));
			LOGGER.info("register zookeeper {} {} path is {}", path, JSON.toJSONString(info), result);
			return true;
		} catch (NodeExistsException exp) {
			LOGGER.warn("[thrift] exception", exp);
		} catch (UnsupportedEncodingException exp) {
			LOGGER.warn("[thrift] exception", exp);
		} catch (Exception exp) {
			LOGGER.warn("[thrift] exception", exp);
		}
		return false;
	}

	private void initListener() {
		try {
			this.providerListeners[0] = new TreeCache(zkClient, "/thrift/service");
			this.providerListeners[0].start();
			this.providerListeners[0].getListenable().addListener(new TreeCacheListener() {

				@Override
				public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
					switch (event.getType()) {
					case NODE_ADDED:
						// �ڵ����֪ͨ
						executeNodeAddEvent(event);
						break;
					case NODE_REMOVED:
						// �ڵ��Ƴ�֪ͨ
						executeNodeRemoveEvent(event);
						break;
					case NODE_UPDATED:
						// �ڵ�ֵ���µ�ʱ�򣬵���Ȩ��
						executeNodeUpdateEvent(event);
					default:
						break;
					}
				}

			});
		} catch (Exception exp) {
			LOGGER.error("create listener exception", exp);
			throw new IllegalStateException("create listener exception", exp);
		}
		try {
			this.providerListeners[1] = new TreeCache(zkClient, "/HSF");
			this.providerListeners[1].start();
			this.providerListeners[1].getListenable().addListener(new TreeCacheListener() {

				@Override
				public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
					switch (event.getType()) {
					case NODE_ADDED:
						// �ڵ����֪ͨ
						executeNodeAddEvent(event);
						break;
					case NODE_REMOVED:
						// �ڵ��Ƴ�֪ͨ
						executeNodeRemoveEvent(event);
						break;
					case NODE_UPDATED:
						// �ڵ�ֵ���µ�ʱ�򣬵���Ȩ��
						executeNodeUpdateEvent(event);
					default:
						break;
					}
				}

			});
		} catch (

		Exception exp) {
			LOGGER.error("create listener exception", exp);
			throw new IllegalStateException("create listener exception", exp);
		}
	}

	@Override
	public GrayPubService getGrayPubService(String token, String group, String service, String version) {
		return new GrayPubService(group, version);
	}

	@Override
	public List<ServerNode> loadService(String group, String service, String version) {
		// ����汾һ����a.b.c�ĸ�ʽ
		String[] verParts = version.split("\\.");
		String key = String.format(fmt, service, group, verParts[0], verParts[1]);
		Lock readLock = readWriteLock.readLock();
		// ��������
		readLock.lock();
		try {
			List<ServerNode> providers = serviceList.get(key);
			if (providers == null) {
				return new ArrayList<ServerNode>();
			}
			List<ServerNode> serverNodes = new ArrayList<ServerNode>(providers.size());
			for (ServerNode serverNode : providers) {
				serverNodes.add(serverNode);
			}
			return serverNodes;
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public void stopRegistry() {
		// �ر�zookeeper������
		for (TreeCache treeCache : providerListeners) {
			if (treeCache != null) {
				treeCache.close();
			}
		}
		this.zkClient.close();
	}

	private void executeNodeAddEvent(TreeCacheEvent event) {
		String path = event.getData().getPath();
		byte[] value = event.getData().getData();
		LOGGER.info("zookeeper node add event {}, {}", path, new String(value));
		if (path.startsWith("/thrift/service")) {
			String[] parts = path.split("/");
			if (parts.length == 6) {
				// /thrift/service/${group}/${service}/${provider}
				ServerNode serverNode = JSON.parseObject(value, ServerNode.class);
				if (serverNode == null) {
					return;
				}
				String[] vector = parts[4].split("#");
				fillServiceList(vector[0], parts[3], vector[1], parts[5], serverNode);
			}
		} else if (path.startsWith("/HSF")) {
			String[] parts = path.split("/");
			if (parts.length == 4) {
				// /HSF/${service}/${provider}
				ServerNode serverNode = JSON.parseObject(value, ServerNode.class);
				if (serverNode == null) {
					return;
				}
				String[] vector = parts[2].split("#");
				fillServiceList(vector[0], Constants.DEF_GROUP, vector[1], parts[3], serverNode);
			}
		}
	}

	private void executeNodeRemoveEvent(TreeCacheEvent event) {
		String path = event.getData().getPath();
		if (path.startsWith("/thrift/service")) {
			// /thrift/service/${group}/${service}/${provider}
			String[] parts = path.split("/");
			if (parts.length == 6) {
				String[] vector = parts[4].split("#");
				removeServiceProvider(vector[0], parts[3], vector[1], parts[5]);
			}
		} else if (path.startsWith("/HSF")) {
			// /HSF/${service}/${provider}
			String[] parts = path.split("/");
			if (parts.length == 4) {
				String[] vector = parts[2].split("#");
				removeServiceProvider(vector[0], Constants.DEF_GROUP, vector[1], parts[3]);
			}
		}
	}

	private void executeNodeUpdateEvent(TreeCacheEvent event) {
		String path = event.getData().getPath();
		int weight = getWeight(event.getData().getData());
		if (path.startsWith("/thrift/service")) {
			// /thrift/service/${group}/${service}/${provider}
			String[] parts = path.split("/");
			if (parts.length == 6) {
				String[] vector = parts[4].split("#");
				updateServiceProvider(vector[0], parts[3], vector[1], parts[5], weight);
			}
		} else if (path.startsWith("/HSF")) {
			// /HSF/${service}/${provider}
			String[] parts = path.split("/");
			if (parts.length == 4) {
				String[] vector = parts[2].split("#");
				updateServiceProvider(vector[0], Constants.DEF_GROUP, vector[1], parts[3], weight);
			}
		}
	}

	private void loadGroups() {
		// ����/thrift/service�ڵ�д�ķ���
		try {
			List<String> groups = this.zkClient.getChildren().forPath("/thrift/service");
			for (String group : groups) {
				// ���е�group
				loadServices(group);
			}
		} catch (Exception exp) {
			LOGGER.error("load service exception", exp);
		}
		// ������ʷ�汾������/HSF�µķ���
		try {
			List<String> services = this.zkClient.getChildren().forPath("/HSF");
			for (String service : services) {
				loadProviders(service);
			}
		} catch (Exception exp) {
			LOGGER.error("load service exception", exp);
		}
		// �������ӡһ�η����б�
		LOGGER.info("the thrift service list is {}", JSON.toJSONString(this.serviceList));
	}

	private void loadServices(String group) {
		try {
			List<String> services = this.zkClient.getChildren().forPath("/thrift/service/" + group);
			for (String service : services) {
				loadProviders(group, service);
			}
		} catch (Exception exp) {
			LOGGER.error("load service exception", exp);
		}
	}

	private void loadProviders(String service) {
		try {
			String path = "/HSF" + "/" + service;
			List<String> providers = this.zkClient.getChildren().forPath(path);
			for (String provider : providers) {
				byte[] value = this.zkClient.getData().forPath(path + "/" + provider);
				if (value == null || value.length <= 0) {
					continue;
				}
				ServerNode serverNode = JSON.parseObject(value, ServerNode.class);
				if (serverNode == null) {
					continue;
				}
				// ����Ψһ��ʶ
				serverNode.setUniqueTag(provider);
				String[] parts = service.split("#");
				if (parts == null || parts.length < 2) {
					continue;
				}
				fillServiceList(parts[0], Constants.DEF_GROUP, parts[1], provider, serverNode);
			}
		} catch (Exception exp) {
			LOGGER.error("load service exception", exp);
		}
	}

	private void removeServiceProvider(String service, String group, String version, String uniqueTag) {
		String[] verParts = version.split("\\.");
		if (verParts == null || verParts.length < 2) {
			return;
		}
		String key = String.format(fmt, service, group, verParts[0], verParts[1]);
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			List<ServerNode> serverNodes = serviceList.get(key);
			if (serverNodes != null) {
				Iterator<ServerNode> it = serverNodes.iterator();
				while (it.hasNext()) {
					ServerNode serverNode = it.next();
					if (uniqueTag.equals(serverNode.getUniqueTag())) {
						it.remove();
						break;
					}
				}
			}
		} finally {
			writeLock.unlock();
		}
	}

	private void updateServiceProvider(String service, String group, String version, String uniqueTag, int weight) {
		String[] verParts = version.split("\\.");
		if (verParts == null || verParts.length < 2) {
			return;
		}
		String key = String.format(fmt, service, group, verParts[0], verParts[1]);
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			List<ServerNode> serverNodes = serviceList.get(key);
			if (serverNodes != null) {
				Iterator<ServerNode> it = serverNodes.iterator();
				while (it.hasNext()) {
					ServerNode serverNode = it.next();
					if (uniqueTag.equals(serverNode.getUniqueTag())) {
						// ���¸���Ȩ��
						serverNode.setWeight(weight < 0 ? 1 : weight);
						break;
					}
				}
			}
		} finally {
			writeLock.unlock();
		}
	}

	private void fillServiceList(String service, String group, String version, String uniqueTag,
			ServerNode serverNode) {
		serverNode.setUniqueTag(uniqueTag);
		String[] verParts = version.split("\\.");
		if (verParts == null || verParts.length < 2) {
			return;
		}
		String fmt = "thrift://%s?group=%s&major=%s&minor=%s";
		String key = String.format(fmt, service, group, verParts[0], verParts[1]);
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			List<ServerNode> serverNodes = serviceList.get(key);
			if (serverNodes == null) {
				serverNodes = Lists.newArrayList();
				serviceList.put(key, serverNodes);
			}
			// ��Ҫ�жϸ÷����ṩ���Ƿ����
			boolean exist = false;
			for (ServerNode _serverNode : serverNodes) {
				if (_serverNode.getUniqueTag().equals(serverNode.getUniqueTag())) {
					exist = true;
				}
			}
			if (!exist) {
				serverNodes.add(serverNode);
			}
		} finally {
			writeLock.unlock();
		}
	}

	private void loadProviders(String group, String service) {
		try {
			String path = "/thrift/service/" + group + "/" + service;
			List<String> providers = this.zkClient.getChildren().forPath(path);
			for (String provider : providers) {
				byte[] value = this.zkClient.getData().forPath(path + "/" + provider);
				if (value == null || value.length <= 0) {
					continue;
				}
				ServerNode serverNode = JSON.parseObject(value, ServerNode.class);
				if (serverNode == null) {
					continue;
				}
				String[] parts = service.split("#");
				if (parts == null || parts.length < 2) {
					continue;
				}
				fillServiceList(parts[0], group, parts[1], provider, serverNode);
			}
		} catch (Exception exp) {
			LOGGER.error("load service exception", exp);
		}
	}

	protected void createRootIfNotExists(String rootPath) {
		try {
			// �������̵Ĵ���ͬһ�ڵ㣬��������쳣������
			ExistsBuilder existsBuilder = zkClient.checkExists();
			Stat state = existsBuilder.forPath(rootPath);
			if (state == null) {
				CreateBuilder createBuilder = zkClient.create();
				createBuilder.creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT);
				createBuilder.forPath(rootPath, "".getBytes("utf-8"));
			}
		} catch (NodeExistsException fail) {
			// �����쳣���Ժ���,�����������Ľ��̴����ĸýڵ�
			LOGGER.warn("create root path fail, {} exists!", rootPath);
		} catch (Exception exp) {
			LOGGER.warn("create zookeeper node occur exception", exp);
			throw new RpcException(exp);
		}
	}

	private boolean clearNodeContent(String path) {
		try {
			if (zkClient.checkExists().forPath(path) != null) {
				zkClient.setData().forPath(path, "".getBytes());
				return true;
			}
		} catch (Exception exp) {
			LOGGER.warn("clear zookeeper node occur exception", exp);
		}
		return false;
	}

	private int getWeight(byte[] value) {
		if (value == null) {
			return 5;
		}
		try {
			// ���ܳ��ֹ���˱��Ĵ�����
			JSONObject json = JSON.parseObject(new String(value));
			return json.getIntValue("weight");
		} catch (Exception exp) {
			return 5;
		}
	}

	public CuratorFramework getZkClient() {
		return zkClient;
	}

	public Map<String, List<ServerNode>> getServiceList() {
		return serviceList;
	}

}
