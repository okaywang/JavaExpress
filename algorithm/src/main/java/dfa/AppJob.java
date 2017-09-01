/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package dfa;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by guojun.wang on 2017/9/1.
 */
public class AppJob {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String jd = new String(Files.readAllBytes(Paths.get(AppJob.class.getClassLoader().getResource("jd.txt").toURI())));
        System.out.println(Pattern.matches("a*", "aaa我在练法轮功"));

        List<String> lines = Files.readAllLines(Paths.get(AppJob.class.getClassLoader().getResource("SensitiveWord.txt").toURI()), Charset.forName("utf-8"));

        String pattern = String.join("|", lines); // "foo and bar and baz"
        Pattern p = Pattern.compile(pattern);

        SensitivewordFilter filter = new SensitivewordFilter();
        System.out.println("敏感词的数量：" + filter.sensitiveWordMap.size());

        System.out.println("待检测语句字数：" + jd.length());


        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
//            Set<String> set = filterDfa(filter, jd);
//            System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);

            boolean b = filterReg(p, jd);
            System.out.println(b);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("总共消耗时间为：" + (endTime - beginTime));
    }

    private static Set<String> filterDfa(SensitivewordFilter filter, String text) {
        Set<String> set = filter.getSensitiveWord(text, 1);
        return set;
    }

    private static boolean filterReg(Pattern pattern, String text) {
        return pattern.matcher(text).groupCount()> 1;
        //return Pattern.matches(pattern, text);
//        int count = pattern.matcher(text).groupCount();
//        return count;
    }
}
