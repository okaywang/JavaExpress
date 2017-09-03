package com.zhaopin.plugin.docparser.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zhaopin.plugin.Constants;
import com.zhaopin.plugin.common.ThriftProject;
import com.zhaopin.plugin.common.ThriftService;
import com.zhaopin.plugin.common.ThriftServiceFunc;
import com.zhaopin.plugin.common.ThriftServiceFuncParam;
import com.zhaopin.plugin.common.ThriftStruct;
import com.zhaopin.plugin.common.ThriftStructField;
import com.zhaopin.plugin.docparser.dto.IfaceDto;
import com.zhaopin.plugin.docparser.dto.ParamDto;
import com.zhaopin.plugin.docparser.dto.ServiceDto;

public class DocumentParserUtil {

	private Map<String, ThriftStruct> thriftStructMap = new HashMap<String, ThriftStruct>();

	public List<ServiceDto> paserToServiceList(ThriftProject thriftProject) {
		return parserToServiceList(thriftProject);
	}

	private List<ServiceDto> parserToServiceList(ThriftProject thriftProject) {
		List<ServiceDto> ifaceList = new ArrayList<ServiceDto>();
		Set<ThriftStruct> thriftStructList = thriftProject.getThriftStructs();
		initStructMap(thriftStructList);
		for (ThriftService thriftService : thriftProject.getThriftServices()) {
			ServiceDto dto = parserToService(thriftService);
			dto.setFullPath(thriftService.getPkgName() + "." + thriftService.getClassName());
			ifaceList.add(dto);
		}
		return ifaceList;
	}

	private void initStructMap(Set<ThriftStruct> thriftStructList) {
		for (ThriftStruct thriftStruct : thriftStructList) {
			this.thriftStructMap.put(thriftStruct.getClassName(), thriftStruct);
		}
	}

	private ServiceDto parserToService(ThriftService thriftService) {
		ServiceDto serviceDto = new ServiceDto();
		serviceDto.setName(thriftService.getClassName());
		serviceDto.setIdentifier(thriftService.getClassName());
		Set<ThriftServiceFunc> thriftServiceFuncList = thriftService.getServiceFuncs();
		List<IfaceDto> ifaceList = new ArrayList<IfaceDto>();
		for (ThriftServiceFunc thriftServiceFunc : thriftServiceFuncList) {
			ifaceList.add(parserToIface(thriftService, thriftServiceFunc));
		}
		serviceDto.setIfaceList(ifaceList);
		return serviceDto;
	}

	private IfaceDto parserToIface(ThriftService thriftService, ThriftServiceFunc thriftServiceFunc) {
		IfaceDto ifaceDto = new IfaceDto();
		ifaceDto.setName(thriftServiceFunc.getFuncName());
		// 接口路径
		ifaceDto.setRequestUrl(thriftServiceFunc.getFuncName());
		List<ParamDto> requestParamList = new ArrayList<ParamDto>();
		List<ThriftServiceFuncParam> thriftServiceFuncParamList = thriftServiceFunc.getFuncParams();
		// 接口备注说明
		ifaceDto.setRemark(thriftServiceFunc.getComment());
		// 请求类型
		if (Constants.POST_JSON.equals(thriftServiceFunc.getHttpMethod())) {
			ifaceDto.setRequestType(IfaceDto.RequestType.POSTJSON.getValue());
		} else if (Constants.POST_FORM.equals(thriftServiceFunc.getHttpMethod())) {
			ifaceDto.setRequestType(IfaceDto.RequestType.POSTFORM.getValue());
		} else {
			ifaceDto.setRequestType(IfaceDto.RequestType.GET.getValue());
		}
		for (ThriftServiceFuncParam thriftServiceFuncParam : thriftServiceFuncParamList) {
			requestParamList.add(parserToRequestParamDto(thriftServiceFuncParam));
		}
		ifaceDto.setRequestUrl(thriftServiceFunc.getInnerUrl());
		ifaceDto.setRequestParamList(requestParamList);
		ifaceDto.setResponseParam(parserToResponseParamDto(thriftServiceFunc.getRetType()));
		return ifaceDto;
	}

