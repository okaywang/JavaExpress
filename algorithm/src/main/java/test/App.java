package test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */
public class App {
    public static void main(String[] args) {
        List<Integer> result=new ArrayList<>();
        func(new int[]{1, 2, 3, 4, 5, 6}, "1,9,6");
    }


    private static void func(int[] items, String str) {
        String[] strs = str.split(",");
        if (strs.length == 1) {
            for (int i : items) {
                if (String.valueOf(i).equals(strs[0])) {
                    System.out.println(i);
                    return;
                }
            }
            return;
        }
        for (String s : strs) {
            func(items, s);
        }
    }
}
