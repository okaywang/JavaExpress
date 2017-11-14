package star;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by guojun.wang on 2017/11/10.
 */
public class App {
    public static void main(String[] args) {
        printStar(1, 5);
    }

    private static void printStar(int row, int maxRow) {
        System.out.println(System.out.getClass());
        System.out.println("abcd");
        System.out.println(new char[]{'2', '3', '1'});
        if (row == maxRow) {
            pirntLine(2 * row - 1, '*', 2 * maxRow - 1);
            return;
        }

        pirntLine(2 * row - 1, '*', 2 * maxRow - 1);
        printStar(row + 1, maxRow);
        pirntLine(2 * row - 1, '*', 2 * maxRow - 1);
    }

    private static void pirntLine(int count, char c, int length) {
        for (int i = 0; i < (length - count) / 2; i++) {
            System.out.print(' ');
        }
        for (int i = 0; i < count; i++) {
            System.out.print('*');
        }
        System.out.println();
    }
}
