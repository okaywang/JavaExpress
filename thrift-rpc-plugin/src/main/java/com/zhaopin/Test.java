package com.zhaopin;

import com.zhaopin.business.ThriftCompileService;
import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;

import java.io.File;

/**
 * Created by Administrator on 2017/9/3.
 */
public class Test {
    public static void main(String[] args) {
        File file = new File("D:\\Projects\\JavaExpress\\thrift-rpc-job");
        Project project = ProjectBuilder.builder().withProjectDir(file).build();
        ThriftCompileService service = new ThriftCompileService();
        service.execute(project);
        System.out.println(project);
    }
}
