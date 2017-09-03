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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guojun.wang on 2017/9/1.
 */
public class AppJob {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String jd = new String(Files.readAllBytes(Paths.get(AppJob.class.getClassLoader().getResource("jd.txt").toURI())));

        List<String> lines = Files.readAllLines(Paths.get(AppJob.class.getClassLoader().getResource("SensitiveWord.txt").toURI()), Charset.forName("utf-8"));

        String pattern = String.join("|", lines); // "foo and bar and baz"
        Pattern p = Pattern.compile("(" + pattern + ")");

        SensitivewordFilter filter = new SensitivewordFilter();
        System.out.println("敏感词的数量：" + filter.sensitiveWordMap.size());

        System.out.println("待检测语句字数：" + jd.length());


        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
        //Set<String> set = filterDfa(filter, jd);

        Set<String> set = filterReg(p, jd);
        System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);

        }

        long endTime = System.currentTimeMillis();

        System.out.println("总共消耗时间为：" + (endTime - beginTime));
    }

    private static Set<String> filterDfa(SensitivewordFilter filter, String text) {
        Set<String> set = filter.getSensitiveWord(text, 1);
        return set;
    }

    private static Set<String> filterReg(Pattern pattern, String text) {
        Set<String> set = new HashSet<>();
        Matcher m = pattern.matcher(text);
        while (m.find()) {
            String matched = m.group(1);
            set.add(matched);
        }
        return set;
        //return Pattern.matches(pattern, text);
//        int count = pattern.matcher(text).groupCount();
//        return count;
    }
}
