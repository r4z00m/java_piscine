package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageToChar {

    public static final String ARGS_ERR = "Error: bad ARGS!";
    public static final String FILE_ERR = "Error: file not found!";
    public static final String PARAM_ERR = "Error: bad parameters!";
    public static final String FATAL = "Fatal! IOException!";
    public static final String PATH_TO_BMP = "src/resources/image.bmp";
    public static final Integer WHITE = -1;
    public static final Integer BLACK = -16777216;
    private final String[] args;

    public ImageToChar(String[] args) {
        this.args = args;
    }


    public void run() {
        if (check()) {
            return;
        }

        Character white = args[0].charAt(0);
        Character black = args[1].charAt(0);

        try (FileInputStream fileInputStream = new FileInputStream(PATH_TO_BMP)) {
            BufferedImage image = ImageIO.read(fileInputStream);
            print(image, white, black);
        } catch (FileNotFoundException e) {
            System.err.println(FILE_ERR);
        } catch (IOException e) {
            System.err.println(FATAL);
        }
    }

    private void print(BufferedImage image, Character white, Character black) {
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if (image.getRGB(j, i) == WHITE) {
                    System.out.print(white);
                } else if (image.getRGB(j, i) == BLACK) {
                    System.out.print(black);
                } else {
                    System.out.print(white);
                }
            }
            System.out.println();
        }
    }

    private boolean check() {
        if (args.length != 2) {
            System.err.println(ARGS_ERR);
            return true;
        }

        if (args[0].length() != 1 || args[1].length() != 1) {
            System.err.println(PARAM_ERR);
            return true;
        }

        return false;
    }
}
