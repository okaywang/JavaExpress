package uuid;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * foo...Created by wgj on 2017/9/2.
 */
public class RdHelper {
    public static void showInfo() {
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();

        String jvmName = runtimeBean.getName();
        System.out.println("JVM Name = " + jvmName);
        long pid = Long.valueOf(jvmName.split("@")[0]);
        System.out.println("JVM PID  = " + pid);

        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] threadIds = bean.getAllThreadIds();
         //int peakThreadCount = bean.getPeakThreadCount();
        String result = Arrays.stream(threadIds)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
         System.out.println("Thread ids = " + result);
    }
}
