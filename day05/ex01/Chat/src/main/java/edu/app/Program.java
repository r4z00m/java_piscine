package edu.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.models.Message;
import edu.repositories.MessagesRepository;
import edu.repositories.MessagesRepositoryJdbcImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Program {

    public static final String DB_URL = "jdbc:postgresql://localhost/";
    public static final String DB_USER = "postgres";
    public static final String SCHEMA_PATH = "src/main/resources/schema.sql";
    public static final String DATA_PATH = "src/main/resources/data.sql";

    public static final String CONNECTION_ERROR = "Error: can't connection to DB";
    public static final String SQL_QUERY_ERROR = "Error: SQLException";
    public static final String FILE_NOT_FOUND = "Error: file not found";
    public static final String PUT_LONG = "Error: put long!";
    public static final String NULL = "null";
    public static final String MSG_TO_USER = "Enter a message ID";

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

            MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);

            System.out.println(MSG_TO_USER);

            Long l;

            try (Scanner scanner = new Scanner(System.in)) {
                l = scanner.nextLong();
            } catch (InputMismatchException e) {
                System.err.println(PUT_LONG);
                return;
            }

            Optional<Message> message = messagesRepository.findById(l);

            if (message.isPresent()) {
                System.out.println(message.get());
            } else {
                System.out.println(NULL);
            }
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
