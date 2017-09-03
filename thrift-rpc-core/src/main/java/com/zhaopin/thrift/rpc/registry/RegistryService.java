package com.zhaopin.thrift.rpc.registry;

import java.util.List;
import java.util.Map;

import com.zhaopin.common.log.Logger;
import com.zhaopin.common.log.LoggerFactory;
import com.zhaopin.thrift.rpc.common.ServerNode;
import com.zhaopin.thrift.rpc.server.ServerNodeInfo;

/**
 * ע�����ķ���
 *
 */
public interface RegistryService {

	public static final Logger LOGGER = LoggerFactory.getLogger(RegistryService.class);

	/**
	 * ��ʼ����zookeeper
	 */
	public void listen();

	/**
	 * ˢ�»���
	 */
	public void refresh();

	/**
	 * ����token��ȡ����ĻҶ���Ϣ
	 * 
	 * @param token
	 *            tokenֵ
	 * @param group
	 *            ����ķ���
	 * @param service
	 *            ��������
	 * @param version
	 *            ����İ汾
	 * @return
	 */
	public GrayPubService getGrayPubService(String token, String group, String service, String version);

	/**
	 * 
	 * @param group
	 *            �������
	 * @param service
	 *            ����#�汾
	 * @return
	 */
	public List<ServerNode> loadService(String group, String service, String version);

	/**
	 * ע�����
	 * 
	 * @param path
	 *            �����·��
	 * @param value
	 *            ע��Ľڵ��ֵ
	 * @return
	 */
	public boolean registerService(String path, ServerNodeInfo serverNodeInfo);

	/**
	 * ע��http�ӿ�
	 * 
	 * @param path
	 * @param info
	 * @return
	 */
	public boolean registerHttp(String path, String info);

	/**
	 * ��ȡ�����б�
	 * 
	 * @return
	 */
	public Map<String, List<ServerNode>> getServiceList();

	/**
	 * ע������������
	 * 
	 * @param group
	 * @param service
	 * @param version
	 */
	public void registerConsumer(String group, String service, String version);

	/**
	 * ֹͣ����zookeeper
	 */
	public void stopRegistry();

}
