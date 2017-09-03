package com.zhaopin.thrift.rpc.registry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.zookeeper.data.Stat;

import com.alibaba.fastjson.JSON;
import com.zhaopin.thrift.rpc.exception.RpcException;

public class GrayPubRegistry extends DefaultRegistry {

	private TreeCache grayPub;
	
	private String grayFmt = "%s?group=%s&major=%s&protocol=thrift";
	// 读写锁
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);

	private Map<Integer, Map<String, GrayPubService>> grayPubCache = new HashMap<Integer, Map<String, GrayPubService>>();

	public GrayPubRegistry(String zkAddr) {
		super(zkAddr);
		initGrayPub();
	}
	
	public void stopRegistry() {
		// 停止灰度的监听
		this.grayPub.close();
		// 停止父类的监听
		super.stopRegistry();
	}

	/**
	 * 初始化灰度缓存和服务列表缓存
	 */
	public void listen() throws RpcException {
		super.listen();
		try {
			CuratorFramework zkClient = getZkClient();
			List<String> grayIds = zkClient.getChildren().forPath("/graypub");
			for (String grayId : grayIds) {
				List<String> paths = zkClient.getChildren().forPath("/graypub/" + grayId);
				for (String path : paths) {
					if (!path.endsWith("protocol=thrift")) {
						continue;
					}
					byte[] grayVal = zkClient.getData().forPath("/graypub/" + grayId + "/" + path);
					Stat stat = zkClient.checkExists().forPath("/graypub/" + grayId + "/" + path);
					Map<String, GrayPubService> results = this.grayPubCache.get(Integer.parseInt(grayId));
					if (results == null) {
						results = new HashMap<String, GrayPubService>();
						this.grayPubCache.put(Integer.parseInt(grayId), results);
					}
					GrayPubService service = results.get(path);
					if (service == null) {
						service = JSON.parseObject(grayVal, GrayPubService.class);
						if (service == null) {
							continue;
						}
						service.setTime(stat.getMtime());
						results.put(path, service);
					}
				}
			}
		} catch (Exception exp) {
			LOGGER.warn("zookeeper初始化灰度方案异常", exp);
		}
	}

	/**
	 * 查询token对应的灰度服务
	 * 
	 * @param token
	 * @param group
	 * @param service
	 * @param version
	 * @return
	 */
	@Override
	public GrayPubService getGrayPubService(String token, String group, String service, String version) {
		String parts[] = version.split("\\.");
		if (parts == null || parts.length != 3) {
			return null;
		}
		String key = String.format(grayFmt, service, group, parts[0], parts[1]);
		GrayPubService grayPub = new GrayPubService(group, version);
		if (token == null) {
			return grayPub;
		}
		byte[] bytes = token.getBytes();
		for (int t = 0; t < bytes.length; ++t) {
			byte value = 0;
			if (bytes[t] >= '0' && bytes[t] <= '9') {
				value = (byte) (bytes[t] - '0');
			} else if (bytes[t] >= 'a' && bytes[t] <= 'f') {
				value = (byte) (bytes[t] - 'a' + 10);
			}
			if (value == 0) {
				continue;
			}
			for (int k = 0; k < 4; ++k) {
				if ((value & (0x01 << k)) == 0) {
					continue;
				}
				Lock readLock = readWriteLock.readLock();
				readLock.lock();
				try {
					Map<String, GrayPubService> services = grayPubCache.get(t * 4 + k + 1);
					if (services != null) {
						GrayPubService tmp = services.get(key);
						if (tmp != null && grayPub.getTime() < tmp.getTime()) {
							grayPub = tmp;
						}
					}
				} finally {
					readLock.unlock();
				}
			}
		}
		return grayPub;
	}

	private void initGrayPub() {
		try {
			createRootIfNotExists("/graypub");
			this.grayPub = new TreeCache(zkClient, "/graypub");
			this.grayPub.start();
			this.grayPub.getListenable().addListener(new TreeCacheListener() {

				@Override
				public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
					switch (event.getType()) {
					case NODE_ADDED:
						executeNodeAddEvent(event);
						break;
					case NODE_REMOVED:
						executeGrayNodeRemoveEvent(event);
						break;
					case NODE_UPDATED:
						executeGrayNodeUpdateEvent(event);
						break;
					default:
						break;
					}
				}

			});
		} catch (Exception exp) {
			throw new IllegalStateException("init zookeeper fail!", exp);
		}
	}

	private void executeNodeAddEvent(TreeCacheEvent event) {
		ChildData childData = event.getData();
		String path = childData.getPath();
		String[] paths = path.split("/");
		if (paths.length == 4) {
			if (!paths[3].endsWith("protocol=thrift")) {
				return;
			}
			Lock writeLock = readWriteLock.writeLock();
			writeLock.lock();
			try {
				Map<String, GrayPubService> results = this.grayPubCache.get(Integer.parseInt(paths[2]));
				if (results == null) {
					results = new HashMap<String, GrayPubService>();
					this.grayPubCache.put(Integer.parseInt(paths[2]), results);
				}
				GrayPubService service = JSON.parseObject(childData.getData(), GrayPubService.class);
				service.setTime(childData.getStat().getMtime());
				results.put(paths[3], service);
			} finally {
				writeLock.unlock();
			}
		}
	}

	private void executeGrayNodeRemoveEvent(TreeCacheEvent event) {
		ChildData childData = event.getData();
		String path = childData.getPath();
		String[] paths = path.split("/");
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			if (paths.length == 4) {
				Map<String, GrayPubService> results = this.grayPubCache.get(Integer.parseInt(paths[2]));
				if (results == null) {
					return;
				}
				results.remove(paths[3]);
			} else if (paths.length == 3) {
				this.grayPubCache.remove(Integer.parseInt(paths[2]));
			}
		} finally {
			writeLock.unlock();
		}
	}

	private void executeGrayNodeUpdateEvent(TreeCacheEvent event) {
		ChildData childData = event.getData();
		String path = childData.getPath();
		String[] paths = path.split("/");
		if (paths.length == 4) {
			Lock writeLock = readWriteLock.writeLock();
			writeLock.lock();
			try {
				Map<String, GrayPubService> results = this.grayPubCache.get(Integer.parseInt(paths[2]));
				if (results == null) {
					results = new HashMap<String, GrayPubService>();
					this.grayPubCache.put(Integer.parseInt(paths[2]), results);
				}
				GrayPubService service = JSON.parseObject(childData.getData(), GrayPubService.class);
				service.setTime(childData.getStat().getMtime());
				results.put(paths[3], service);
			} finally {
				writeLock.unlock();
			}
		}
	}
}
