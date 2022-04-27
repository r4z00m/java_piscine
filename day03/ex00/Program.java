
public class Program {

    public static final String ERROR_ARGS = "Error: Bad ARGS!";
    public static final String ERROR_PARAM = "Error: Bad parameter!";
    public static final String FATAL = "Fatal!";
    public static final String KEY = "--count=";
    public static final String EGG = "Egg";
    public static final String HEN = "Hen";
    public static final String HUMAN = "Human";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(ERROR_ARGS);
            return;
        }

        if (!args[0].startsWith(KEY)) {
            System.err.println(ERROR_PARAM);
            return;
        }

        final Integer count;
        try {
            count = Integer.parseInt(args[0].replaceFirst(KEY, ""));
        } catch (NumberFormatException e) {
            System.err.println(ERROR_PARAM);
            return;
        }

        if (count < 1) {
            System.err.println(ERROR_PARAM);
            return;
        }

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < count; i++) {
                System.out.println(EGG);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < count; i++) {
                System.out.println(HEN);
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.err.println(FATAL);
            return;
        }

        for (int i = 0; i < count; i++) {
            System.out.println(HUMAN);
        }
    }
}
