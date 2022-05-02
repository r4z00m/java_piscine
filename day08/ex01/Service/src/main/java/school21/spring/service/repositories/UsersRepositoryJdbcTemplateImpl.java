package school21.spring.service.repositories;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static java.sql.Types.BIGINT;
import static java.sql.Types.VARCHAR;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    public static final String SELECT_FROM_USER_TABLE_WHERE_IDENTIFIER = "SELECT FROM userTable WHERE identifier=?";
    public static final String SELECT_FROM_USER_TABLE = "SELECT * FROM userTable";
    public static final String SELECT_FROM_USER_TABLE_WHERE_EMAIL = "SELECT FROM userTable WHERE email=?";
    public static final String INSERT_INTO_USER_TABLE_EMAIL_VALUES = "INSERT INTO userTable (email) VALUES (?)";
    public static final String UPDATE_USER_TABLE_SET_EMAIL_WHERE_IDENTIFIER = "UPDATE userTable SET email=? WHERE identifier=?";
    public static final String DELETE_FROM_USER_TABLE_WHERE_IDENTIFIER = "DELETE FROM userTable WHERE identifier=?";
    private JdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
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
        jdbcTemplate.update(INSERT_INTO_USER_TABLE_EMAIL_VALUES, entity.getEmail());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update(UPDATE_USER_TABLE_SET_EMAIL_WHERE_IDENTIFIER,
                entity.getEmail(),
                entity.getIdentifier());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_FROM_USER_TABLE_WHERE_IDENTIFIER, id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jdbcTemplate
                .query(SELECT_FROM_USER_TABLE_WHERE_EMAIL,
                        new Object[]{email},
                        new int[]{VARCHAR},
                        new BeanPropertyRowMapper<>(User.class))
                .stream().findAny();
    }
}
