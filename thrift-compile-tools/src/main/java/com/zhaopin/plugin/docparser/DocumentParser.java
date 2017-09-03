package com.zhaopin.plugin.docparser;

import java.util.List;

import com.zhaopin.plugin.common.ThriftProject;
import com.zhaopin.plugin.wiki.ThriftWiki;

public interface DocumentParser {

	public String parserToJson(ThriftProject thriftProject);

	public void writeThriftPaserWikiResult(List<ThriftWiki> wikis, String resDir);

	public List<ThriftWiki> paserToWiki(ThriftProject thriftProject);
}
