package meituan;

/**
 * Created by Administrator on 2017/9/26.
 */
public class App {
    public static void main(String[] args) {

        int x = 100;
        Integer y = null;
        System.out.println(x == y);
        int[][] items = new int[10][10];
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[i].length; j++) {
                items[i][j] = Integer.parseInt(String.valueOf(i) + String.valueOf(j));
            }
        }
        printTable(items);
    }

    private static void printTable(int[][] items) {
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[i].length; j++) {
                if (items[i][i] < 10) {
                    System.out.print("0");
                }
                System.out.print(items[i][j]);
                System.out.print("\t");
            }
            System.out.println();
        }
    }
}
