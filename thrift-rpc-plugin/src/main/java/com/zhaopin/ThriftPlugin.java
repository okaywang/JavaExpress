package com.zhaopin;

import com.zhaopin.task.ThriftCompileTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Created by Administrator on 2017/9/3.
 */
public class ThriftPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getTasks().create("thrift_compile", ThriftCompileTask.class);
    }
}