	private ParamDto parserToRequestParamDto(ThriftServiceFuncParam thriftServiceFuncParam) {
		ParamDto requestParamDto = new ParamDto();
		requestParamDto.setName(thriftServiceFuncParam.getParamName());
		requestParamDto.setIdentifier(thriftServiceFuncParam.getParamName());
		requestParamDto.setDataType(thriftServiceFuncParam.getParamType());
		requestParamDto.setType(ParamDto.TypeEnum.REQUEST.getValue());
		// 方法参数是否必须
		if (thriftServiceFuncParam.isRequire()) {
			requestParamDto.setIsrequired(ParamDto.RequiredEnum.Required.getValue());
		} else {
			requestParamDto.setIsrequired(ParamDto.RequiredEnum.OPTIONAL.getValue());
		}
		requestParamDto.setRemark(thriftServiceFuncParam.getComment());

		String paramType = requestParamDto.getDataType();
		// 集合
		if (this.isCollection(paramType)) {
			String collectionT = this.getTypeStringFlag(paramType);
			String collectionV = this.getTypeString(paramType);
			if (collectionT.equals(ParamDto.DataTypeEnum.MAP.getValue())) {
				collectionV = this.getMapValueType(paramType);
				// 设置map的key
				requestParamDto.setKey(this.getMapKey(paramType));
			}
			requestParamDto.setCollection(true);
			requestParamDto.setCollectionType(collectionT);
			requestParamDto.setDataType(collectionV);
			List<ParamDto> list = paserToParamDto(collectionV, requestParamDto.getType());
			requestParamDto.setStruct(list);
		}
		// 实体
		else if (this.isStruct(paramType)) {
			List<ParamDto> list = paserToParamDto(requestParamDto.getDataType(), requestParamDto.getType());
			requestParamDto.setStruct(list);
		}
		return requestParamDto;
	}

	private ParamDto parserToResponseParamDto(String retType) {
		ParamDto responseParamDto = new ParamDto();
		responseParamDto.setName(retType);
		responseParamDto.setIdentifier(retType);
		responseParamDto.setDataType(retType);
		responseParamDto.setType(ParamDto.TypeEnum.RESPONSE.getValue());
		String paramType = responseParamDto.getDataType();
		// 集合
		if (this.isCollection(paramType)) {
			String collectionT = this.getTypeStringFlag(paramType);
			String collectionV = this.getTypeString(paramType);
			if (collectionT.equals(ParamDto.DataTypeEnum.MAP.getValue())) {
				collectionV = this.getMapValueType(paramType);
				responseParamDto.setKey(this.getMapKey(paramType));
			}
			responseParamDto.setCollection(true);
			responseParamDto.setCollectionType(collectionT);
			responseParamDto.setDataType(collectionV);
			List<ParamDto> list = paserToParamDto(collectionV, responseParamDto.getType());
			responseParamDto.setStruct(list);
		}
		// 实体
		else if (this.isStruct(paramType)) {
			List<ParamDto> list = paserToParamDto(responseParamDto.getDataType(), responseParamDto.getType());
			responseParamDto.setStruct(list);
		} else {
		}
		return responseParamDto;
	}

