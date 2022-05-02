package edu.school21.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {

    public static final String NOT_THROWN_EXCEPTION = "Not thrown exception";

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 113})
    public void isPrimeForPrimes(int number) {
        assertTrue(new NumberWorker().isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 42, 195})
    public void isPrimeForNotPrimes(int number) {
        assertFalse(new NumberWorker().isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, -122})
    public void isPrimeForIncorrectNumbers(int number) {
        assertThrows(IllegalNumberException.class, () -> {
            new NumberWorker().isPrime(number);
        }, NOT_THROWN_EXCEPTION);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void isCorrectDigitsSum(int number, int sum) {
        assertEquals(new NumberWorker().digitsSum(number), sum);
    }
}
