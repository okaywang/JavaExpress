/**
 * Created by guojun.wang on 2017/4/18.
 */
public class App {

    public static void main(String[] args) {
        int score[] = {67, 69, 75, 87, 89, 90, 99, 100};
        sort(score);
        for (int a = 0; a < score.length; a++) {
            System.out.print(score[a] + "\t");
        }
    }

    public static int[] sort(int[] items) {
        for (int i = 0; i < items.length - 1; i++) {    //最多做n-1趟排序
            for (int j = 0; j < items.length - i - 1; j++) {    //对当前无序区间score[0......length-i-1]进行排序(j的范围很关键，这个范围是在逐步缩小的)
                if (items[j] < items[j + 1]) {    //把小的值交换到后面
                    int temp = items[j];
                    items[j] = items[j + 1];
                    items[j + 1] = temp;
                }
            }
        }
        return items;
    }
}