	private List<ParamDto> paserToParamDto(String structName, short type) {
		List<ParamDto> paramDtoList = new ArrayList<ParamDto>();
		// 集合
		if (this.isCollection(structName)) {

			ParamDto paramDto = new ParamDto();
			// 集合类型
			String collectionType = this.getTypeStringFlag(structName);
			// 集合泛型
			String colletionValue = this.getTypeString(structName);
			// map集合
			if (collectionType.equals(ParamDto.DataTypeEnum.MAP.getValue())) {
				colletionValue = this.getMapValueType(structName);
				paramDto.setKey(this.getMapKey(structName));
			}
			paramDto.setName(collectionType);
			paramDto.setIdentifier(collectionType);
			paramDto.setDataType(colletionValue);
			paramDto.setType(type);
			paramDto.setCollection(true);
			paramDto.setCollectionType(collectionType);
			// 集合泛型是集合
			if (this.isCollection(colletionValue)) {
				paramDto.setStruct(paserToParamDto(colletionValue, type));
			}
			// 集合类型是实体
			else if (this.isStruct(colletionValue)) {
				paramDto.setStruct(paserToParamDto(colletionValue, type));
			}
			paramDtoList.add(paramDto);
		}
		// 实体
		else if (this.isStruct(structName)) {
			if (this.thriftStructMap.containsKey(structName)) {
				ThriftStruct thriftStruct = thriftStructMap.get(structName);
				Set<ThriftStructField> fields = thriftStruct.getStructFields();
				for (ThriftStructField field : fields) {
					ParamDto paramDto = new ParamDto();
					paramDto.setName(field.getFieldName());
					paramDto.setIdentifier(field.getFieldName());
					paramDto.setDataType(field.getFieldType());
					paramDto.setDataType(field.getFieldType());
					if (field.isRequired()) {
						paramDto.setIsrequired(ParamDto.RequiredEnum.Required.getValue());
					} else {
						paramDto.setIsrequired(ParamDto.RequiredEnum.OPTIONAL.getValue());
					}
					paramDto.setDataType(field.getFieldType());
					// 设置备注说明
					paramDto.setRemark(field.getComment());
					String dataType = paramDto.getDataType();
					if (this.isCollection(dataType)) {
						String col_t = this.getTypeStringFlag(dataType);
						String col_v = this.getTypeString(dataType);
						paramDto.setCollection(true);
						paramDto.setCollectionType(col_t);
						paramDto.setDataType(col_v);
						if (col_t.equals(ParamDto.DataTypeEnum.MAP.getValue())) {
							paramDto.setDataType(this.getMapValueType(dataType));
							paramDto.setKey(this.getMapKey(dataType));
						}
					}
					if (this.isStruct(paramDto.getDataType())) {
						paramDto.setStruct(paserToParamDto(paramDto.getDataType(), type));
					}
					paramDtoList.add(paramDto);
				}
			}
		} else {
		}
		return paramDtoList;
	}

	private boolean isStruct(String paramType) {
		if (paramType.equals(ParamDto.DataTypeEnum.STRING.getValue())
				|| paramType.equals(ParamDto.DataTypeEnum.SHORT.getValue())
				|| paramType.equals(ParamDto.DataTypeEnum.INT.getValue())
				|| paramType.equals(ParamDto.DataTypeEnum.LONG.getValue())
				|| paramType.equals(ParamDto.DataTypeEnum.BOOL.getValue())
				|| paramType.equals(ParamDto.DataTypeEnum.BYTE.getValue())
				|| paramType.equals(ParamDto.DataTypeEnum.DOUBLE.getValue())) {
			return false;
		} else {
			return true;
		}
	}

	private boolean isCollection(String dataType) {
		if (dataType.indexOf("<") != -1) {
			return true;
		} else {
			return false;
		}
	}

	private String getTypeStringFlag(String dataType) {
		return dataType.substring(0, dataType.indexOf("<"));
	}

	private String getTypeString(String dataType) {
		return dataType.substring(dataType.indexOf("<") + 1, dataType.length() - 1);
	}

	private String getMapValueType(String dataType) {
		if (dataType.contains(">")) {
			return dataType.substring(dataType.indexOf(",") + 1, dataType.length() - 1).trim();
		} else {
			return dataType.substring(dataType.indexOf(",") + 1).trim();
		}
	}

	private String getMapKey(String dataType) {
		return dataType.substring(dataType.indexOf("<") + 1, dataType.indexOf(","));
	}

}
