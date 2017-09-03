package com.zhaopin.plugin.docparser.business;

import java.util.ArrayList;
import java.util.List;

import com.zhaopin.plugin.common.ThriftProject;
import com.zhaopin.plugin.docparser.dto.IfaceDto;
import com.zhaopin.plugin.docparser.dto.ParamDto;
import com.zhaopin.plugin.docparser.dto.ServiceDto;
import com.zhaopin.plugin.wiki.ThriftWiki;

public class ParserToWikiBusiness {

	public List<ThriftWiki> parserToWiki(ThriftProject thriftProject) {
		List<ThriftWiki> wikis = new ArrayList<ThriftWiki>();
		List<ServiceDto> serviceList = new DocumentParserUtil().paserToServiceList(thriftProject);
		for (ServiceDto serviceDto : serviceList) {
			ThriftWiki wiki = new ThriftWiki();
			wiki.setName(serviceDto.getFullPath().replace(".", "_"));
			wiki.setValue(createWiki(serviceDto));
			wikis.add(wiki);
		}
		return wikis;
	}

	private String createWiki(ServiceDto serviceDto) {

		String serviceWiki = "";
		// service����
		serviceWiki = serviceWiki + this.createServiceTitle(serviceDto.getIdentifier());

		// service �ӿڱ������ɱ��
		List<IfaceDto> ifaceList = serviceDto.getIfaceList();
		for (int i = 0; i < ifaceList.size(); i++) {
			IfaceDto dto = ifaceList.get(i);
			// �ӿ�title
			String ifaceTitle = createIfaceTitle(i + 1, dto.getRequestUrl());
			// �ӿ�˵��
			String ifaceDescTable = createIfaceDescTable(serviceDto, dto);
			// �ӿ���������б�
			String ifaceRequestParamTable = createIfaceRequestParamTable(dto.getRequestParamList());
			// �ӿ��������demo
			String ifaceRequestParamDemo = createIfaceRequsetParamDemo("{}");
			// �ӿڷ��ز����б�
			String ifaceResponseParamTable = createIfaceResponseParamTable(dto.getResponseParam());
			// �ӿڷ��ز���demo
			String ifaceResponseParamDemo = createIfaceReponseParamDemo("{}");

			serviceWiki = serviceWiki + ifaceTitle + ifaceDescTable + ifaceRequestParamTable + ifaceRequestParamDemo
					+ ifaceResponseParamTable + ifaceResponseParamDemo;

			serviceWiki += "\r\n";
		}

		return serviceWiki;
	}

	// ����Service Title
	private String createServiceTitle(String serviceTitle) {
		return "## " + serviceTitle + "\r\n";
	}

	// ����Iface Title
	private String createIfaceTitle(int index, String ifaceName) {
		int k = ifaceName.lastIndexOf("/");
		if (k < 0) {
			return "### " + index + ". " + ifaceName + "\r\n";
		} else {
			return "### " + index + ". " + ifaceName.substring(k + 1) + "\r\n";
		}
		
	}

	// ���ɽӿ�˵�����
	private String createIfaceDescTable(ServiceDto serviceDto, IfaceDto dto) {
		String ifaceDescTable = "";
		String ifaceDescTableTitle = Constant.WIKI_IFACEDESC_TABLE_TITLE;
		String ifaceDescTableTr = "";
		// �ӿ��������� 10-get,20-postjson,30-postform
		if (dto.getRequestType() == 20) {
			ifaceDescTableTr = "|" + dto.getRequestUrl() + "|" + "POST" + "|" + "JSON" + "|" + dto.getRemark()
					+ "|\r\n";
		} else if (dto.getRequestType() == 30) {
			ifaceDescTableTr = "|" + dto.getRequestUrl() + "|" + "POST" + "|" + "FORM" + "|" + dto.getRemark()
					+ "|\r\n";
		} else {
			ifaceDescTableTr = "|" + dto.getRequestUrl() + "|" + "GET" + "|" + " " + "|" + dto.getRemark() + "|\r\n";
		}
		ifaceDescTable = ifaceDescTableTitle + ifaceDescTableTr;
		return ifaceDescTable;
	}

	// ���ɽӿ��������˵�����
	private String createIfaceRequestParamTable(List<ParamDto> list) {
		String ifaceRequestParamTable = "";
		String ifaceRequestParamTableTitle = Constant.WIKI_IFACEREQUESTPARAM_TABLE_TITLE;
		String trs = "";
		for (ParamDto dto : list) {
			trs = trs + createParamTableTr(dto, 1);
		}
		ifaceRequestParamTable = ifaceRequestParamTable + ifaceRequestParamTableTitle + trs;
		return ifaceRequestParamTable;
	}

	// ���ɽӿ��������demo
	private String createIfaceRequsetParamDemo(String demo) {
		String demo_title = Constant.WIKI_IFACEREQUESTPARAM_DEMO_TITLE;
		String demo_body = "> " + demo + "\r\n";
		return demo_title + demo_body;
	}

