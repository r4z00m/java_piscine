package ex04;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] ints = new int[65535];
        int[] countResult = new int[10];
        char[] valueResult = new char[10];
        String s = scanner.nextLine();
        char[] chars = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            ints[chars[i]]++;
        }
        int max, maxIndex;
        for (int i = 0; i < 10; i++) {
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
        for(int i = 0; i < 10; i++) {
            if(counts[i] * 10 / d == 10)
                System.out.print(counts[i] + "\t");
        }
        System.out.println();
        for (int i = 10; i > 0; i--) {
            for (int j = 0; j < 10; j++) {
                if (counts[j] * 10 / d >= i)
                    System.out.print("#\t");
                if (counts[j] * 10 / d == i - 1) {
                    if (counts[j] != 0) {
                        System.out.print(counts[j] + "\t");
                    }
                }
            }
            System.out.println();
        }
        for (int i = 0; i < 10; i++) {
            if (chars[i] != 0) {
                System.out.print(chars[i] + "\t");
            }
        }
    }

    public static int findMax(int[] ints) {
        int max = 0;
        for (int i = 0; i < 65535; i++) {
            if (ints[i] > max) {
                max = ints[i];
            }
        }
        return max;
    }

    public static int findMaxIndex(int[] ints, int max) {
        for (int i = 0; i < 65535; i++) {
            if (ints[i] == max) {
                return i;
            }
        }
        return -1;
    }
}
