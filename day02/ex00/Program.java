
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Program {

    public static final Integer SING_SIZE = 8;
    public static final char[] HEX_CHAR_ARRAY = "0123456789ABCDEF".toCharArray();
    public static final String SIGNATURE = "signatures.txt";
    public static final String RESULT = "result.txt";
    public static final String EXIT = "42";
    public static final String FILE_NOT_FOUND = "Error: file not found!";
    public static final String PROCESSED = "PROCESSED";
    public static final String UNDEFINED = "UNDEFINED";
    public static final String ERROR = "Error!";

    public static void main(String[] args) {
        Map<String, String> signatures = readSignatures();

        if (signatures.size() == 0) {
            return;
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(RESULT, true)) {
            try (Scanner scanner = new Scanner(System.in)) {
                String fileName;

                while (true) {
                    fileName = scanner.nextLine();

                    if (fileName.equals(EXIT)) {
                        break;
                    }

                    try (FileInputStream fis = new FileInputStream(fileName)) {
                        byte[] bytes = new byte[SING_SIZE];

                        Integer res = fis.read(bytes, 0, SING_SIZE);

                        if (res < SING_SIZE) {
                            System.err.println(ERROR);
                            return;
                        }

                        putSignatureToFile(signatures, bytesToHex(bytes), fileOutputStream);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println(FILE_NOT_FOUND);
        } catch (IOException e) {
            System.err.println(ERROR);
        }
    }

    public static void putSignatureToFile(Map<String, String> signatures, String signature, FileOutputStream fileOutputStream) throws IOException {
        for (Map.Entry<String, String> entry : signatures.entrySet()) {
            if (signature.contains(entry.getValue())) {
                fileOutputStream.write(entry.getKey().getBytes());

                fileOutputStream.write('\n');

                System.out.println(PROCESSED);
                return;
            }
        }

        System.out.println(UNDEFINED);
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];

        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_CHAR_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_CHAR_ARRAY[v & 0x0F];
        }

        return new String(hexChars);
    }

    public static Map<String, String> readSignatures() {
        Map<String, String> signatures = new HashMap<>();

        try (Scanner fileScanner = new Scanner(new FileInputStream(SIGNATURE))) {
            while (fileScanner.hasNextLine()) {
                String[] strings = fileScanner.nextLine().split(",");
                signatures.put(strings[0], strings[1].replaceAll("\\s+", ""));
            }
        } catch (FileNotFoundException e) {
            System.err.println(FILE_NOT_FOUND);
        }

        return signatures;
    }
}
