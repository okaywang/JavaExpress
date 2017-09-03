package com.zhaopin.plugin.parser;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.zhaopin.plugin.common.ThriftObject;
import com.alibaba.fastjson.JSONObject;
import com.zhaopin.plugin.Constants;
import com.zhaopin.plugin.common.ThriftFileType;
import com.zhaopin.plugin.common.ThriftProject;
import com.zhaopin.plugin.common.ThriftService;
import com.zhaopin.plugin.common.ThriftServiceFunc;
import com.zhaopin.plugin.common.ThriftStruct;
import com.zhaopin.plugin.generate.AnnotationThriftGenerator;
import com.zhaopin.plugin.generate.ThriftGenerator;
import com.zhaopin.plugin.util.ThriftTypeUtil;
import com.zhaopin.plugin.xml.OpenService;
import com.zhaopin.plugin.xml.Product;
import com.zhaopin.plugin.xml.ServiceXmlGenerate;

/**
 * 
 * @author shunli.gao
 *
 */
public class AnnotationThriftParser extends AbstractThriftParser {

	public AnnotationThriftParser(String srcDir, String resDir) {
		this(new File(srcDir), new File(resDir));
	}

	public AnnotationThriftParser(File srcDir, File resDir) {
		super(new File[] { srcDir }, new File[] { resDir });
	}

	public AnnotationThriftParser(Set<File> srcDirs, Set<File> resDirs) {
		super(srcDirs.toArray(new File[srcDirs.size()]), resDirs.toArray(new File[resDirs.size()]));
	}

	@Override
	public ThriftProject parse() {
		// Դ����·���µ��ļ����жϸ����Ƿ���@ThriftService��@ThriftStructע��
		ThriftProject thriftProject = new ThriftProject();
		// �����ж�Դ����Ŀ¼���Ƿ���thriftĿ¼
		cleanFiles();
		File thriftDir = getThriftSrcDir(getSrcDir());
		// ɾ��thriftDis����������ļ�
		for (File file : getSrcDir()) {
			ThriftProject tp = new ThriftProject();
			Collection<File> files = FileUtils.listFiles(file, new SourceFilter(), new DirFilter());
			if (files == null) {
				continue;
			}
			for (File thriftFile : files) {
				// �������ļ�
				ThriftObject thriftObject = null;
				try {
					thriftObject = parseFile(thriftFile);
				} catch (Exception exp) {
					System.err.println("����" + thriftFile.getAbsolutePath() + "ʧ��");
					throw exp;
				}
				if (thriftObject == null) {
					continue;
				}
				tp.addThriftObject(thriftObject);
			}
			if (thriftDir != null) {
				ThriftGenerator gen = new AnnotationThriftGenerator(thriftDir);
				gen.generate(tp);
				thriftProject.addThriftServices(tp.getThriftServices());
				thriftProject.addThriftStructs(tp.getThriftStructs());
			} else {
				ThriftGenerator gen = new AnnotationThriftGenerator(file);
				gen.generate(tp);
				thriftProject.addThriftServices(tp.getThriftServices());
				thriftProject.addThriftStructs(tp.getThriftStructs());
			}
		}
		File[] resDirs = this.getResDir();
		if (resDirs == null || resDirs.length <= 0) {
			System.err.println("û��������ԴĿ¼!");
		} else {
			// ÿһ�α����ʱ������
			File resDir = getTargetResourceDir(resDirs);
			if (resDir == null) {
				// SEE WIKI
				System.err.println("gradle.build��û��������Դ�ļ�Ŀ¼!");
			}
			if (!resDir.exists()) {
				// SEE WIKI
				System.err.println("��ԴĿ¼" + resDir.getAbsolutePath() + "�����ڻ���û�����ó�gradle����ԴĿ¼!");
			} else {
				boolean skip = false;
				if (thriftProject.getThriftServices() == null || thriftProject.getThriftServices().size() <= 0) {
					skip = true;
				}
				if (thriftProject.getThriftStructs() == null || thriftProject.getThriftStructs().size() <= 0) {
					skip = true;
				}
				if (!skip) {
					File cfgFile = new File(resDir, "META-INF/thrift/cfg");
					if (!cfgFile.exists()) {
						cfgFile.mkdirs();
					}
					if (thriftProject.getThriftServices() != null) {
						for (ThriftService thriftService : thriftProject.getThriftServices()) {
							String fullName = thriftService.getPkgName() + "." + thriftService.getClassName();
							fullName = fullName.replace(".", "_") + ".json";
							File file = new File(cfgFile, fullName);
							JSONObject json = new JSONObject();
							json.put("type", "service");
							json.put("data", thriftService);
							try {
								FileUtils.writeStringToFile(file, json.toJSONString(), "utf-8");
							} catch (Exception exp) {
								System.err.println("д���ļ�" + file.getAbsolutePath() + "�쳣!");
							}
						}
					}
					if (thriftProject.getThriftStructs() != null) {
						for (ThriftStruct thriftStruct : thriftProject.getThriftStructs()) {
							String fullName = thriftStruct.getPkgName() + "." + thriftStruct.getClassName();
							fullName = fullName.replace(".", "_") + ".json";
							File file = new File(cfgFile, fullName);
							JSONObject json = new JSONObject();
							json.put("type", "service");
							json.put("data", thriftStruct);
							try {
								FileUtils.writeStringToFile(file, json.toJSONString(), "utf-8");
							} catch (Exception exp) {
								System.err.println("д���ļ�" + file.getAbsolutePath() + "�쳣!");
							}
						}
					}
				}
			}
		}
		return thriftProject;
	}

