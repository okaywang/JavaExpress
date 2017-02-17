/**
 * Created by guojun.wang on 2017/2/17.
 */
public class SymmetricalStar {
    public static void main(String[] args) {
        SymmetricalStar obj = new SymmetricalStar();
        obj.printGraph(11);
    }

    public void printGraph(int rows) {
        if (rows < 3) {
            System.out.println("gt3");
            return;
        }
        if (rows % 2 != 1) {
            System.out.println("not odd");
            return;
        }
        int middle = rows / 2;
        int columns = rows;
        for (int row = 0; row < middle; row++) {
            int starCount = 2 * row + 1;
            int blankCount = columns - starCount;
            printLine(blankCount, starCount);
        }
        for (int row = middle; row < rows; row++) {
            int starCount = 2 * (2 * middle - row) + 1;
            int blankCount = columns - starCount;
            printLine(blankCount, starCount);
        }
    }

    private void printLine(int blankCount, int starCount) {
        printChars(blankCount / 2, ' ');
        printChars(starCount, '*');
        printChars(blankCount / 2, ' ');
        System.out.println();
    }

    private void printChars(int count, char c) {
        for (int i = 0; i < count; i++) {
            System.out.print(c);
        }
    }
}
