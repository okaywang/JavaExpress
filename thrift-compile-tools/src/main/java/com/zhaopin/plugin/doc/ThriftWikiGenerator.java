package com.zhaopin.plugin.doc;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.zhaopin.plugin.common.ThriftProject;
import com.zhaopin.plugin.common.ThriftService;
import com.zhaopin.plugin.common.ThriftServiceFunc;
import com.zhaopin.plugin.common.ThriftStruct;
import com.zhaopin.plugin.common.ThriftStructField;
import com.zhaopin.plugin.docparser.DocumentParser;
import com.zhaopin.plugin.docparser.impl.DocumentParserImpl;
import com.zhaopin.plugin.wiki.ThriftWiki;

public class ThriftWikiGenerator extends AbstractGenerator {

	/**
	 * args[0] 应用上下文 args[1] 扫描的包路径 args[2] project.rootDir 根项目路径 args[3]
	 * project.name 项目的名称
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args != null && args.length > 2 && args[0] != null && args[1] != null) {
			new ThriftWikiGenerator(args[0], args[2], args[3]).generate(args[1]);
		}
	}

	// http应用的上下文
	private final String context;
	// 项目的根路径
	private final String projectRoot;
	// 项目的名称
	private final String projectName;
	// api注册模板
	private String apiOpenFmt;

	public ThriftWikiGenerator(String context, String projectRoot, String projectName) {
		if (!StringUtils.isEmpty(context) && !context.startsWith("/")) {
			context = "/" + context;
		}
		this.context = context;
		this.projectRoot = projectRoot;
		this.projectName = projectName;
		this.apiOpenFmt = getResourceContent("/META-INF/" + "openapi.fmt");
	}

	public void generate(String basePkg) {
		// 扫描出所有带有@ThriftService的类
		ThriftCandidateBean thriftCandidate = getCandidates(basePkg);
		ThriftProject thriftProject = new ThriftProject();
		for (CandidateService candidateBean : thriftCandidate.services) {
			if (candidateBean.interfaces != null) {
				for (Class<?> interf : candidateBean.interfaces) {
					if (thriftCandidate.interfDefines.containsKey(interf.getName())) {
						JSONObject json = JSON.parseObject(thriftCandidate.interfDefines.get(interf.getName()));
						if ("service".equals(json.getString("type"))) {
							ThriftService thriftService = JSON.parseObject(json.getString("data"), ThriftService.class);
							if (thriftService != null) {
								thriftProject.addThriftService(thriftService);
							}
							// 如果是service.需要判断该参数是否必须
							paramRequire(candidateBean, interf, thriftService);
							httpMethod(candidateBean, interf, thriftService);
							// 生成
						}
					}
				}
				for (Class<?> struct : thriftCandidate.structs) {
					if (thriftCandidate.interfDefines.containsKey(struct.getName())) {
						JSONObject json = JSON.parseObject(thriftCandidate.interfDefines.get(struct.getName()));
						ThriftStruct thriftStruct = JSON.parseObject(json.getString("data"), ThriftStruct.class);
						if (thriftStruct != null) {
							thriftProject.addThriftStruct(thriftStruct);
							fieldRequire(struct, thriftStruct);
						}
					}
				}
			}
		}
		// 生成wiki
		DocumentParser wikiParser = new DocumentParserImpl();
		List<ThriftWiki> wikis = wikiParser.paserToWiki(thriftProject);
		File thriftDir = new File(this.projectRoot, "build/thrift");
		if (!thriftDir.exists()) {
			thriftDir.mkdirs();
		}
		thriftDir = new File(thriftDir, this.projectName);
		if (!thriftDir.exists()) {
			thriftDir.mkdirs();
		}
		// 将wiki写入目标文件夹
		for (ThriftWiki wiki : wikis) {
			try {
				File dir = new File(thriftDir, wiki.getName());
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileUtils.writeStringToFile(new File(dir, "wiki.md"), wiki.getValue(), "utf-8");
			} catch (IOException exp) {
				System.err.println("写入wiki失败!");
				throw new IllegalStateException(exp);
			}
		}
		System.out.println("写入api的wiki信息成功!");
		genApi(thriftCandidate);
		System.out.println("写入api的注册信息成功!");
	}

	private void genApi(ThriftCandidateBean thriftCandidate) {
		Map<String, Set<String>> result = new HashMap<String, Set<String>>();
		for (CandidateService candidateBean : thriftCandidate.services) {
			if (candidateBean.interfaces != null) {
				for (Class<?> interf : candidateBean.interfaces) {
					if (thriftCandidate.interfDefines.get(interf.getName()) == null) {
						continue;
					}
					JSONObject data = JSON.parseObject(thriftCandidate.interfDefines.get(interf.getName()));
					if (data == null) {
						continue;
					}
					Set<String> lists = result.get(interf.getName());
					if (lists == null) {
						lists = Sets.newConcurrentHashSet();
						result.put(interf.getName(), lists);
					}
					for (Method method : interf.getMethods()) {
						String comment = getComment(data, method.getName());
						String httpMethod = getHttpMethod(method);
						String requestUri = "/" + candidateBean.thriftService.service() + "/" + method.getName();
						String content = apiOpenFmt.replace("#{1}", method.getName()).replace("#{2}", method.getName())
								.replace("#{3}", this.context + "" + requestUri)
								.replace("#{4}", comment.replace("\"", "'")).replace("#{5}", httpMethod);
						lists.add(content);
					}
				}
			}
		}
		// 生成api.open
		for (Entry<String, Set<String>> entry : result.entrySet()) {
			File thriftDir = new File(this.projectRoot,
					"build/thrift/" + this.projectName + "/" + entry.getKey().replace(".", "_"));
			if (!thriftDir.exists()) {
				thriftDir.mkdirs();
			}
			try {
				String data = "[\r\n" + Joiner.on(",\r\n").join(entry.getValue()) + "\r\n]";
				FileUtils.writeStringToFile(new File(thriftDir, "api.json"), data, "utf-8");
			} catch (Exception exp) {
				System.err.println("写入api的注册信息异常!");
				throw new IllegalStateException(exp);
			}
		}
	}

	private void httpMethod(CandidateService candidateBean, Class<?> interf, ThriftService thriftService) {
		for (ThriftServiceFunc thriftFunc : thriftService.getServiceFuncs()) {
			Method method = getMethod(candidateBean.beanClass, thriftFunc.getFuncName());
			if (method != null) {
				String requestUri = getContext() + "/" + candidateBean.thriftService.service() + "/" + method.getName();
				String httpMethod = getHttpMethod(method);
				thriftFunc.setHttpMethod(httpMethod);
				thriftFunc.setInnerUrl(requestUri);
			}
		}
	}

	private String getComment(JSONObject data, String methodName) {
		// 从接口中寻找该方法
		if (data.getJSONObject("data") == null) {
			return "";
		}
		JSONArray list = data.getJSONObject("data").getJSONArray("serviceFuncs");
		String comment = "";
		for (int t = 0; t < list.size(); ++t) {
			JSONObject json = list.getJSONObject(t);
			if (json != null && methodName.equals(json.getString("funcName"))) {
				comment = json.getString("comment");
				break;
			}
		}
		return comment;
	}

	private void fieldRequire(Class<?> struct, ThriftStruct thriftStruct) {
		for (ThriftStructField thriftField : thriftStruct.getStructFields()) {
			try {
				Field field = struct.getDeclaredField(thriftField.getFieldName());
				boolean require = isParamRequire(field.getAnnotations());
				thriftField.setRequired(require);
			} catch (Exception exp) {

			}
		}
	}

	private void paramRequire(CandidateService candidateBean, Class<?> interf, ThriftService thriftService) {
		for (ThriftServiceFunc thriftFunc : thriftService.getServiceFuncs()) {
			Method method = getMethod(candidateBean.beanClass, thriftFunc.getFuncName());
			if (method != null) {
				Annotation[][] annotations = method.getParameterAnnotations();
				if (annotations != null) {
					for (int t = 0; t < thriftFunc.getFuncParams().size(); ++t) {
						boolean require = isParamRequire(annotations[t]);
						thriftFunc.getFuncParams().get(t).setRequire(require);
					}
				}
			}
		}
	}

	private boolean isParamRequire(Annotation[] annotations) {
		if (annotations == null) {
			return false;
		}
		for (Annotation annotation : annotations) {
			if (annotation instanceof javax.validation.constraints.NotNull) {
				return true;
			} else if (annotation instanceof org.hibernate.validator.constraints.NotEmpty) {
				return true;
			} else if (annotation instanceof com.zhaopin.rpc.annotation.RequestParam) {
				return ((com.zhaopin.rpc.annotation.RequestParam) annotation).required();
			} else if (annotation instanceof org.springframework.web.bind.annotation.RequestParam) {
				return ((org.springframework.web.bind.annotation.RequestParam) annotation).required();
			} else if (annotation instanceof com.zhaopin.rpc.annotation.RequestHeader) {
				return ((com.zhaopin.rpc.annotation.RequestHeader) annotation).required();
			} else if (annotation instanceof org.springframework.web.bind.annotation.RequestHeader) {
				return ((org.springframework.web.bind.annotation.RequestHeader) annotation).required();
			}
		}
		return false;
	}

	private Method getMethod(Class<?> beanClass, String methodName) {
		for (Method method : beanClass.getMethods()) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		return null;
	}

	public String getContext() {
		return context;
	}

}