	private void cleanFiles() {
		for (File file : getSrcDir()) {
			if (file.exists() && file.isDirectory()) {
				Collection<File> files = FileUtils.listFiles(file, new String[] { "java" }, true);
				for (File f : files) {
					if (f.isFile() && f.getName().startsWith("_")) {
						if (f.getName().startsWith(Constants.CODEC_CLS_SUFFIX + ".java")) {
							FileUtils.deleteQuietly(f);
						} else if (f.getName().startsWith(Constants.INVOKER_CLS_SUFFIX + ".java")) {
							FileUtils.deleteQuietly(f);
						} else if (f.getName().startsWith(Constants.ROCESSOR_CLS_SUFFIX + ".java")) {
							FileUtils.deleteQuietly(f);
						}
					}
				}
			}
		}
	}

	private File getTargetResourceDir(File[] files) {
		if (files == null || files.length <= 0) {
			return null;
		}
		for (File file : files) {
			File tmp = file;
			if (!tmp.isDirectory() || !tmp.getName().equals("resources")) {
				continue;
			}
			tmp = tmp.getParentFile();
			if (tmp == null || !tmp.getName().equals("main")) {
				continue;
			}
			tmp = tmp.getParentFile();
			if (tmp == null || !tmp.getName().equals("src")) {
				continue;
			}
			return file;
		}
		return files[0];
	}

	private File getThriftSrcDir(File[] files) {
		for (File file : files) {
			File tmp = file;
			if (!tmp.isDirectory() || !tmp.getName().equals("thrift")) {
				continue;
			}
			tmp = tmp.getParentFile();
			if (tmp == null || !tmp.getName().equals("main")) {
				continue;
			}
			tmp = tmp.getParentFile();
			if (tmp == null || !tmp.getName().equals("src")) {
				continue;
			}
			return file;
		}
		return null;
	}

