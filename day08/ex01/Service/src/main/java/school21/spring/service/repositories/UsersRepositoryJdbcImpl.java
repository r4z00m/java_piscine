package school21.spring.service.repositories;

import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    public static final String WHERE_IDENTIFIER = "WHERE identifier=";
    public static final String SELECT_FROM_USER_TABLE_WHERE_IDENTIFIER = "SELECT FROM userTable " + WHERE_IDENTIFIER;
    public static final String IDENTIFIER = "identifier";
    public static final String EMAIL = "email";
    public static final String SELECT_FROM_USER_TABLE = "SELECT * FROM userTable";
    public static final String INSERT_INTO_USER_TABLE_EMAIL_VALUES = "INSERT INTO userTable (email) VALUES (?)";
    public static final String DELETE_FROM_USER_TABLE_WHERE_IDENTIFIER = "DELETE FROM userTable " + WHERE_IDENTIFIER;
    public static final String SELECT_FROM_USER_TABLE_WHERE_EMAIL = "SELECT FROM userTable WHERE email=";
    public static final String UPDATE_USER_TABLE_SET_EMAIL = "UPDATE userTable SET email=";

    private final DataSource dataSource;
    private Connection connection;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        init();
    }

    private void init() {
        try {
            connection = dataSource.getConnection();
            if (connection == null) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(Long id) {
        try {
            ResultSet set = connection.createStatement()
                    .executeQuery(SELECT_FROM_USER_TABLE_WHERE_IDENTIFIER + id);

            if (set.next()) {
                User user = new User();

                user.setIdentifier(set.getLong(IDENTIFIER));
                user.setEmail(set.getString(EMAIL));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> findAll() {
        ArrayList<User> users = new ArrayList<>();

        try {
            ResultSet set = connection.createStatement()
                    .executeQuery(SELECT_FROM_USER_TABLE);

            while (set.next()) {
                User user = new User();
                user.setIdentifier(set.getLong(IDENTIFIER));
                user.setEmail(set.getString(EMAIL));
                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void save(User entity) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(INSERT_INTO_USER_TABLE_EMAIL_VALUES);
            preparedStatement.setString(1, entity.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User entity) {
        try {
            connection.createStatement()
                    .executeUpdate(UPDATE_USER_TABLE_SET_EMAIL
                            + entity.getEmail() + " " + WHERE_IDENTIFIER
                            + entity.getIdentifier());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try {
            connection.createStatement()
                    .executeUpdate(DELETE_FROM_USER_TABLE_WHERE_IDENTIFIER + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            ResultSet set = connection.createStatement()
                    .executeQuery(SELECT_FROM_USER_TABLE_WHERE_EMAIL + email);

            if (set.next()) {
                User user = new User();

                user.setIdentifier(set.getLong(IDENTIFIER));
                user.setEmail(set.getString(EMAIL));

                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
