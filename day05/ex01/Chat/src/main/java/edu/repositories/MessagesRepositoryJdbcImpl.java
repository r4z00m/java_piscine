package edu.repositories;

import edu.models.Chatroom;
import edu.models.Message;
import edu.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    public static final String CONNECTION_ERROR = "Error: can't connection to DB";
    public static final String SQL_QUERY_ERROR = "Error: SQLException";

    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        Connection connection;

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            System.err.println(CONNECTION_ERROR);
            return Optional.empty();
        }

        ResultSet set;

        try {
            set = connection.createStatement().executeQuery("SELECT * FROM messages WHERE id=" + id);
        } catch (SQLException e) {
            System.err.println(SQL_QUERY_ERROR);
            return Optional.empty();
        }


        try {
            set.next();
            Long messageId = set.getLong("id");
            Long authorId = set.getLong("author");
            Long roomId = set.getLong("room");
            String message = set.getString("text");
            Timestamp timestamp = set.getTimestamp("time");

            ResultSet authorSet = connection.createStatement().executeQuery("SELECT * FROM users WHERE id=" + authorId);
            ResultSet roomSet = connection.createStatement().executeQuery("SELECT * FROM chatrooms WHERE id=" + roomId);

            authorSet.next();
            roomSet.next();

            Long authorIdSet = authorSet.getLong("id");
            String authorLoginSet = authorSet.getString("login");
            String authorPasswordSet = authorSet.getString("password");

            Long roomIdSet = roomSet.getLong("id");
            String roomName = roomSet.getString("name");

            Message msg = new Message(
                    messageId,
                    new User(authorIdSet, authorLoginSet, authorPasswordSet, null, null),
                    new Chatroom(roomIdSet, roomName, null, null),
                    message, timestamp);

            return Optional.of(msg);
        } catch (SQLException e) {
            System.err.println(SQL_QUERY_ERROR);
            System.exit(1);
        }
        return Optional.empty();
    }
}
