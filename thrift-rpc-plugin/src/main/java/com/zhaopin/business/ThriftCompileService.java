package com.zhaopin.business;

import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

/**
 * Created by Administrator on 2017/9/3.
 */
public class ThriftCompileService {
    public Object execute(Project project) {
        System.out.println("ThriftCompileService.execute");
        Object obj = project.findProperty("sourceSets");
        System.out.println(obj.getClass().toString());
        if (!(obj instanceof SourceSetContainer)) {
            System.err.println("gradle插件无法找到sourceSets对象");
            return null;
        }
        SourceSetContainer sourceSets = (SourceSetContainer) obj;
        SourceSet main = sourceSets.getByName("main");
        System.out.println("main");
        if (main == null) {
            // WIKI
            System.err.println("gradle.build中没有配置sourceSets");
            return null;
        }
        SourceDirectorySet srcDirs = main.getJava();
        if (srcDirs == null) {
            System.err.println("gradle.build中没有配置sourceSets的java");
            return null;
        }
        SourceDirectorySet resDirs = main.getResources();
        if (resDirs == null) {
            System.err.println("gradle.build中没有配置sourceSets的resources");
            return null;
        }
        System.out.println("[thrift] src dir is " + srcDirs.getSrcDirs());
        System.out.println("[thrift] res dir is " + resDirs.getSrcDirs());
        ThriftParser thriftParser = new AnnotationThriftParser(srcDirs.getSrcDirs(), resDirs.getSrcDirs());
//        ThriftProject thriftProject = thriftParser.parse();
//        return thriftProject;
        return new Object();
    }
}