	protected void completeInfoByXml(ThriftProject thriftProject) {
		try {
			// ������󣬶�service������Ϣ����
			File[] resDirs = this.getResDir();
			File resDir = null;
			if (resDirs != null && resDirs.length > 0) {
				resDir = resDirs[0];
			}
			if (resDir == null) {
				return;
			}
			File serviceDir = new File(resDir, "api");
			if (!serviceDir.exists()) {
				return;
			}
			File serviceFile = new File(serviceDir, "service.xml");
			if (!serviceFile.exists()) {
				return;
			}
			Product product = new ServiceXmlGenerate().getProduct(serviceFile);
			for (ThriftService thriftService : thriftProject.getThriftServices()) {
				String className = ThriftTypeUtil.getClassPath(thriftService.getClassName(), thriftService);
				for (ThriftServiceFunc func : thriftService.getServiceFuncs()) {
					OpenService openService = product.getOpenService(className, func.getFuncName());
					if (openService != null) {
						func.setGroupName(openService.getGroupName());
						func.setHttpMethod(openService.getHttpMethod());
						func.setInnerUrl(openService.getInnerUrl());
						func.setOuterUrl(openService.getOuterUrl());
						func.setProductName(openService.getProductName());
						func.setWikiUrl(openService.getWikiUrl());
					}
				}
			}
		} catch (Exception exp) {
			System.out.println("ͨ��api/service.xml������Ϣʧ��!");
		}
	}

	enum ThriftType {
		ThriftService, ThriftStruct, None
	}

	private ThriftObject parseFile(File thriftFile) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setIgnoreMethodBodies(true);
		Map<?, ?> options = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_7, options);
		parser.setCompilerOptions(options);
		try {
			String content = FileUtils.readFileToString(thriftFile, "utf-8");
			parser.setSource(content.toCharArray());
			parser.setResolveBindings(true);
			CompilationUnit result = (CompilationUnit) parser.createAST(null);
			String pkgName = result.getPackage().getName().getFullyQualifiedName();
			List<?> types = result.types();
			if (types != null && types.size() <= 0) {
				return null;
			}
			Set<String> imports = parserImports(result);
			TypeDeclaration thriftInstance = (TypeDeclaration) types.get(0);
			List<?> modifiers = thriftInstance.modifiers();
			ThriftFileType thriftType = ThriftFileType.NONE;
			if (modifiers != null) {
				for (Object obj : modifiers) {
					if (!(obj instanceof MarkerAnnotation)) {
						continue;
					}
					MarkerAnnotation annotation = (MarkerAnnotation) obj;
					String fullName = annotation.getTypeName().getFullyQualifiedName();
					Class<?> structClass = com.zhaopin.rpc.annotation.ThriftStruct.class;
					Class<?> serviceClass = com.zhaopin.rpc.annotation.ThriftInterface.class;
					if (structClass.getName().equals(fullName) || structClass.getSimpleName().equals(fullName)) {
						thriftType = ThriftFileType.THRIFT_STRUCT;
					} else if (serviceClass.getName().equals(fullName)
							|| serviceClass.getSimpleName().equals(fullName)) {
						thriftType = ThriftFileType.THRIFT_SERVICE;
					}
				}
			}
			if (thriftType == ThriftFileType.NONE) {
				return null;
			}
			ThriftObject thriftObject = null;
			String className = thriftInstance.getName().getIdentifier();
			if (thriftType == ThriftFileType.THRIFT_SERVICE) {
				thriftObject = doParseService(thriftInstance, pkgName, className, imports);
			} else if (thriftType == ThriftFileType.THRIFT_STRUCT) {
				// ֻ��struct�вŴ�����ע��
				List<String> list = FileUtils.readLines(thriftFile, "utf-8");
				thriftObject = doParseStruct(thriftInstance, pkgName, className, imports, new StructContext(list));
			}
			System.out.println("detect " + thriftFile.getAbsolutePath() + " is thrift defination!");
			return thriftObject;
		} catch (Exception cause) {
			throw new IllegalStateException(cause);
		}
	}

	private Set<String> parserImports(CompilationUnit result) {
		Set<String> treeSet = new TreeSet<String>();
		List<?> imports = result.imports();
		for (Object obj : imports) {
			if (obj instanceof ImportDeclaration) {
				String fulltxt = obj.toString().trim();
				fulltxt = fulltxt.substring("import".length(), fulltxt.length() - 1).trim();
				treeSet.add(fulltxt);
			}
		}
		return treeSet;
	}
}
