package client;

import java.io.*;
import java.net.Socket;

public class Client {

    public static final String PORT = "--server-port=";
    public static final String LOCALHOST = "localhost";
    public static final String ERROR_BAD_ARGS = "Error: bad args";
    public static final String ERROR_BAD_PARAMS = "Error: bad params";
    public static final String ERROR_PUT_NUMBER = "Error: put number!";
    public static final String ERROR_YOU_SHOULD_TO_PUT_SIGN_UP = "Error: you should to put \"signUp\"";
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
                out.write(signUp + "\n");
                out.flush();

                String answer = in.readLine();
                System.out.println(answer);

                if (answer.equals(ERROR_YOU_SHOULD_TO_PUT_SIGN_UP)) {
                    return;
                }

                String userName = console.readLine();

                out.write(userName + "\n");
                out.flush();

                System.out.println(in.readLine());

                String password = console.readLine();

                out.write(password + "\n");
                out.flush();

                System.out.println(in.readLine());
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
