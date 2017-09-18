package chinese;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/9/18.
 */
public class App {
    static String[] chinese = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    static String[] power = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十","百"};

    public static void main(String[] args) {
        String str = getStr(111);
        System.out.println(str);
    }

    private static String getStr(int num) {
        int[] items = getItems(num);
        String[] strItems = new String[items.length];
        for (int i = 0; i < items.length; i++) {
            strItems[i] = chinese[items[i]];
            if (items[i] != 0) {
                strItems[i] += power[items.length - i - 1];
            }
        }
        return String.join(" ", strItems);
    }

    private static int[] getItems(int num) {
        int len = String.valueOf(num).length();
        int[] items = new int[len];
        int value = num;
        for (int i = len - 1; i >= 0; i--) {
            items[i] = value % 10;
            value = value / 10;
        }
        return items;
    }
}
