package com.zhaopin.plugin.docparser.business;

public class Constant {
	
	//wiki 接口说明表格的title
	public static String WIKI_IFACEDESC_TABLE_TITLE = "\r\n#### 请求说明 \r\n|请求路径|请求类型|参数类型|说明|\r\n|----------------|----------------|----------------|----------------|\r\n";
	
	//wiki 接口请求参数表格的title
	public static String WIKI_IFACEREQUESTPARAM_TABLE_TITLE = "\r\n#### 请求参数\r\n|参数名|||类型|是否必须|说明\r\n|----------------|----------------|----------------|----------------|----------------|----------------|\r\n";
	
	//wiki 接口请求参数demo的title
	public static String WIKI_IFACEREQUESTPARAM_DEMO_TITLE = "\r\n#### 请求参数demo\r\n";
	
	//wiki 接口返回参数表格的title
	public static String WIKI_IFACEREPONSEPARAM_TABLE_TITLE = "\r\n#### 返回参数\r\n|参数名|||类型|是否必须|说明\r\n|----------------|----------------|----------------|----------------|----------------|----------------|\r\n";
	
	//wiki 接口请求参数demo的title
	public static String WIKI_IFACERESPONSEPARAM_DEMO_TITLE = "\r\n#### 返回参数demo\r\n";
	
	//mock server 注册接口
	public static String MOCK_REGISTER_URL = "http://172.17.6.23:8080/api/ifaceAutoRegister.do";
	
	//mock地址
	public static String MOCK_URL = "http://172.17.6.23:8080/mockjs/{0}/{1}";
	
	public static void main(String[] args) {
		System.out.println(Constant.WIKI_IFACEDESC_TABLE_TITLE);
	}
}
