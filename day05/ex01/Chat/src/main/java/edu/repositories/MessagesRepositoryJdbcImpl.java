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
    public static final String ID = "id";
    public static final String AUTHOR = "author";
    public static final String ROOM = "room";
    public static final String TEXT = "text";
    public static final String TIME = "time";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";

    public static final String SELECT_FROM_USERS_WHERE_ID = "SELECT * FROM users WHERE id=";
    public static final String SELECT_FROM_CHATROOMS_WHERE_ID = "SELECT * FROM chatrooms WHERE id=";
    public static final String SELECT_FROM_MESSAGES_WHERE_ID = "SELECT * FROM messages WHERE id=";

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
            set = connection.createStatement()
                    .executeQuery(SELECT_FROM_MESSAGES_WHERE_ID + id);
        } catch (SQLException e) {
            System.err.println(SQL_QUERY_ERROR);
            return Optional.empty();
        }


        try {
            set.next();
            Long messageId = set.getLong(ID);
            Long authorId = set.getLong(AUTHOR);
            Long roomId = set.getLong(ROOM);
            String message = set.getString(TEXT);
            Timestamp timestamp = set.getTimestamp(TIME);

            ResultSet authorSet = connection
                    .createStatement()
                    .executeQuery(SELECT_FROM_USERS_WHERE_ID + authorId);
            ResultSet roomSet = connection
                    .createStatement()
                    .executeQuery(SELECT_FROM_CHATROOMS_WHERE_ID + roomId);

            authorSet.next();
            roomSet.next();

            Long authorIdSet = authorSet.getLong(ID);
            String authorLoginSet = authorSet.getString(LOGIN);
            String authorPasswordSet = authorSet.getString(PASSWORD);

            Long roomIdSet = roomSet.getLong(ID);
            String roomName = roomSet.getString(NAME);

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
