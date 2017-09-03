package com.zhaopin.plugin.xml;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.zhaopin.plugin.common.ThriftProject;
import com.zhaopin.plugin.common.ThriftService;
import com.zhaopin.plugin.common.ThriftServiceFunc;
import com.zhaopin.plugin.common.ThriftServiceFuncParam;
import com.zhaopin.plugin.util.ThriftTypeDetermine;
import com.zhaopin.plugin.util.ThriftTypeUtil;

public class ServiceXmlGenerate {

	private static final String WIKI = "http://172.17.0.59:8080/gitbucket/platform/#{0}/wiki";

	public Product getProduct(String path) {
		return getProduct(new File(path));
	}

	public Product getProduct(File serviceFile) {
		if (!serviceFile.exists()) {
			return null;
		}
		return parseXml(serviceFile);
	}

	public String resolve(String resDirName, String srcDirName, String projectName, ThriftProject thriftProject) {
		File resDir = new File(resDirName);
		return resolve(resDir, projectName, thriftProject);
	}

	public String resolve(Set<File> resDirs, String projectName, ThriftProject thriftProject) {
		File[] resFiles = resDirs.toArray(new File[resDirs.size()]);
		return this.resolve(resFiles[0], projectName, thriftProject);
	}

	public String resolve(File resDir, String projectName, ThriftProject thriftProject) {
		File serviceDir = new File(resDir, "api");
		if (!serviceDir.exists()) {
			serviceDir.mkdirs();
		}
		File serviceFile = new File(serviceDir, "service.xml");
		Product product = new Product();
		if (!serviceFile.exists()) {
			product.setApiopenProduct("#{apiopen_product}");
			String wikiBase = WIKI.replace("#{0}", projectName);
			product.setWikiBase(wikiBase);
			product.setHttpContext("#{http_context}");
		} else {
			// 解析该文件
			product = parseXml(serviceFile);
		}
		for (ThriftService thriftService : thriftProject.getThriftServices()) {
			String serviceFullPath = ThriftTypeUtil.getClassPath(thriftService.getClassName(), thriftService);
			Services serviceCluster = resolveService(product, serviceFullPath);
			if (serviceCluster == null) {
				serviceCluster = new Services();
				serviceCluster.setInterf(serviceFullPath);
				serviceCluster.setWiki("#{wiki}");
				if (thriftService.getService() == null || "".equals(thriftService.getService())) {
					serviceCluster.setName(thriftService.getService());
				} else {
					serviceCluster.setName("#{service}");
				}
				if (thriftService.getVersion() == null || "".equals(thriftService.getVersion())) {
					serviceCluster.setVersion(thriftService.getVersion());
				} else {
					serviceCluster.setVersion("#{version}");
				}
				serviceCluster.setApiOpenGroup(thriftService.getService());
				product.addServices(serviceCluster);
			}
			for (ThriftServiceFunc func : thriftService.getServiceFuncs()) {
				Service oprt = getRegisteredService(serviceCluster, func.getFuncName());
				if (oprt != null) {
					oprt.setDesc(func.getComment());
					continue;
				} else {
					// 如果服务没有注册，需要注册进去
					oprt = new Service();
					oprt.setName(func.getFuncName());
					oprt.setMethod(detectHttpMethod(func));
					oprt.setDesc(func.getComment());
					oprt.setServiceName(funcName2ServiceName(func.getFuncName()));
					serviceCluster.addServices(oprt);
				}
			}
		}
		writeXml(serviceFile, product);
		try {
			return serviceFile.getCanonicalPath();
		} catch (Exception exp) {
			throw new IllegalStateException(exp);
		}
	}

