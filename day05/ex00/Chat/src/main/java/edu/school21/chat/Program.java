package edu.school21.chat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

public class Program {

    public static final String DB_URL = "jdbc:postgresql://localhost/";
    public static final String DB_USER = "postgres";
    public static final String SCHEMA_PATH = "src/main/resources/schema.sql";
    public static final String DATA_PATH = "src/main/resources/data.sql";

    public static final String CONNECTION_ERROR = "Error: can't connection to DB";
    public static final String FILE_NOT_FOUND = "Error: file not found";
    public static final String SQL_QUERY_ERROR = "Error: SQLException";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, null)) {
            if (connection == null) {
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
        } catch (SQLException e) {
            System.err.println(CONNECTION_ERROR);
            e.printStackTrace();
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
