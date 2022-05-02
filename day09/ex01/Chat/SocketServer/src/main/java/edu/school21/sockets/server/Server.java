package edu.school21.sockets.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static final String PORT = "--port=";
    public static final String ERROR_BAD_ARGS = "Error: bad args";
    public static final String ERROR_BAD_PARAMS = "Error: bad params";
    public static final String ERROR_PUT_NUMBER = "Error: put number!";
    public static final String SERVER_STARTED = "Server started";

    private final String[] args;
    private int port;
    public static final ArrayList<MultiServer> servers = new ArrayList<>();

    public Server(String[] args) {
        this.args = args;
    }

    public void start() {
        if (checkArgs()) {
            return;
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println(SERVER_STARTED);

            while (true) {
                Socket socket = serverSocket.accept();
                new MultiServer(socket);
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