	public Product parseXml(File path) {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(path);
			Element root = document.getRootElement();
			String apiopenProduct = root.attribute("apiopenProduct").getValue();
			String httpContext = root.attribute("httpContext").getValue();
			String wikiBase = root.attribute("wikiBase").getValue();
			Product product = new Product();
			product.setApiopenProduct(apiopenProduct);
			product.setHttpContext(httpContext);
			product.setWikiBase(wikiBase);
			List<?> groups = root.elements("services");
			for (Object group : groups) {
				if (group instanceof Element) {
					parseGroup((Element) group, product);
				}
			}
			return product;
		} catch (Exception exp) {
			throw new IllegalStateException(exp);
		}
	}

	private void writeXml(File serviceFile, Product product) {
		try {
			if (!serviceFile.exists()) {
				serviceFile.createNewFile();
			}
			StringBuffer buf = new StringBuffer();
			buf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n");
			String formatProduct = "<product apiopenProduct=\"#{0}\" httpContext=\"#{1}\" wikiBase=\"#{2}\">\r\n";
			buf.append(formatProduct.replace("#{0}", product.getApiopenProduct())
					.replace("#{1}", product.getHttpContext()).replace("#{2}", product.getWikiBase()));
			for (Services serviceCluster : product.getServiceList()) {
				String formatGroup = "#{0}<services apiopenGroup=\"#{1}\" name=\"#{2}\" version=\"#{3}\" interface=\"#{4}\" wiki=\"#{5}\">\r\n";
				String tmp = formatGroup.replace("#{0}", "\t");
				tmp = tmp.replace("#{1}", serviceCluster.getApiOpenGroup());
				tmp = tmp.replace("#{2}", serviceCluster.getName());
				tmp = tmp.replace("#{3}", serviceCluster.getVersion());
				tmp = tmp.replace("#{4}", serviceCluster.getInterf());
				tmp = tmp.replace("#{5}", serviceCluster.getWiki());
				buf.append(tmp);
				for (Service service : serviceCluster.getServices()) {
					String formatService = "#{0}<service serviceName=\"#{1}\" funcName=\"#{2}\" httpMethod=\"#{3}\" desc=\"#{4}\">";
					buf.append(formatService.replace("#{0}", "\t\t").replace("#{1}", service.getServiceName())
							.replace("#{2}", service.getName()).replace("#{3}", service.getMethod())
							.replace("#{4}", service.getDesc()));
					buf.append("</service>").append("\r\n");
				}
				buf.append("\t").append("</services>").append("\r\n");
			}
			buf.append("</product>");
			FileUtils.writeStringToFile(serviceFile, buf.toString(), "utf-8");
		} catch (Exception exp) {
			throw new IllegalStateException(exp);
		}
	}

	private void parseGroup(Element groupElem, Product product) {
		Services serviceCluster = new Services();
		String apiopenGroup = groupElem.attribute("apiopenGroup").getValue();
		String name = groupElem.attribute("name").getValue();
		String version = groupElem.attribute("version").getValue();
		String interf = groupElem.attribute("interface").getValue();
		String wiki = groupElem.attribute("wiki").getValue();
		serviceCluster.setApiOpenGroup(apiopenGroup);
		serviceCluster.setName(name);
		serviceCluster.setVersion(version);
		serviceCluster.setInterf(interf);
		serviceCluster.setWiki(wiki);
		product.addServices(serviceCluster);
		List<?> services = ((Element) groupElem).elements("service");
		for (Object service : services) {
			if (service instanceof Element) {
				parseService((Element) service, serviceCluster);
			}
		}
	}

	private void parseService(Element serviceElem, Services serviceCluster) {
		String name = serviceElem.attribute("funcName").getValue();
		String method = serviceElem.attribute("httpMethod").getValue();
		String desc = serviceElem.attribute("desc").getValue();
		String serviceName = serviceElem.attribute("serviceName").getValue();
		Service service = new Service();
		service.setName(name);
		service.setMethod(method);
		service.setDesc(desc);
		service.setServiceName(funcName2ServiceName(serviceName));
		serviceCluster.addServices(service);
	}

	private String funcName2ServiceName(String funcName) {
		StringBuffer serviceName = new StringBuffer();
		for (int t = 0; t < funcName.length(); ++t) {
			if (Character.isUpperCase(funcName.charAt(t))) {
				serviceName.append("_" + Character.toLowerCase(funcName.charAt(t)));
			} else {
				serviceName.append(funcName.charAt(t));
			}
		}
		return serviceName.toString();
	}

	private String detectHttpMethod(ThriftServiceFunc func) {
		for (ThriftServiceFuncParam funcName : func.getFuncParams()) {
			if (!ThriftTypeDetermine.isBasicType(funcName.getParamType())) {
				return "JSON";
			}
		}
		return "GET";
	}

	private Service getRegisteredService(Services serviceRegisty, String funcName) {
		for (Service oprt : serviceRegisty.getServices()) {
			if (funcName.trim().equals(oprt.getName().trim())) {
				return oprt;
			}
		}
		return null;
	}

	private Services resolveService(Product product, String serviceName) {
		List<Services> serviceCluster = product.getServiceList();
		for (Services service : serviceCluster) {
			if (service.getInterf().equals(serviceName)) {
				return service;
			}
		}
		return null;
	}
}