	private String createIfaceReponseParamDemo(String demo) {
		String demo_title = Constant.WIKI_IFACERESPONSEPARAM_DEMO_TITLE;
		String demo_body = "> " + demo + "\r\n";
		return demo_title + demo_body;
	}

	// ���ɽӿڷ��ز���˵�����
	private String createIfaceResponseParamTable(ParamDto paramDto) {
		String ifaceRequestParamTable = "";
		String ifaceRequestParamTableTitle = Constant.WIKI_IFACEREPONSEPARAM_TABLE_TITLE;
		String trs = "";
		trs = trs + this.createParamTableTr(paramDto, 1);
		ifaceRequestParamTable = ifaceRequestParamTable + ifaceRequestParamTableTitle + trs;
		return ifaceRequestParamTable;
	}

	// ����ÿһ��������� Ŀǰ֧��һ��
	private String createParamTableTr(ParamDto dto, int level) {
		String p1Name = dto.getIdentifier();
		String p1Type = dto.getDataType();
		String p1Desc = dto.getRemark();
		// �Ƿ����
		String isRequired = "Y";
		if (dto.getIsrequired().equals(ParamDto.RequiredEnum.Required.getValue())) {
			isRequired = "Y";
		} else if (dto.getIsrequired().equals(ParamDto.RequiredEnum.OPTIONAL.getValue())) {
			isRequired = "N";
		}
		String tr1 = "";
		// 1 ����
		if (dto.isCollection()) {
			String col_t_1 = dto.getCollectionType();
			String col_str_1 = this.getCollectionStr(col_t_1, p1Type, dto.getKey());
			tr1 = tr1 + this.getTr(p1Name, col_str_1, isRequired, p1Desc, level);
			if (this.isCollenction(p1Type) || this.isStruct(p1Type)) {
				String trs2 = "";
				List<ParamDto> list = dto.getStruct();
				for (ParamDto paramDto : list) {
					trs2 = trs2 + this.createParamTableTr(paramDto, level + 1);
				}
				tr1 = tr1 + trs2;
			}
		}
		// ʵ��
		else if (this.isStruct(p1Type)) {
			tr1 = tr1 + getTr(p1Name, p1Type, isRequired, p1Desc, level);
			List<ParamDto> list = dto.getStruct();
			String trs2 = "";
			for (ParamDto paramDto : list) {
				trs2 = trs2 + createParamTableTr(paramDto, level + 1);
			}
			tr1 = tr1 + trs2;
		}
		// ��������
		else {
			tr1 = tr1 + this.getTr(p1Name, p1Type, isRequired, p1Desc, level);
		}

		return tr1;
	}

	private String getTr(String name, String type, String isRequired, String desc, int level) {
		if (desc == null || desc.equals("null")) {
			desc = "";
		}
		String tr = "";
		if (level == 1) {
			tr = "|" + name + "|||" + type + "|" + isRequired + "|" + desc + "\r\n";
		} else if (level == 2) {
			tr = "||" + name + "||" + type + "| " + isRequired + "|" + desc + "\r\n";
		} else if (level == 3) {
			tr = "|||" + name + "|" + type + "|" + isRequired + "|" + desc + "\r\n";
		} else {
		}
		return tr;
	}

	// list<object> map<string, object>
	private String getCollectionStr(String collectionType, String dataType, String key) {
		String value = "";
		if (this.isList(collectionType)) {
			value = collectionType + "<" + dataType + ">";
		} else if (this.isMap(collectionType)) {
			value = collectionType + "<" + key + "," + dataType + ">";
		} else {
		}
		return value;
	}

	private boolean isStruct(String paramType) {
		if (paramType.equals(ParamDto.DataTypeEnum.STRING.getValue())
				|| paramType.equals(ParamDto.DataTypeEnum.SHORT.getValue())
				|| paramType.equals(ParamDto.DataTypeEnum.INT.getValue())
				|| paramType.equals(ParamDto.DataTypeEnum.LONG.getValue())
				|| paramType.equals(ParamDto.DataTypeEnum.BOOL.getValue())
				|| paramType.equals(ParamDto.DataTypeEnum.BYTE.getValue())
				|| paramType.equals(ParamDto.DataTypeEnum.DOUBLE.getValue())
				|| paramType.equals(ParamDto.DataTypeEnum.DATE.getValue())) {
			return false;
		} else {
			return true;
		}
	}

	private boolean isMap(String paramType) {
		if (paramType.contains(ParamDto.DataTypeEnum.MAP.getValue())) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isList(String paramType) {
		if (paramType.contains(ParamDto.DataTypeEnum.LIST.getValue())) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isCollenction(String paramType) {
		if (this.isMap(paramType) || this.isList(paramType)) {
			return true;
		} else {
			return false;
		}
	}
}
