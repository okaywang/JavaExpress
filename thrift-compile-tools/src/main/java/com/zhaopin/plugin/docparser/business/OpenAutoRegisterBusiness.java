package com.zhaopin.plugin.docparser.business;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.zhaopin.plugin.common.ThriftProject;
import com.zhaopin.plugin.docparser.dto.IfaceDto;
import com.zhaopin.plugin.docparser.dto.ServiceAutoIncDto;
import com.zhaopin.plugin.docparser.dto.ServiceDto;
import com.zhaopin.plugin.xml.OpenService;
import com.zhaopin.plugin.xml.Product;
import com.zhaopin.plugin.xml.ServiceXmlGenerate;

public class OpenAutoRegisterBusiness {

	public boolean openAutoRegister(ThriftProject thriftProject, String xmlDir) {
		List<ServiceAutoIncDto> serviceAutoIncList = new ArrayList<ServiceAutoIncDto>();
		List<ServiceDto> serviceList = new DocumentParserUtil().paserToServiceList(thriftProject);
		Product product = new ServiceXmlGenerate().getProduct(xmlDir);
		StringBuffer buf = new StringBuffer();
		for (ServiceDto serviceDto : serviceList) {
			List<ServiceAutoIncDto> tmp = createServiceAutoInc(serviceDto, product);
			serviceAutoIncList.addAll(tmp);
			buf.append(generateSQL(tmp));
		}
		try {
			File file = new File(xmlDir);
			File dir = file.getParentFile();
			File sqlFile = new File(dir, "api.sql");
			System.out.println("write generate sql file " + sqlFile.getCanonicalPath());
			FileUtils.writeStringToFile(sqlFile, buf.toString());
		} catch (Exception exp) {
			throw new IllegalStateException(exp);
		}
		return true;
	}

	private String generateSQL(List<ServiceAutoIncDto> serviceAutoIncList) {
		StringBuffer buf = new StringBuffer();
		if (serviceAutoIncList != null && serviceAutoIncList.size() > 0) {
			buf.append("INSERT INTO open_service(" + "service_name, service_outer_url, service_inner_url, "
					+ "service_wiki_url, service_type, service_desc, group_id, createdate) VALUES").append("\r\n");
			for (int t = 0; t < serviceAutoIncList.size(); ++t) {
				ServiceAutoIncDto dto = serviceAutoIncList.get(t);
				if (t == 0) {
					buf.append("('" + dto.getName() + "', ").append("'" + dto.getOuterurl() + "', ")
							.append("'" + dto.getInnerurl() + "', ").append("'" + dto.getWikiurl() + "', ")
							.append("'" + dto.getRequesttype() + "', ")
							.append("'" + dto.getDescription() + "', #{_GROUP_}, now())");
				} else {
					buf.append(",\r\n('" + dto.getName() + "', ").append("'" + dto.getOuterurl() + "', ")
							.append("'" + dto.getInnerurl() + "', ").append("'" + dto.getWikiurl() + "', ")
							.append("'" + dto.getRequesttype() + "', ")
							.append("'" + dto.getDescription() + "', #{_GROUP_}, now())");
				}
			}
		}
		return buf.toString();
	}

	private List<ServiceAutoIncDto> createServiceAutoInc(ServiceDto serviceDto, Product product) {
		List<ServiceAutoIncDto> list = new ArrayList<ServiceAutoIncDto>();
		for (IfaceDto ifaceDto : serviceDto.getIfaceList()) {
			ServiceAutoIncDto dto = new ServiceAutoIncDto();
			OpenService open = product.getOpenService(serviceDto.getFullPath(), ifaceDto.getRequestUrl());
			// 真实url
			dto.setInnerurl(open.getInnerUrl());
			// 对外url
			dto.setOuterurl(open.getOuterUrl());
			// wiki url
			dto.setWikiurl(open.getWikiUrl());
			// http method
			String httpMethod = open.getHttpMethod().toUpperCase().trim();
			if (httpMethod.equals("GET")) {
				dto.setRequesttype("GET");
			} else if (httpMethod.equals("JSON")) {
				dto.setRequesttype("POST");
			} else {
			}
			// 服务名称
			dto.setName(ifaceDto.getRequestUrl());
			// desc
			dto.setDescription(ifaceDto.getRemark());
			// product name
			dto.setProductname(open.getProductName());
			// group name
			dto.setGroupname(open.getGroupName());

			list.add(dto);
		}
		return list;
	}
}
