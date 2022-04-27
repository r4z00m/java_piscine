
import java.util.Random;

public class Program {

    public static final String ERROR_ARGS = "Error: Bad ARGS!";
    public static final String ERROR_PARAM = "Error: Bad parameter!";
    public static final String FATAL = "Fatal!";
    public static final String KEY_ARRAY = "--arraySize=";
    public static final String KEY_THREADS = "--threadsCount=";

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println(ERROR_ARGS);
            return;
        }

        if (!args[0].startsWith(KEY_ARRAY) || !args[1].startsWith(KEY_THREADS)) {
            System.err.println(ERROR_PARAM);
            return;
        }

        Integer arraySize;
        Integer threadsCount;
        try {
            arraySize = Integer.parseInt(args[0].replaceFirst(KEY_ARRAY, ""));
            threadsCount = Integer.parseInt(args[1].replaceFirst(KEY_THREADS, ""));
        } catch (NumberFormatException e) {
            System.err.println(ERROR_PARAM);
            return;
        }

        if (arraySize < 1 || threadsCount < 1 || arraySize < threadsCount) {
            System.err.println(ERROR_PARAM);
            return;
        }

        Integer[] integers = initArray(arraySize);

        printSum(integers);

        Integer range = arraySize / threadsCount;
        Integer module = arraySize % threadsCount;

        startThreads(integers, threadsCount, range, module);
    }

    private static void startThreads(Integer[] integers, Integer threadsCount, Integer range, Integer module) {
        Integer[] sumArray = new Integer[threadsCount];

        Integer start = 0;

        if (module == 0) {
            for (int i = 0; i < threadsCount; i++) {

                Integer st = start;

                Integer index = i;

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Integer sum = 0;
                        Integer end = 0;
                        for (int j = st; j < range + st; j++) {
                            sum += integers[j];
                            end = j;
                        }
                        sumArray[index] = sum;

                        System.out.printf("Thread %d: from %d to %d sum is %d\n", index + 1, st, end, sum);
                    }
                });

                start += range;

                thread.start();
            }
        } else {
            if (integers.length / 2 < threadsCount) {
                startSpesial(integers, sumArray, threadsCount, range);
            } else {
                for (int i = 0; i < threadsCount; i++) {
                    Integer st = start;

                    Integer index = i;

                    Integer newRange = range + 1;
                    Thread thread = new Thread(new Runnable() {
                        Integer end;

                        @Override
                        public void run() {
                            Integer sum = 0;
                            for (int j = st; j < newRange + st; j++) {
                                if (j > integers.length - 1 || st > integers.length - 1) {
                                    end = j;
                                    break;
                                }
                                sum += integers[j];
                                end = j;
                            }
                            sumArray[index] = sum;

                            System.out.printf("Thread %d: from %d to %d sum is %d\n", index + 1, st, end, sum);
                        }
                    });

                    start += newRange;

                    thread.start();
                }
            }
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.err.println(FATAL);
            System.exit(-1);
        }

        printSumByThereads(sumArray);
    }

    private static void startSpesial(Integer[] integers, Integer[] sumArray, Integer threadsCount, Integer range) {
        Integer start = 0;

        for (int i = 0; i < threadsCount - 1; i++) {
            Integer st = start;

            Integer index = i;

            Thread thread = new Thread(new Runnable() {
                Integer end;

                @Override
                public void run() {
                    Integer sum = 0;
                    for (int j = st; j < range + st; j++) {
                        if (j > integers.length - 1) {
                            end = j;
                            break;
                        }
                        sum += integers[j];
                        end = j;
                    }
                    sumArray[index] = sum;

                    System.out.printf("Thread %d: from %d to %d sum is %d\n", index + 1, st, end, sum);
                }
            });

            start += range;

            thread.start();
        }

        Integer st = start;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Integer sum = 0;
                for (int j = st; j < integers.length; j++) {
                    sum += integers[j];
                }
                sumArray[threadsCount - 1] = sum;

                System.out.printf("Thread %d: from %d to %d sum is %d\n", threadsCount, st, integers.length - 1, sum);
            }
        });

        thread.start();
    }

    private static void printSumByThereads(Integer[] sumArray) {
        Integer sum = 0;

        for (Integer integer : sumArray) {
            sum += integer;
        }
        System.out.println("Sum by threads: " + sum);
    }

    public static Integer[] initArray(Integer arraySize) {
        Integer[] integers = new Integer[arraySize];
        Random random = new Random();

        for (int i = 0; i < integers.length; i++) {
            Integer num = random.nextInt(1001);
            Boolean b = random.nextBoolean();
            integers[i] = b ? num : -num;
        }
        return integers;
    }

    public static void printSum(Integer[] integers) {
        Integer sum = 0;

        for (Integer integer : integers) {
            sum += integer;
        }
        System.out.println("Sum: " + sum);
    }
}
