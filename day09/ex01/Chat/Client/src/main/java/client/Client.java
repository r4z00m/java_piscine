package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static final String PORT = "--server-port=";
    public static final String LOCALHOST = "localhost";
    public static final String ENTER_USERNAME = "Enter username:";
    public static final String ERROR_BAD_ARGS = "Error: bad args";
    public static final String ERROR_BAD_PARAMS = "Error: bad params";
    public static final String ERROR_PUT_NUMBER = "Error: put number!";
    public static final String INCORRECT_PASSWORD = "Incorrect password!";
    public static final String SUCCESSFUL = "Successful!";
    public static final String USER_WITH_THE_NAME_IS_ALREADY_EXIST = "User with the name is already exist!";
    public static final String EXIT = "exit";
    public static final String YOU_HAVE_LEFT_THE_CHAT = "You have left the chat.";
    public static final String ERROR_SERVER_IS_DOWN = "Error: server is down!";

    private final String[] args;
    private int port;

    public Client(String[] args) {
        this.args = args;
    }

    public void start() {
        if (checkArgs()) {
            return;
        }

        try (Socket socket = new Socket(LOCALHOST, port)) {
            try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
                 BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                System.out.println(in.readLine());

                String signUp = console.readLine();

                while (true) {
                    out.write(signUp + "\n");
                    out.flush();

                    String answer = in.readLine();
                    System.out.println(answer);

                    if (answer.equals(ENTER_USERNAME)) {
                        break;
                    }

                    signUp = console.readLine();
                }

                String userName = console.readLine();

                out.write(userName + "\n");
                out.flush();

                System.out.println(in.readLine());

                String password = console.readLine();

                out.write(password + "\n");
                out.flush();

                String answer = in.readLine();
                System.out.println(answer);

                if (answer.equals(INCORRECT_PASSWORD) ||
                        answer.equals(SUCCESSFUL) ||
                        answer.equals(USER_WITH_THE_NAME_IS_ALREADY_EXIST)) {
                    out.write("\n");
                    out.flush();
                    return;
                }

                Thread thread = new Thread(() -> {
                    try {
                        while (true) {
                            String s = in.readLine();
                            if (s == null) {
                                break;
                            }
                            if (s.equals(YOU_HAVE_LEFT_THE_CHAT)) {
                                break;
                            }
                            System.out.println(s);
                        }
                    } catch (IOException ignored) {
                    }
                });

                thread.start();

                while (true) {
                    String s = console.readLine();
                    out.write(s + "\n");
                    out.flush();
                    if (s.equals(EXIT)) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(ERROR_SERVER_IS_DOWN);
        }
    }

    private boolean checkArgs() {
        if (args.length != 1) {
            System.err.println(ERROR_BAD_ARGS);
            return true;
        }

        if (!args[0].startsWith(PORT)) {
            System.err.println(ERROR_BAD_PARAMS);
            return true;
        }

        try {
            port = Integer.parseInt(args[0].replaceFirst(PORT, ""));
        } catch (NumberFormatException e) {
            System.err.println(ERROR_PUT_NUMBER);
            return true;
        }

        return false;
    }
}
