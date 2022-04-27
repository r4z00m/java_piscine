package ex03;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i;
        long store = 0;
        for (i = 1; i <= 18; i++) {
            String s = scanner.next();
            int day;
            if (s.equals("42")) {
                break;
            } else if (s.equals("Week")) {
                day = scanner.nextInt();
                if (day != i) {
                    System.err.println("IllegalArgument");
                    System.exit(-1);
                }
            }
            int min = scanner.nextInt();
            if (min < 1 || min > 9) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            for (int j = 0; j < 4; j++) {
                int grade = scanner.nextInt();
                if (grade < 1 || grade > 9) {
                    System.err.println("IllegalArgument");
                    System.exit(-1);
                }
                if (grade < min) {
                    min = grade;
                }
            }
            int tmp = i;
            int multiplier = 1;
            while (tmp > 1) {
                multiplier *= 10;
                tmp--;
            }
            store += (long) min * multiplier;
        }
        for (int j = 1; j < i; j++) {
            System.out.print("Week " + j + " ");
            long len = store % 10;
            for (int k = 0; k < len; k++) {
                System.out.print("=");
            }
            System.out.println(">");
            store /= 10;
        }
    }
}
