
import java.util.concurrent.atomic.AtomicBoolean;

public class Program {

    public static final String ERROR_ARGS = "Error: Bad ARGS!";
    public static final String ERROR_PARAM = "Error: Bad parameter!";
    public static final String FATAL = "Fatal!";
    public static final String KEY = "--count=";
    public static final String EGG = "Egg";
    public static final String HEN = "Hen";

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

        Object lock = new Object();
        AtomicBoolean b = new AtomicBoolean(true);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    for (int i = 0; i < count; i++) {
                        if (!b.get()) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                System.out.println(FATAL);
                                System.exit(-1);
                            }
                        }
                        System.out.println(EGG);
                        b.set(false);
                        lock.notify();
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    for (int i = 0; i < count; i++) {
                        if (b.get()) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                System.out.println(FATAL);
                                System.exit(-1);
                            }
                        }
                        System.out.println(HEN);
                        b.set(true);
                        lock.notify();
                    }
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.err.println(FATAL);
        }
    }
}
