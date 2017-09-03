package com.zhaopin.plugin.docparser.impl;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.zhaopin.plugin.common.ThriftProject;
import com.zhaopin.plugin.docparser.DocumentParser;
import com.zhaopin.plugin.docparser.business.DocumentParserUtil;
import com.zhaopin.plugin.docparser.business.ParserToWikiBusiness;
import com.zhaopin.plugin.docparser.dto.ServiceDto;
import com.zhaopin.plugin.wiki.ThriftWiki;

public class DocumentParserImpl implements DocumentParser {

	@Override
	public String parserToJson(ThriftProject thriftProject) {
		List<ServiceDto> serviceList = new DocumentParserUtil().paserToServiceList(thriftProject);
		return JSON.toJSONString(serviceList);
	}

	@Override
	public List<ThriftWiki> paserToWiki(ThriftProject thriftProject) {
		return new ParserToWikiBusiness().parserToWiki(thriftProject);
	}

	public void writeThriftPaserWikiResult(List<ThriftWiki> wikis, Set<File> resDir) throws Exception {
		if (resDir.iterator().hasNext()) {
			File file = resDir.iterator().next();
			writeThriftPaserWikiResult(wikis, file.getCanonicalPath());
		}
	}

	public void writeThriftPaserWikiResult(List<ThriftWiki> wikis, String resDir) {
		String wikiPath = resDir + File.separator + "wiki";
		File wikiPage = new File(wikiPath);
		if (!wikiPage.exists()) {
			wikiPage.mkdirs();
		}
		System.out.println("[thrift] thrift wiki dir:" + wikiPath);
		for (ThriftWiki wiki : wikis) {
			String wikiName = wiki.getName();
			String wikiValue = wiki.getValue();
			File file = new File(wikiPath + File.separator + wikiName + ".md");
			if (!file.exists()) {
				try {
					file.createNewFile();

				} catch (Exception exp) {
					throw new IllegalStateException("无法创建文件", exp);
				}
			}
			try {
				FileUtils.writeStringToFile(file, wikiValue, "utf-8");
			} catch (Exception exp) {
				throw new IllegalStateException("写入wiki失败", exp);
			}

		}
	}

}
