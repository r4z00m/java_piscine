package edu.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.models.Chatroom;
import edu.models.Message;
import edu.models.User;
import edu.repositories.MessagesRepository;
import edu.repositories.MessagesRepositoryJdbcImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class Program {

    public static final String DB_URL = "jdbc:postgresql://localhost/";
    public static final String DB_USER = "postgres";
    public static final String SCHEMA_PATH = "src/main/resources/schema.sql";
    public static final String DATA_PATH = "src/main/resources/data.sql";

    public static final String CONNECTION_ERROR = "Error: can't connection to DB";
    public static final String SQL_QUERY_ERROR = "Error: SQLException";
    public static final String FILE_NOT_FOUND = "Error: file not found";

    public static void main(String[] args) {
        try (HikariDataSource dataSource = new HikariDataSource()) {
            dataSource.setJdbcUrl(DB_URL);
            dataSource.setUsername(DB_USER);
            dataSource.setPassword(null);

            Connection connection;
            try {
                connection = dataSource.getConnection();
                if (connection == null) {
                    System.err.println(CONNECTION_ERROR);
                    return;
                }
            } catch (SQLException e) {
                System.err.println(CONNECTION_ERROR);
                return;
            }

            List<String> schemaQueries;
            List<String> dataQueries;

            try {
                schemaQueries = Files.readAllLines(Paths.get(SCHEMA_PATH));
                dataQueries = Files.readAllLines(Paths.get(DATA_PATH));
            } catch (IOException e) {
                System.err.println(FILE_NOT_FOUND);
                return;
            }

            createSchema(connection, schemaQueries);

            insertToDB(connection, dataQueries);

            User creator = new User(1L, "user", "user", new ArrayList<Chatroom>(), new ArrayList<Chatroom>());
            User author = creator;
            Chatroom room = new Chatroom(3L, "room", creator, new ArrayList<Message>());
            Message message = new Message(null, author, room, "Hello!", Timestamp.valueOf(LocalDateTime.now()));

            MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);
            messagesRepository.save(message);

            System.out.println(message.getId());
        }
    }

    public static void createSchema(Connection connection, List<String> schemaQueries) {
        for (String schemaQuery : schemaQueries) {
            try {
                connection.createStatement().execute(schemaQuery);
            } catch (SQLException e) {
                System.err.println(SQL_QUERY_ERROR);
                return;
            }
        }
    }

    public static void insertToDB(Connection connection, List<String> dataQueries) {
        for (String dataQuery : dataQueries) {
            try {
                connection.createStatement().execute(dataQuery);
            } catch (SQLException e) {
                System.err.println(SQL_QUERY_ERROR);
                return;
            }
        }
    }
}
