package ex01;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        if (num <= 1) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
        if (num == 2) {
            System.out.println("true 1");
            return;
        }
        if (num % 2 == 0) {
            System.out.println("false 1");
        } else {
            int count = 1;
            for (long i = 2; i * i <= num; i++) {
                if (num % i == 0) {
                    System.out.println("false " + count);
                    return;
                }
                count++;
            }
            System.out.println("true " + count);
        }
    }
}
