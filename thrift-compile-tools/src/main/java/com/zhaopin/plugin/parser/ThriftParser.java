package com.zhaopin.plugin.parser;

import com.zhaopin.plugin.common.ThriftProject;

public interface ThriftParser {
	/**
	 * 解析项目中的标注有@ThriftInterface和@ThriftStruct的类 将@ThriftInterface的类转换成服务调用和处理实现
	 * 将@ThriftStruct的类转换成dto的序列化实现
	 * 
	 * @return 解析后的thrift数据结构, 包含struct和service
	 */
	public ThriftProject parse();
}
