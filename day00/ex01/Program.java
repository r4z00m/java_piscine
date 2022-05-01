
import java.util.Scanner;

public class Program {

    public static final String ILLEGAL_ARGUMENT = "Illegal Argument";
    public static final String TRUE = "true ";
    public static final String FALSE = "false ";
    public static final String TRUE_1 = "true 1";
    public static final String FALSE_1 = "false 1";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int num = scanner.nextInt();

            if (num <= 1) {
                System.err.println(ILLEGAL_ARGUMENT);
                System.exit(-1);
            }

            if (num == 2) {
                System.out.println(TRUE_1);
                return;
            }

            if (num % 2 == 0) {
                System.out.println(FALSE_1);
            } else {
                int count = 1;

                for (long i = 2; i * i <= num; i++) {

                    if (num % i == 0) {
                        System.out.println(FALSE + count);
                        return;
                    }
                    count++;
                }

                System.out.println(TRUE + count);
            }
        }
    }
}
