package com.zhaopin.plugin.docparser.business;

public class Constant {
	
	//wiki �ӿ�˵������title
	public static String WIKI_IFACEDESC_TABLE_TITLE = "\r\n#### ����˵�� \r\n|����·��|��������|��������|˵��|\r\n|----------------|----------------|----------------|----------------|\r\n";
	
	//wiki �ӿ������������title
	public static String WIKI_IFACEREQUESTPARAM_TABLE_TITLE = "\r\n#### �������\r\n|������|||����|�Ƿ����|˵��\r\n|----------------|----------------|----------------|----------------|----------------|----------------|\r\n";
	
	//wiki �ӿ��������demo��title
	public static String WIKI_IFACEREQUESTPARAM_DEMO_TITLE = "\r\n#### �������demo\r\n";
	
	//wiki �ӿڷ��ز�������title
	public static String WIKI_IFACEREPONSEPARAM_TABLE_TITLE = "\r\n#### ���ز���\r\n|������|||����|�Ƿ����|˵��\r\n|----------------|----------------|----------------|----------------|----------------|----------------|\r\n";
	
	//wiki �ӿ��������demo��title
	public static String WIKI_IFACERESPONSEPARAM_DEMO_TITLE = "\r\n#### ���ز���demo\r\n";
	
	//mock server ע��ӿ�
	public static String MOCK_REGISTER_URL = "http://172.17.6.23:8080/api/ifaceAutoRegister.do";
	
	//mock��ַ
	public static String MOCK_URL = "http://172.17.6.23:8080/mockjs/{0}/{1}";
	
	public static void main(String[] args) {
		System.out.println(Constant.WIKI_IFACEDESC_TABLE_TITLE);
	}
}
