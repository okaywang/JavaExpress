package com.zhaopin.task;

import com.zhaopin.business.ThriftCompileService;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

/**
 * Created by Administrator on 2017/9/3.
 */
public class ThriftCompileTask extends DefaultTask {

    @TaskAction
    public void compile(){
        System.out.println("ThriftCompileTask.compile");
        Project project = this.getProject();
        new ThriftCompileService().execute(project);
    }
}
