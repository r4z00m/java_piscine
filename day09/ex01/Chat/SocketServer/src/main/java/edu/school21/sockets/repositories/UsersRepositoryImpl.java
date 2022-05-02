package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static java.sql.Types.BIGINT;
import static java.sql.Types.VARCHAR;

@Repository
public class UsersRepositoryImpl implements UsersRepository {

    public static final String SELECT_FROM_USER_TABLE_WHERE_IDENTIFIER = "SELECT * FROM userTable WHERE identifier=?";
    public static final String SELECT_FROM_USER_TABLE = "SELECT * FROM userTable";
    public static final String SELECT_FROM_USER_TABLE_WHERE_NAME = "SELECT * FROM userTable WHERE name=?";
    public static final String INSERT_INTO_USER_TABLE_EMAIL_VALUES = "INSERT INTO userTable (name, password) VALUES (?, ?)";
    public static final String UPDATE_USER_TABLE_SET_EMAIL_WHERE_IDENTIFIER = "UPDATE userTable SET name=?, password=? WHERE identifier=?";
    public static final String DELETE_FROM_USER_TABLE_WHERE_IDENTIFIER = "DELETE FROM userTable WHERE identifier=?";

    private final JdbcTemplate jdbcTemplate;

    public UsersRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User findById(Long id) {
        return jdbcTemplate
                .query(SELECT_FROM_USER_TABLE_WHERE_IDENTIFIER,
                        new Object[]{id},
                        new int[]{BIGINT},
                        new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate
                .query(SELECT_FROM_USER_TABLE,
                        new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update(INSERT_INTO_USER_TABLE_EMAIL_VALUES,
                entity.getName(),
                entity.getPassword());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update(UPDATE_USER_TABLE_SET_EMAIL_WHERE_IDENTIFIER,
                entity.getName(),
                entity.getPassword(),
                entity.getIdentifier());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_FROM_USER_TABLE_WHERE_IDENTIFIER, id);
    }

    @Override
    public Optional<User> findByName(String name) {
        return jdbcTemplate
                .query(SELECT_FROM_USER_TABLE_WHERE_NAME,
                        new Object[]{name},
                        new int[]{VARCHAR},
                        new BeanPropertyRowMapper<>(User.class))
                .stream().findAny();
    }
}
