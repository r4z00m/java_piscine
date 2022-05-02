package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final String PORT = "--port=";
    public static final String SIGN_UP = "signUp";
    public static final String HELLO_FROM_SERVER = "Hello from Server!\n";
    public static final String ENTER_USERNAME = "Enter username:\n";
    public static final String ENTER_PASSWORD = "Enter password:\n";
    public static final String ERROR_BAD_ARGS = "Error: bad args";
    public static final String ERROR_BAD_PARAMS = "Error: bad params";
    public static final String ERROR_PUT_NUMBER = "Error: put number!";
    public static final String ERROR_YOU_SHOULD_TO_PUT_SIGN_UP = "Error: you should to put \"signUp\"\n";

    private final String[] args;
    private int port;

    public Server(String[] args) {
        this.args = args;
    }

    public void start() {
        if (checkArgs()) {
            return;
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket = serverSocket.accept();

            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                out.write(HELLO_FROM_SERVER);
                out.flush();

                String answer = in.readLine();
                if (!answer.equals(SIGN_UP)) {
                    out.write(ERROR_YOU_SHOULD_TO_PUT_SIGN_UP);
                    out.flush();
                    return;
                }

                out.write(ENTER_USERNAME);
                out.flush();

                String userName = in.readLine();

                out.write(ENTER_PASSWORD);
                out.flush();

                String password = in.readLine();

                ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);

                UsersService usersService = context.getBean(UsersService.class);

                out.write(usersService.saveNewUser(userName, password));
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
