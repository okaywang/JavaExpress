package com.zhaopin.plugin.generate;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.zhaopin.plugin.common.ThriftProject;
import com.zhaopin.plugin.common.ThriftResult;
import com.zhaopin.plugin.common.ThriftService;
import com.zhaopin.plugin.common.ThriftStruct;
import com.zhaopin.plugin.generate.helper.InvokerGenerateHelper;
import com.zhaopin.plugin.generate.helper.ProcessorGenerateHelper;
import com.zhaopin.plugin.generate.helper.StructCodecGenerateHelper;

public class AnnotationThriftGenerator extends AbstractThriftGenerator {

	private final ProcessorGenerateHelper processorGenerate;

	private final InvokerGenerateHelper invokerGenerate;

	private final StructCodecGenerateHelper structCodecGenerate;

	public AnnotationThriftGenerator(String srcDir) {
		this(new File(srcDir));
	}

	public AnnotationThriftGenerator(File srcDir) {
		super(srcDir);
		this.processorGenerate = new ProcessorGenerateHelper(srcDir);
		this.invokerGenerate = new InvokerGenerateHelper(srcDir);
		this.structCodecGenerate = new StructCodecGenerateHelper(srcDir);
	}

	@Override
	public void generate(ThriftProject thriftProject) {
		try {
			for (ThriftStruct thriftStruct : thriftProject.getThriftStructs()) {
				writeResult(structCodecGenerate.generate(thriftStruct));
			}
			for (ThriftService thriftService : thriftProject.getThriftServices()) {
				writeResult(processorGenerate.generate(thriftService));
				writeResult(invokerGenerate.generate(thriftService));
			}
		} catch (Exception exp) {
			exp.printStackTrace();
			throw exp;
		}
	}

	private void writeResult(ThriftResult result) {
		String className = result.getPath();
		String fullPath = getSrcPath() + File.separator + className.replace(".", File.separator) + ".java";
		try {
			File file = new File(fullPath);
			File dir = file.getParentFile();
			if (!dir.exists()) {
				dir.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			FileUtils.writeStringToFile(file, result.getContent(), "utf-8");
		} catch (Exception exp) {
			throw new IllegalStateException(exp);
		}
	}
}
