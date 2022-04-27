package ex02;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        int count = 0;
        while (i != 42) {
            int sum = sumDigitsOfNumber(i);
            if (isPrime(sum)) {
                count++;
            }
            i = scanner.nextInt();
        }
        System.out.println("Count of coffee - request - " + count);
    }

    private static int sumDigitsOfNumber(int number) {
        int sum = 0;
        while (number != 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }

    private static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        if (num == 2) {
            return true;
        }
        if (num % 2 == 0) {
            return false;
        } else {
            for (long i = 2; i * i <= num; i++) {
                if (num % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
