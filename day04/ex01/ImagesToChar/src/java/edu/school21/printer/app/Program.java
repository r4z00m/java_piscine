package edu.school21.printer.app;

import edu.school21.printer.logic.ImageToChar;

public class Program {

    public static void main(String[] args) {
        ImageToChar imageToChar = new ImageToChar(args);
        imageToChar.run();
    }
}
