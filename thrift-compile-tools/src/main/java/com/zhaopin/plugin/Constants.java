package com.zhaopin.plugin;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;

import com.zhaopin.rpc.annotation.ThriftField;
import com.zhaopin.rpc.annotation.ThriftStruct;
import com.zhaopin.thrift.rpc.codec.ThriftCodec;
import com.zhaopin.thrift.rpc.common.TField;
import com.zhaopin.thrift.rpc.common.TType;
import com.zhaopin.thrift.rpc.helper.BasicHelper;
import com.zhaopin.thrift.rpc.helper.ListHelper;
import com.zhaopin.thrift.rpc.helper.SetHelper;
import com.zhaopin.thrift.rpc.protocol.TProtocol;
import com.zhaopin.thrift.rpc.common.TStruct;
import com.zhaopin.thrift.rpc.common.TList;
import com.zhaopin.thrift.rpc.common.TSet;
import com.zhaopin.thrift.rpc.common.TMap;
import com.zhaopin.thrift.rpc.common.TMessage;
import com.zhaopin.thrift.rpc.common.TMessageType;
import com.zhaopin.thrift.rpc.processor.TProcessor;
import com.zhaopin.thrift.rpc.protocol.TProtocolUtil;
import com.zhaopin.thrift.rpc.exception.EmptyDtoException;

public class Constants {
	
	public static final String THRIFT_STRUCT = ThriftStruct.class.getName();
	
	public static final String THRIFT_FIELD = ThriftField.class.getName();

	public static final String _ThriftCodec = ThriftCodec.class.getSimpleName();

	public static final String TFIELD = TField.class.getName();
	public static final String _TFIELD = TField.class.getSimpleName();

	public static final String TProtocol = TProtocol.class.getName();
	public static final String _TProtocol = TProtocol.class.getSimpleName();

	public static final String TProcessor = TProcessor.class.getName();

	public static final String TMessage = TMessage.class.getName();
	public static final String _TMessage = TMessage.class.getSimpleName();

	public static final String TMessageType = TMessageType.class.getName();

	public static final String TTYPE = TType.class.getName();
	public static final String _TTYPE = TType.class.getName();

	public static final String TLIST = TList.class.getName();

	public static final String TSet = TSet.class.getName();

	public static final String TMap = TMap.class.getName();

	public static final String TStruct = TStruct.class.getName();
	public static final String _TStruct = TStruct.class.getSimpleName();

	public static final String TException = TException.class.getName();
	public static final String _TException = TException.class.getSimpleName();

	public static final String EmptyDtoException = EmptyDtoException.class.getName();
	public static final String _EmptyDtoException = EmptyDtoException.class.getSimpleName();

	public static final String LIST_HELPER = ListHelper.class.getName();

	public static final String SET_HELPER = SetHelper.class.getName();

	public static final String BASIC_HELPER = BasicHelper.class.getName();

	public static final String TTYPE_LIST = TType.class.getName() + ".LIST";
	public static final String _TTYPE_LIST = TType.class.getSimpleName() + ".LIST";

	public static final String TTYPE_SET = TType.class.getName() + ".SET";
	public static final String _TTYPE_SET = TType.class.getSimpleName() + ".SET";

	public static final String TTYPE_MAP = TType.class.getName() + ".MAP";
	public static final String _TTYPE_MAP = TType.class.getSimpleName() + ".MAP";

	public static final String TTYPE_STRUCT = TType.class.getName() + ".STRUCT";
	public static final String _TTYPE_STRUCT = TType.class.getSimpleName() + ".STRUCT";

	public static final String TTYPE_STOP = TType.class.getName() + ".STOP";
	public static final String _TTYPE_STOP = TType.class.getSimpleName() + ".STOP";

	public static final String TTYPE_VOID = TType.class.getName() + ".VOID";
	public static final String _TTYPE_VOID = TType.class.getSimpleName() + ".VOID";

	public static final String TTYPE_BOOL = TType.class.getName() + ".BOOL";
	public static final String _TTYPE_BOOL = TType.class.getSimpleName() + ".BOOL";

	public static final String TTYPE_BYTE = TType.class.getName() + ".BYTE";
	public static final String _TTYPE_BYTE = TType.class.getSimpleName() + ".BYTE";

	public static final String TTYPE_I16 = TType.class.getName() + ".I16";
	public static final String _TTYPE_I16 = TType.class.getSimpleName() + ".I16";

	public static final String TTYPE_I32 = TType.class.getName() + ".I32";
	public static final String _TTYPE_I32 = TType.class.getSimpleName() + ".I32";

	public static final String TTYPE_I64 = TType.class.getName() + ".I64";
	public static final String _TTYPE_I64 = TType.class.getSimpleName() + ".I64";

	public static final String TTYPE_DOUBLE = TType.class.getName() + ".DOUBLE";
	public static final String _TTYPE_DOUBLE = TType.class.getSimpleName() + ".DOUBLE";

	public static final String TTYPE_STRING = TType.class.getName() + ".STRING";
	public static final String _TTYPE_STRING = TType.class.getSimpleName() + ".STRING";
	
	public static final String TTYPE_DATE = TType.class.getName() + ".DATE";
	public static final String _TTYPE_DATE = TType.class.getSimpleName() + ".DATE";

	public static final String TProtocolUtil = TProtocolUtil.class.getName();
	public static final String _TProtocolUtil = TProtocolUtil.class.getSimpleName();

	public static final String ROCESSOR_PREFIX = "com.zhaopin.thrift.processor";
	
	public static final String ROCESSOR_CLS_PREFIX = "_";
	
	public static final String ROCESSOR_CLS_SUFFIX = "Processor_";

	public static final String INVOKER_PREFIX = "com.zhaopin.thrift.invoker";
	
	public static final String INVOKER_CLS_PREFIX = "_";
	
	public static final String INVOKER_CLS_SUFFIX = "Invoker_";

	public static final String WRAPER_PREFIX = "com.zhaopin.thrift.wraper";
	
	public static final String WRAPER_SUFFIX = "Wraper";
	
	public static final String CODEC_PREFIX = "com.zhaopin.thrift.codec";
	
	public static final String CODEC_CLS_PREFIX = "_";
	
	public static final String CODEC_CLS_SUFFIX = "Codec_";

	public static final String REPLY = TMessageType.class.getSimpleName() + ".REPLY";

	public static final Map<String, String> TYPE_MAPPING = initTypeMapping();
	
	public static final String POST_JSON = "post/json";
	public static final String POST_FORM = "post/form";
	public static final String GET = "get";

	/**
	 * 基本类型映射
	 * @return
	 */
	public static Map<String, String> initTypeMapping() {
		Map<String, String> typeMapping = new HashMap<String, String>();
		typeMapping.put("boolean", "bool");
		typeMapping.put("byte", "byte");
		typeMapping.put("short", "i16");
		typeMapping.put("int", "i32");
		typeMapping.put("long", "i64");
		typeMapping.put("double", "double");
		typeMapping.put("String", "string");
		typeMapping.put("Date", "date");
		return typeMapping;
	}
}
