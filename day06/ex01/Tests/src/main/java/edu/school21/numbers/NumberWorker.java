package edu.school21.numbers;

public class NumberWorker {

    public boolean isPrime(int number) {
        if (number <= 1) {
            throw new IllegalNumberException("Error: Illegal number!");
        }
        if (number == 2) {
            return true;
        }
        if (number % 2 == 0) {
            return false;
        } else {
            for (long i = 2; i * i <= number; i++) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }

    public int digitsSum(int number) {
        if (number < 0) {
            number = -number;
        }
        int sum = 0;
        while (number != 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }
}
