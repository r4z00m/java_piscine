package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.services.MessageService;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class MultiServer extends Thread {

    public static final String HELLO_FROM_SERVER = "Hello from Server!\n";
    public static final String SIGN_UP = "signUp";
    public static final String SIGN_IN = "signIn";
    public static final String ERROR_COMMAND_NOT_FOUND = "Error: command not found\n";
    public static final String ENTER_USERNAME = "Enter username:\n";
    public static final String ENTER_PASSWORD = "Enter password:\n";
    public static final String INCORRECT_PASSWORD = "Incorrect password!\n";
    public static final String START_MESSAGING = "Start messaging\n";
    public static final String EXIT = "exit";
    public static final String YOU_HAVE_LEFT_THE_CHAT = "You have left the chat.\n";
    private final BufferedReader in;
    private final BufferedWriter out;

    public MultiServer(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
            UsersService usersService = context.getBean(UsersService.class);
            MessageService messageService = context.getBean(MessageService.class);

            out.write(HELLO_FROM_SERVER);
            out.flush();

            String answer;

            while (true) {
                answer = in.readLine();

                if (!answer.equals(SIGN_UP) && !answer.equals(SIGN_IN)) {
                    out.write(ERROR_COMMAND_NOT_FOUND);
                    out.flush();
                } else {
                    break;
                }
            }

            out.write(ENTER_USERNAME);
            out.flush();

            String userName = in.readLine();

            out.write(ENTER_PASSWORD);
            out.flush();

            String password = in.readLine();

            if (answer.equals(SIGN_UP)) {
                out.write(usersService.saveNewUser(userName, password));
                out.flush();
            } else {
                if (!usersService.validateUser(userName, password)) {
                    out.write(INCORRECT_PASSWORD);
                    out.flush();
                    return;
                }

                Server.servers.add(this);

                out.write(START_MESSAGING);
                out.flush();

                while (true) {
                    answer = in.readLine();

                    if (!answer.equals(EXIT)) {
                        messageService.saveMessage(new Message(answer, Timestamp.valueOf(LocalDateTime.now())));
                    }

                    if (answer.equals(EXIT)) {
                        out.write(YOU_HAVE_LEFT_THE_CHAT);
                        out.flush();
                        Server.servers.removeIf(server -> server == this);
                        break;
                    }

                    for (MultiServer server : Server.servers) {
                        server.sendMessage(userName + ": " + answer);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String answer) {
        try {
            out.write(answer + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
