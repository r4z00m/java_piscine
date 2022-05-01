
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Program {

    public static final String ERROR_ARGS = "Error: bad ARGS!";
    public static final String ERROR_PATH = "Error: bad path";
    public static final String ERROR_MOVE = "Error: can't move file";
    public static final String COMMAND_EXIT = "exit";
    public static final String COMMAND_LS = "ls";
    public static final String COMMAND_CD = "cd";
    public static final String COMMAND_MV = "mv";
    public static final String KB = " KB";
    public static final String KEY = "--current-folder=";
    public static final String AVAILABLE = "Available commands: ls, cd, mv";
    public static final Integer DIVIDER = 1000;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(ERROR_ARGS);
            return;
        }

        if (!args[0].startsWith(KEY)) {
            System.err.println(ERROR_ARGS);
            return;
        }

        String path = args[0].replaceFirst(KEY, "");
        if (path.isEmpty()) {
            System.err.println(ERROR_PATH);
            return;
        }

        File current = new File(path);
        if (!Files.isDirectory(Paths.get(String.valueOf(current)))) {
            System.err.println(ERROR_PATH);
            return;
        }

        System.out.println(current);

        try (Scanner scanner = new Scanner(System.in)) {
            String string;

            while (true) {
                string = scanner.nextLine().trim();

                if (string.isEmpty()) {
                    continue;
                }

                if (string.equals(COMMAND_EXIT)) {
                    break;
                } else if (string.equals(COMMAND_LS)) {
                    showContent(current);
                } else if (string.startsWith(COMMAND_CD)) {
                    current = executeCd(current, string);
                    System.out.println(current);
                } else if (string.startsWith(COMMAND_MV)) {
                    executeMv(current, string);
                } else {
                    System.out.println(AVAILABLE);
                }
            }
        }
    }

    private static void executeMv(File current, String string) {
        String[] strings = string.split("\\s+");

        if (strings.length != 3) {
            System.err.println(ERROR_ARGS);
            return;
        }

        Path path1 = Paths.get(current + File.separator + strings[1]);
        Path path2;

        if (strings[2].startsWith("..")) {
            String pathTo = strings[2].replaceFirst("\\.\\.", "");

            if (pathTo.isEmpty()) {
                path2 = Paths.get(current.getParent() + File.separator + strings[1]);
            } else {
                path2 = Paths.get(current.getParent() + File.separator + pathTo + File.separator + strings[1]);
            }
        } else {
            File file = new File(strings[2]);

            if (file.isDirectory()) {
                path2 = Paths.get(strings[2] + File.separator + strings[1]);
            } else {
                path2 = Paths.get(current + File.separator + strings[2]);
            }
        }

        try {
            Files.move(path1, path1.resolveSibling(path2));
        } catch (IOException e) {
            System.err.println(ERROR_MOVE);
        }
    }

    private static File executeCd(File current, String string) {
        String[] strings = string.split("\\s+");

        if (strings.length != 2) {
            System.err.println(ERROR_ARGS);
            return current;
        }

        if (strings[1].startsWith("..")) {
            String path = strings[1].replaceFirst("\\.\\.", "");

            if (path.isEmpty()) {
                if (current.getParent() != null) {
                    return new File(current.getParent());
                } else {
                    return new File(File.separator);
                }
            } else {
                if (current.getParent() != null) {
                    File file = new File(current.getParent() + path);

                    return returnFile(file, current);
                } else {
                    File file = new File(path);

                    return returnFile(file, current);
                }
            }
        } else {
            File file = new File(current + File.separator + strings[1]);

            return returnFile(file, current);
        }
    }

    private static File returnFile(File file, File current) {
        if (file.isDirectory()) {
            return file;
        } else {
            System.err.println(ERROR_PATH);
            return current;
        }
    }

    private static void showContent(File current) {
        File[] files = current.listFiles();

        if (files != null) {
            for (File file : files) {
                if (!file.isHidden()) {
                    if (file.isDirectory()) {
                        System.out.println(file.getName() + " " + folderSize(file) / DIVIDER + KB);
                    } else {
                        System.out.println(file.getName() + " " + file.length() / DIVIDER + KB);
                    }
                }
            }
        }
    }

    public static Long folderSize(File directory) {
        Long length = 0L;

        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile())
                    length += file.length();
                else
                    length += folderSize(file);
            }
        }

        return length;
    }
}
