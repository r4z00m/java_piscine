
import java.util.Scanner;

public class Program {

    public static final int SIZE = 10;

    public static void main(String[] args) {
        int[] ints, countResult;
        char[] valueResult;

        String s;
        try (Scanner scanner = new Scanner(System.in)) {
            ints = new int[Character.MAX_VALUE];
            countResult = new int[SIZE];
            valueResult = new char[SIZE];
            s = scanner.nextLine();
        }

        char[] chars = s.toCharArray();

        for (int i = 0; i < s.length(); i++) {
            ints[chars[i]]++;
        }

        int max, maxIndex;

        for (int i = 0; i < SIZE; i++) {
            max = findMax(ints);
            maxIndex = findMaxIndex(ints, max);
            countResult[i] = ints[maxIndex];
            valueResult[i] = (char) maxIndex;
            ints[maxIndex] = 0;
        }

        printResult(countResult, valueResult);
    }

    public static void printResult(int[] counts, char[] chars) {
        int d = counts[0];

        for(int i = 0; i < SIZE; i++) {
            if(counts[i] * SIZE / d == SIZE)
                System.out.print(counts[i] + "\t");
        }

        System.out.println();

        for (int i = SIZE; i > 0; i--) {
            for (int j = 0; j < SIZE; j++) {
                if (counts[j] * SIZE / d >= i)
                    System.out.print("#\t");
                if (counts[j] * SIZE / d == i - 1) {
                    if (counts[j] != 0) {
                        System.out.print(counts[j] + "\t");
                    }
                }
            }
            System.out.println();
        }

        for (int i = 0; i < SIZE; i++) {
            if (chars[i] != 0) {
                System.out.print(chars[i] + "\t");
            }
        }
    }

    public static int findMax(int[] ints) {
        int max = 0;
        for (int i = 0; i < Character.MAX_VALUE; i++) {
            if (ints[i] > max) {
                max = ints[i];
            }
        }
        return max;
    }

    public static int findMaxIndex(int[] ints, int max) {
        for (int i = 0; i < Character.MAX_VALUE; i++) {
            if (ints[i] == max) {
                return i;
            }
        }
        return -1;
    }
}
