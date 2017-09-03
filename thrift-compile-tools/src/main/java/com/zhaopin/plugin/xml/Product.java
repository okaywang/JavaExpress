package com.zhaopin.plugin.xml;

import java.util.ArrayList;
import java.util.List;

public class Product {
	// 产品的名称
	private String apiopenProduct;
	// 应用的上下文
	private String httpContext = "";
	// wiki的基地址
	private String wikiBase;
	// 所有的分组
	private List<Services> serviceList = new ArrayList<Services>();

	public OpenService getOpenService(String interf, String method) {
		interf = interf.trim();
		method = method.trim();
		for (Services serviceCluster : serviceList) {
			if (serviceCluster.getInterf().equals(interf)) {
				for (Service service : serviceCluster.getServices()) {
					if (service.getName().equals(method)) {
						OpenService openService = new OpenService();
						if (httpContext.length() <= 0) {
							openService.setInnerUrl("/" + serviceCluster.getName() + "/" + serviceCluster.getVersion()
									+ "/" + service.getName());
						} else {
							openService.setInnerUrl("/" + httpContext + "/" + serviceCluster.getName() + "/"
									+ serviceCluster.getVersion() + "/" + service.getName());
						}
						openService.setOuterUrl("/" + apiopenProduct + "/" + serviceCluster.getApiOpenGroup() + "/"
								+ service.getServiceName());
						openService.setDesc(service.getDesc());
						openService.setWikiUrl("" + wikiBase + "/" + serviceCluster.getWiki());
						openService.setProductName(apiopenProduct);
						openService.setGroupName(serviceCluster.getApiOpenGroup());
						openService.setHttpMethod(service.getMethod());
						return openService;
					}
				}
			}
		}
		throw new IllegalArgumentException("arg\"" + interf + "\", \"" + method + "\" is invalid!");
	}

	public String getApiopenProduct() {
		return apiopenProduct;
	}

	public void setApiopenProduct(String apiopenProduct) {
		this.apiopenProduct = apiopenProduct;
	}

	public String getHttpContext() {
		return httpContext;
	}

	public void setHttpContext(String httpContext) {
		this.httpContext = httpContext;
	}

	public List<Services> getServiceList() {
		return serviceList;
	}

	public void addServices(Services services) {
		this.serviceList.add(services);
	}

	public String getWikiBase() {
		return wikiBase;
	}

	public void setWikiBase(String wikiBase) {
		this.wikiBase = wikiBase;
	}
}
